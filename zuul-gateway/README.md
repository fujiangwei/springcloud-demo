4、网关全局变量配置
　　4.1 URL路径匹配
复制代码
# URL pattern
# 使用路径方式匹配路由规则。
# 参数key结构： zuul.routes.customName.path=xxx
# 用于配置路径匹配规则。
# 其中customName自定义。通常使用要调用的服务名称，方便后期管理
# 可使用的通配符有： * ** ?
# ? 单个字符
# * 任意多个字符，不包含多级路径
# ** 任意多个字符，包含多级路径
zuul.routes.eureka-application-service.path=/api/**
# 参数key结构： zuul.routes.customName.url=xxx
# url用于配置符合path的请求路径路由到的服务地址。
zuul.routes.eureka-application-service.url=http://127.0.0.1:8080/
复制代码
　　4.2 服务名称匹配
复制代码
# service id pattern 通过服务名称路由
# key结构 ： zuul.routes.customName.path=xxx
# 路径匹配规则
zuul.routes.eureka-application-service.path=/api/**
# key结构 ： zuul.routes.customName.serviceId=xxx
# serviceId用于配置符合path的请求路径路由到的服务名称。
zuul.routes.eureka-application-service.serviceId=eureka-application-service
复制代码
　　服务名称匹配也可以使用简化的配置：

# simple service id pattern 简化配置方案
# 如果只配置path，不配置serviceId。则customName相当于服务名称。
# 符合path的请求路径直接路由到customName对应的服务上。
zuul.routes.eureka-application-service.path=/api/**
　　4.3 路由排除配置
复制代码
# ignored service id pattern
# 配置不被zuul管理的服务列表。多个服务名称使用逗号','分隔。
# 配置的服务将不被zuul代理。
zuul.ignored-services=eureka-application-service

# 此方式相当于给所有新发现的服务默认排除zuul网关访问方式，只有配置了路由网关的服务才可以通过zuul网关访问
# 通配方式配置排除列表。
zuul.ignored-services=*
# 使用服务名称匹配规则配置路由列表，相当于只对已配置的服务提供网关代理。
zuul.routes.eureka-application-service.path=/api/**

# 通配方式配置排除网关代理路径。所有符合ignored-patterns的请求路径都不被zuul网关代理。
zuul.ignored-patterns=/**/test/**
zuul.routes.eureka-application-service.path=/api/**
复制代码
　　4.4 路由前缀配置
复制代码
# prefix URL pattern 前缀路由匹配
# 配置请求路径前缀，所有基于此前缀的请求都由zuul网关提供代理。
zuul.prefix=/api
# 使用服务名称匹配方式配置请求路径规则。
# 这里的配置将为：http://ip:port/api/appservice/**的请求提供zuul网关代理，可以将要访问服务进行前缀分类。
# 并将请求路由到服务eureka-application-service中。
zuul.routes.eureka-application-service.path=/appservice/**



> https://www.cnblogs.com/jing99/p/11696192.html