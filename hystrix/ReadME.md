
> 1.https://blog.csdn.net/qq_43140314/article/details/104175783

## 2.MNT中实例解读
   2.1 ribbon
   ribbon.ReadTimeout = 9000
   ribbon.ConnectTimeout = 9000
   OPB-MS.ribbon.ReadTimeout = 100000
   OPB-MS.ribbon.ConnectTimeout = 100000
   PMS-MS.ribbon.ReadTimeout = 15000
   PMS-MS.ribbon.ConnectTimeout = 10000
   ribbon的全局请求处理超时时间为9s，请求连接超时时间为9s；
   
   opb-ms的请求处理超时时间为100s，请求连接超时时间为100s；
   
   pms-ms的请求处理超时时间为15s，请求连接超时时间为10s；
   
   2.2 hystrix
   hystrix.command.OpbClient#uF(MultipartFile,String,Integer,String,String).execution.isolation.thread.timeoutInMilliseconds = 100000
   hystrix.command.OpbClient#upload(MultipartFile).execution.isolation.thread.timeoutInMilliseconds = 25000
   hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds = 5000
   hystrix全局方法执行的超时时间为5s；
   
   OpbClient.uF执行的超时时间为10s；
   
   OpbClient.upload执行的超时时间为2.5s；
   
   因为mnt配置中未开启ribbon的重试机制，所有hystrix超时时间可以小于ribbon的超时时间
   
   2.3 Feign
   feign.hystrix.enabled = true
   feign.client.config.PMS-MS.connectTimeout = 10000
   开启hystrix；
   
   feign调用pms-ms服务的连接超时时间为10s；
   

## 3.Ribbon
   3.1 简介
   一个基于HTTP和TCP的客户端负载均衡工具，它基于Netflix Ribbon实现。通过Spring Cloud的封装，轻松将面向服务的REST模版请求自动转化为客户端负载均衡的服务调用。
   
   Ribbon工作时分为两步：第一步先选择Eureka Server, 它优先选择在同一个Zone且负载较少的Server；第二步再根据用户指定的策略，在从Server取到的服务注册列表中选择一个地址。其中Ribbon提供了多种策略，例如轮询、随机、根据响应时间加权等。
   
   3.2 部分源码
   3.2.1 LoadBalancerAutoConfiguration 自动化配置类
   org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration为实现客户端负载均衡器的自动化配置类
   
   1.创建了一个LoadBalancerInterceptor的bean，用于对实现对客户端发起的请求进行拦截，以实现客户端的负载均衡。
   
    
   
   2.创建了一个RestTemplateCustomizer的bean，用于给RestTemplate增加LoadBalancerInterceptor拦截器。
   
    
   
   3.创建了一个被@LoadBalanced注解修饰的RestTemplate对象列表，并在这里进行初始化
   
    
   
   4.其余还对重试机制进行了配置
   
   3.2.2 LoadBalanceInterceptor
   当被@LoadBalanced注解修饰的RestTemplate对象对外发起HTTP请求时，会被LoadBalanceInterceptor.intercept拦截，通过创建LoadBalancerInterceptor时注入的LoadBalanceClient中的方法进行负载均衡。
   
   流程图
   
   
   
    
   
   3.3 配置详解
   3.3.1 自动化配置
   未引入Eureka服务治理框架时
   
   （引入Eureka服务治理框架后，自动化配置会有不同）
   
   IClientConfig-Ribbon的客户端配置，默认：DefaultClientConfigImpl
   IRule-Ribbon的负载均衡策略，默认：ZoneAvoidanceRule（该策略能够在多区域环境下选出最佳区域的实例进行访问）
   RandomRule
   RoundRobinRule
   RetryRule
   WeightResponseTimeRule
   ClientConfigEnableRoundRobinRule
   PredicateBasedRule
   AvaailabiltyFilteringRule
   ZoneAvoidanceRule
   IPing-Ribbon的实例检查策略，默认：NoOpPing（该策略不会检查实例是否可用，总是返回true）
   DummyPing
   NoOpPing
   PingUrl 通过request访问服务判断
   NIWSDiscoveryPing 通过Eureka判断
   SeverList<Server>-服务实例清单的维护机制，默认：ConfigurationBasedSeverList
   SeverListFilter<Server>-服务实例清单过滤机制，默认：ZonePreferenceServerListFilter（该策略有限过滤出与请求调用方处于同区域的服务实例）
   ILoadBalancer-负载均衡器，默认：ZoneAwareLoadBalancer（具备区域感知的能力）
   需要改变自动化配置的默认实现，只需要创建对应的实现实例，就能方便地替换上面的这些默认的配置实现
   
   ribbon和Eureka结合后
   
   SeverList：com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList
   IPing：com.netflix.niws.loadbalancer.NIWSDiscoveryPing
   由于Spring Cloud默认实现区域亲和策略，可以通过Eureka实例的元数据配置来实现区域化的实例配置方案。需要在服务实例的元数据中增加zone参数来指定自己所在的区域
   比如：eureka.instance.metadataMap.zone=shanghai
   Ribbon 和 Eureka结合后，可通过参数配置金庸Eureka对Ribbon服务实例的维护实现
   比如：ribbon.eureka.enabled=false
   3.3.2 参数配置
   全局配置采用 ribbon.<key>=<value>
   
   具体服务配置采用 <service-name>.ribbon.<key>=<value>
   
   部分key
   
   ConnectTimeout：请求连接的超时时间（ms）
   ReadTimeout：请求处理的超时时间（ms）
   OkToRetryAllOperations：对所有操作请求都进行重试（true/false）
   MaxAutoRetriesNextSever：切换实例的重试次数（次数）
   MaxAutoRetries：对当前实例的重试次数（次数）
   其他参数可参考DefaultClientConfigImpl
   

