package com.springcloud.demo.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author
 * @Description: 当我们的后端服务出现异常的时候，我们不希望将异常抛出给最外层，期望服务可以自动进行一降级。
 * Zuul给我们提供了这样的支持。当某个服务出现异常时，直接返回我们预设的信息。
 * 我们通过自定义的fallback方法，并且将其指定给某个route来实现该route访问出问题的熔断处理。
 * 主要继承ZuulFallbackProvider接口来实现，ZuulFallbackProvider默认有两个方法，一个用来指明熔断拦截哪个服务，一个定制返回内容。
 * @Author:
 * @Version:
 * @Date 2021/9/7 10:46
 */
@Component
public class ProviderFallback implements FallbackProvider {
    /**
     * 定义那个路由进行熔断
     *
     * @return
     */
    public String getRoute() {
        return "microservice-provider-ha-demo";
    }

    /**
     * 断路出现时处理请求响应
     *
     * @param route
     * @param cause
     * @return
     */
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause != null && cause.getCause() != null) {
            System.out.println(cause.getCause().getMessage());
        }

        return new ClientHttpResponse() {
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            public int getRawStatusCode() throws IOException {
                return 200;
            }

            public String getStatusText() throws IOException {
                return "OK";
            }

            public void close() {

            }

            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("The service is unavailable.".getBytes());
            }

            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
