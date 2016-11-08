# 一、GoodDubbo
对阿里的Dubbo进行精简和封装，降低学习、部署和运维的难度，以适应中等规模公司使用.  

GoodDubbo只是对Dubbo进行扩展，兼容原来的API和配置方式，扩展部分也仅仅涉及配置和监控部分，对Dubbo业务数据传输部分并未做任何改动和扩展，所以对框架稳定性完全不受影响；  

GoodDubbo去除了Dubbo的第三方Registry(zookeeper、redis等)，直接将IP配置在客户端，便于小型项目快捷开发；  
# 二、Quick Start
## 1、依赖
Maven项目添加依赖：  
```xml
	<dependency>
		<groupId>com.github.goodpaas</groupId>
		<artifactId>gooddubbo</artifactId>
		<version>0.1.0.snapshot</version>
	</dependency>
	<dependency>
		<groupId>com.alibaba</groupId>
		<artifactId>dubbo</artifactId>
		<version>2.5.3</version>
	</dependency>
```
非Maven项目自己下载jar包：[gooddubbo.jar](https://repo1.maven.org/maven2/com/github/goodpaas/gooddubbo/0.1.1/gooddubbo-0.1.1.jar)  [dubbo.jar](https://repo1.maven.org/maven2/com/alibaba/dubbo/2.5.3/dubbo-2.5.3.jar)  
## 2、Demo Service
创建Service，用于远程调用测试：  
Service接口:  

		package com.goodpaas.gooddubbo.demo;

		public interface DemoService {
			
			public String sayHello(String name);
			
			public String sayHelloWord(String name);

		}
Service实现：  

		package com.goodpaas.gooddubbo.demo;

		public class Demo1ServiceImpl implements DemoService{

			@Override
			public String sayHello(String name) {
				System.out.println("service_1 say hello to "+name);
				return "sayHello service_1 has said to "+name;
			}

			@Override
			public String sayHelloWord(String name) {
				System.out.println("service_1 say hello world to "+name);
				return "sayHelloWord service_1 has said to "+name;
			}

		}

## 3、直接调用API Demo
服务端对外暴露Service:  

		package com.goodpaas.gooddubbo.demo.simple;

		import com.goodpaas.gooddubbo.demo.Demo1ServiceImpl;
		import com.goodpaas.gooddubbo.demo.DemoService;
		import com.goodpaas.gooddubbo.support.GLocalServerConfig;
		import com.goodpaas.gooddubbo.support.GoodSupportFactory;

		public class MainService1 {
			
			public static void main(String[] args) throws Exception {
				DemoService serviceImpl1 = new Demo1ServiceImpl();
				GLocalServerConfig config1 = new GLocalServerConfig();
		        config1.setProtocolPort(20880);
		        GoodSupportFactory.exportService(config1, DemoService.class,serviceImpl1);
		        System.in.read(); // 按任意键退出
		    }
		}

客户端通过代理调用后端Service实现：  

		package com.goodpaas.gooddubbo.demo.simple;

		import com.goodpaas.gooddubbo.demo.DemoService;
		import com.goodpaas.gooddubbo.support.GLocalClientConfig;
		import com.goodpaas.gooddubbo.support.GoodSupportFactory;

		public class MainClient {
			
			public static void main(String[] args) throws Exception {
		        GLocalClientConfig config = new GLocalClientConfig();
		        config.addServiceAddress("127.0.0.1:20880");
		        //多个服务提供方，客户端自动负载均衡
		        //config.addServiceAddress("127.0.0.1:20881");
		        DemoService demoService = GoodSupportFactory.createGoodProxy(config, DemoService.class);
		        while(true){
			        String res = demoService.sayHello("Tom");
			        System.out.println(res);
			        Thread.sleep(3000);
		        }
		    }

		}
## 4、使用Spring Demo
服务端配置:
```xml
	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
	    xsi:schemaLocation="http://www.springframework.org/schema/beans        http://www.springframework.org/schema/beans/spring-beans.xsd        http://code.alibabatech.com/schema/dubbo        http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
	    <!-- DemoService服务端实现 -->
	    <bean id="demoService1" class="com.goodpaas.gooddubbo.demo.Demo1ServiceImpl" />
	    
	    <!-- 服务端集群名称，用于监控和配置管理 -->
	    <dubbo:application name="demo-service" />
	    
	    <!-- 服务端配置注册管理器，管理服务和集群IP等，此处设置protocol="glocal"，不需要配置第三方注册管理器 -->
	    <dubbo:registry protocol="glocal" />
	    
	    <!-- 暴露服务的协议和端口 -->
	    <dubbo:protocol port="20880" />
	    
	    <!-- 对外暴露服务，具体服务实现ref上面的demoService -->
		<dubbo:service interface="com.goodpaas.gooddubbo.demo.DemoService" ref="demoService1" />
	       
	</beans>
```
服务端启动：

		package com.goodpaas.gooddubbo.demo.simple;

		import org.springframework.context.support.ClassPathXmlApplicationContext;

		public class MainSpringService1 {
			
			public static void main(String[] args) throws Exception {
		        ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext(new String[] {"demo/simple/def_service1.xml"});
		        context1.start();
		 
		        System.in.read(); // 按任意键退出
		    }

		}
客户端配置：
```xml
	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
	    xsi:schemaLocation="http://www.springframework.org/schema/beans        http://www.springframework.org/schema/beans/spring-beans.xsd        http://code.alibabatech.com/schema/dubbo        http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
	    <!-- 服务端集群名称，用于监控和配置管理 -->
		<dubbo:application name="demo-client" />
		
		<!-- 客户端配置注册管理器，管理服务和集群IP等，此处为GoodPaas实现的本地注册器，不依赖额外服务 -->
		<dubbo:registry protocol="glocal">
		    <!-- key为要调用的集群名称，最好和服务端application的name对应，value为提供服务的集群ip和端口，多个ip使用都好分割 -->
		    <!-- key值不能重复，否则会抛出异常 -->
		    <dubbo:parameter key="demo-service" value="127.0.0.1:20880,127.0.0.1:20881"/>
	    </dubbo:registry>
	    
		<!-- 客户端调用远程服务的代理，id可以被其他SpringBean ref -->
		<dubbo:reference id="demoProxy" interface="com.goodpaas.gooddubbo.demo.DemoService">
		    <!-- 远程服务集群配置，key必须为gremote，value为上面registry钟配置的key值 -->
		    <dubbo:parameter key="gremote" value="demo-service"/>
	    </dubbo:reference>
		
	</beans>
```
客户端启动：
		
		package com.goodpaas.gooddubbo.demo.simple;

		import org.springframework.context.support.ClassPathXmlApplicationContext;

		import com.goodpaas.gooddubbo.demo.DemoService;

		public class MainSpringClient {
			
			public static void main(String[] args) throws Exception {
		        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"demo/simple/def_client.xml"});
		        context.start();
		        DemoService demoService = (DemoService)context.getBean("demoProxy");
		        while(true){
			        String res = demoService.sayHello("Tom");
			        System.out.println(res);
			        Thread.sleep(3000);
		        }
		    }
		}





