## 4.Hystrix
4.1 简介
Hystrix熔断器，容错管理工具，旨在通过熔断机制控制服务和第三方库的节点,从而对延迟和故障提供更强大的容错能力。在Spring Cloud Hystrix中实现了线程隔离、断路器等一系列的服务保护功能。它也是基于Netflix的开源框架 Hystrix实现的，该框架目标在于通过控制那些访问远程系统、服务和第三方库的节点，从而对延迟和故障提供更强大的容错能力。Hystrix具备了服务降级、服务熔断、线程隔离、请求缓存、请求合并以及服务监控等强大功能。

工作流程

创建HystrixCommand或HystrixObservableCommand对象
命令执行
结果是否被缓存
断路器是否打开
线程池/请求队列/信号量是否占满
HystrixObservableCommand.construct()：返回一个Observable对象来发射多个结果，或通过onError发送错误通知
计算断路器的健康度
fallback处理
返回成功的响应
4.2 原理
4.2.1 断路器
HystrixCircuitBreaker定义



 

isOpen() 判断断路器的打开/关闭状态，详细逻辑如下

如果断路器打开表示为true，则直接返回true，表示断路器处于打开状态。否则，就从度量指标对象metrics中获取HealthCounts统计对象做进一步判断。

如果它的请求总数QPS在预设的阀值范围内就返回false，表示断路器处于为打开状态。配置参数为circuitBreakerRequestVolumeThreshold，默认值为20。
如果错误百分比在阀值范围内就返回false，表示断路器处于为打开状态。配置参数为circuitBreakerErrorThresholdPercentage，默认值为50。
如果以上两条件都不满足，则将判断器设置为打开状态（熔断/短路）。同时，如果是从关闭状态切换成打开状态的话，就将当前时间记录到circuitOpendOrLastTestedTime对象中。
 

断路器详细执行逻辑 来自Netfrix Hystrix官方文档



4.2.2 线程隔离
采用舱壁模式实现线程池的隔离，为每个依赖服务创建一个独立的线程池，从而不会因为个别依赖服务出现问题而引起非相关服务的异常。并且从性能角度考虑，线程池上的开销相对于隔离所带来的好处是无法比拟的。

