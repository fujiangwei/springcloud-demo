package com.springcloud.demo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author
 * @Description:
 *  Zuul大部分功能都是通过过滤器来实现的，这些过滤器类型对应于请求的典型生命周期。
 *      PRE： 这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
 *      ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
 *      POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
 *      ERROR：在其他阶段发生错误时执行该过滤器。
 *      除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。例如，我们可以定制一种STATIC类型的过滤器，直接在Zuul中生成响应，而不将请求转发到后端的微服务。
 * @Author:
 * @Version:
 * @Date 2021/9/7 10:32
 */
public class MyFilter extends ZuulFilter {

    /**
     * 定义filter的类型，有pre、route、post、error四种
     * @return
     */
    public String filterType() {
        System.out.println("======================= run filterType =======================");
        return "pre";
    }

    /**
     * 定义filter的顺序，数字越小表示顺序越高，越先执行
     * @return
     */
    public int filterOrder() {
        System.out.println("======================= run filterOrder =======================");
        return 10;
    }

    /**
     * 表示是否需要执行该filter，true表示执行，false表示不执行
     * @return
     */
    public boolean shouldFilter() {
        System.out.println("======================= run shouldFilter =======================");
        return true;
    }

    /**
     * filter需要执行的具体操作
     * @return
     * @throws ZuulException
     */
    public Object run() throws ZuulException {
        System.out.println("======================= run run =======================");
        return "======================= run MyFilter =======================";
    }
}
