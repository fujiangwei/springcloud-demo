# Springcloud版本-Greenwich.RELEASE

## 一、Eureka介绍

```
待补充
```

## 二、项目构建

### 1.新增maven项目
> 代码结构如下

![](/media/202108/2021-08-18_095713.png)

### 2.pom依赖
> 父类pom.xml
- 引入spring-cloud-dependencies
```xml
<!--属性配置-->
<properties>
	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	<java.version>1.8</java.version>
	<spring-cloud.version>Greenwich.RELEASE</spring-cloud.version>
	<gson.version>2.8.6</gson.version>
</properties>
<!--依赖版本管理-->
<dependencyManagement>
	<dependencies>
		<!--spring-cloud-dependencies-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-dependencies</artifactId>
			<version>${spring-cloud.version}</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
		<!--gson-->
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>${gson.version}</version>
		</dependency>
	</dependencies>
</dependencyManagement>
```

> EurekaServer模块pom.xml
- 引入netflix-eureka-server模块
```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
	</dependency>
	<!--gson-->
	<!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
	<dependency>
		<groupId>com.google.code.gson</groupId>
		<artifactId>gson</artifactId>
	</dependency>
</dependencies>
```

> 配置文件application.yml

```yaml
# 服务端口
server:
  port: 8080
# Eureka实例配置
eureka:
  instance:
    # 实例主机名
    hostname: localhost
  client:
    # 是否将自己注册到EurekaServer上，默认为true。false表示自己不进行服务注册
    register-with-eureka: false
    # 是否从其他EurekaServer获取注册信息，默认为true。单节点不需要同步其他EurekaServer服务节点的数据，设置为false
    fetch-registry: false
    # 设置EurekaServer的地址
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

> 启动类加上EurekaServer注解开启Eureka

```java
package com.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Aria
 */
@SpringBootApplication
// 启动Eureka注解
@EnableEurekaServer
public class EurekaServerApp {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp.class, args);
    }
}
```

```此时运行EurekaServerApp，启动成功后访问http://localhost/8080，如下：```
> 启动成功

![](/media/202108/2021-08-18_101202.png)

> 访问成功，表示EurekaServer已搭建好

![](/media/202108/2021-08-18_101333.png)