在Hystrix内线程的使用是基于Java内置线程池的简单封装，通过Hystrix线程池参数我们可以控制执行Hystrix命令的线程池的行为。比如核心线程池、最大线程数量、作业队列的最大值等。

Hystrix中除了可使用线程池之外，还可以使用信号量来控制单个依赖服务的并发度，如果将隔离策略参数execution.isolation.strategy设置为SEMAPHORE，Hystrix会使用信号量替代线程池。使用信号量的开销远比线程池的开销小，但是它不能设置超时和实现异步访问。

4.3 配置详解
4.3.1 线程隔离相关配置
Hystrix具备的重要关键特性之一就是它能够实现对第三方服务依赖的资源隔离，而隔离最常见的方式是通过线程池资源的隔离来实现的，Hystrix会为每个第三方服务依赖配置单独的线程池资源，从而避免对第三方服务依赖的请求占用应用主线程资源以免造成系统雪崩。

是否给方法执行设置超时时间，默认为true。一般我们不要改
hystrix.command.default.execution.timeout.enabled=true

配置请求隔离的方式，这里是默认的线程池方式。还有一种信号量的方式semaphore，使用比较少
hystrix.command.default.execution.isolation.strategy=threadPool

方式执行的超时时间，默认为1000毫秒，在实际场景中需要根据情况设置
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=1000

发生超时时是否中断方法的执行，默认值为true
hystrix.command.default.execution.isolation.thread.interruptOnTimeout=true

是否在方法执行被取消时中断方法，默认值为false
hystrix.command.default.execution.isolation.thread.interruptOnCancel=false
4.3.2 熔断器相关配置
熔断器是Hystrix最主要的功能，它开启和关闭的时机、灵敏度及准确性是Hystrix是否能够发挥重要的关键

是否启动熔断器，默认为true
hystrix.command.default.circuitBreaker.enabled=true

启用熔断器功能窗口时间内的最小请求数
hystrix.command.default.circuitBreaker.requestVolumeThreshold=20

指定熔断器打开后多长时间内允许一次请求尝试执行，官方默认配置为5秒
hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds=500

在通过滑动窗口获取到当前时间段内Hystrix方法执行失败的几率后，根据此配置来判断是否需要打开熔断器
hystrix.command.default.circuitBreaker.errorThresholdPercentage

是否强制启用熔断器，默认false
hystrix.command.default.circuitBreaker.forceOpen=false

是否强制关闭熔断器，默认false
hystrix.command.default.circuitBreaker.forceClosed=false
4.3.3 Metrics（统计器）相关配置
Hystrix是否正常工作最主要的依赖就是根据捕获的调用指标信息来判断是否打开或者关闭熔断器

用于设置Hystrix统计滑动窗口的时间，单位为毫秒，默认设置为10000毫秒
hystrix.command.default.metrics.rollingStats.timeInMilliseconds=10000

此属性指定了滑动统计窗口划分的桶数。默认为10
metrics.rollingStats.timeInMilliseconds % metrics.rollingStats.numBuckets == 0必须成立，否则就会抛出异常
hystrix.command.default.metrics.rollingStats.numBuckets=10

配置统计方法是否响应时间百分比，默认为true
Hystrix会统计方法执行1%，10%，50%，90%，99%等比例请求的平均耗时用来生成统计图表
hystrix.command.default.metrics.rollingPercentile.enabled=true

统计响应时间百分比时的窗口大小，默认为60000毫秒，即1分钟
hystrix.command.default.metrics.rollingPercentile.timeInMilliseconds=60000

用于设置滑动百分比窗口要划分的桶数，默认为6
hystrix.command.default.metrics.rollingPercentile.numBuckets=6

表示统计响应时间百分比，每个滑动窗口的桶内要保存的请求数，默认为100
hystrix.command.default.metrics.rollingPercentile.bucketSize=100

配置了健康数据统计器（会影响Hystrix熔断）中每个桶的大小，默认为500毫秒
hystrix.command.default.metrics.healthSnapshot.intervalInMilliseconds=500
4.3.4 线程池相关配置
在前面提到过Hystrix实现对第三方服务依赖资源隔离最主要的方式就是通过线程池，而Hystrix内线程的使用是基于Java内置线程池的简单封装，通过以下Hystrix线程池参数我们可以控制执行Hystrix命令的线程池的行为。

核心线程池的大小，默认值是10
hystrix.command.default.coreSize=10


是否允许线程池扩展到最大线程池数量，默认为false
hystrix.command.default.allowMaximumSizeToDivergeFromCoreSize=false


线程池中线程的最大数量，默认值是10。此配置项单独配置时并不会生效，需要启用allowMaximumSizeToDivergeFromCoreSize
hystrix.command.default.maximumSize=10


作业队列的最大值，默认值为-1。表示队列会使用SynchronousQueue，此时值为0，Hystrix不会向队列内存放作业
如果此值设置为一个正int型，队列会使用一个固定size的LinkedBlockingQueue，此时在核心线程池都忙碌的情况下，会将作业暂时存放在此队列内，但是超出此队列的请求依然会被拒绝
hystrix.command.default.maxQueueSize=-1


设置队列拒绝请求的阀值，默认为5
hystrix.command.default.queueSizeRejectionThreshold=5
控制线程在释放前未使用的时间，默认为1分钟
hystrix.command.default.keepAliveTimeMinutes=1

## 5.Feign
5.1 简介
Feign是一个声明式的web service客户端，它使得编写web service客户端更为容易。创建接口，为接口添加注解，即可使用Feign。Feign可以使用Feign注解或者JAX-RS注解，还支持热插拔的编码器和解码器。Spring Cloud为Feign添加了Spring MVC的注解支持，并整合了Ribbon和Eureka来为使用Feign时提供负载均衡。

 



name为服务名称

fallbackFactory为服务降级处理方法

5.2 配置详解
是否开启hystrix
feign.hystrix.enabled=true
连接超时时间
feign.client.config.default.connectTimeout=5000
读取超时时间
feign.client.config.default.readTimeout=5000
5.3 Feign、Hystrix、Ribbon的超时配置关系
在前面的内容我们分别单独梳理了Feign、Hystrix及Ribbon三者常见的配置，针对各自的特性功能配置我们并没有异议，但是我们也看到它们都有针对微服务超时的配置，而在开启熔断器功能后，这些超时配置会影响到熔断器及服务降级逻辑的行为，那么它们之间超时的配置如下：



如上图所示，在Spring Cloud中使用Feign进行微服务调用分为两层：Ribbon的调用及Hystrix的调用。所以Feign的超时时间就是Ribbon和Hystrix超时时间的结合，而如果不启用Hystrix则Ribbon的超时时间就是Feign的超时时间配置，Feign自身的配置会被覆盖。

而如果开启了Hystrix，那么Ribbon的超时时间配置与Hystrix的超时时间配置则存在依赖关系，因为涉及到Ribbon的重试机制，所以一般情况下都是Ribbon的超时时间小于Hystrix的超时时间

那么Ribbon和Hystrix的超时时间配置的关系具体是什么呢？如下：

Hystrix的超时时间=Ribbon的重试次数(包含首次)*(ribbon.ReadTimeout+ribbon.ConnectTimeout)

而Ribbon的重试次数的计算方式为：

Ribbon重试次数(包含首次)=1+ribbon.MaxAutoRetries+ribbon.MaxAutoRetriesNextServer+(ribbon.MaxAutoRetries*ribbon.MaxAutoRetriesNextServer)

以上图中的Ribbon配置为例子，Ribbon的重试次数=1+(1+1+1)*(30000+10000)，所以Hystrix的超时配置应该>=160000毫秒。在Ribbon超时但Hystrix没有超时的情况下，Ribbon便会采取重试机制；而重试期间如果时间超过了Hystrix的超时配置则会立即被熔断（fallback）。
