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
		        //config..addServiceAddress("127.0.0.1:20881");
		        DemoService demoService = GoodSupportFactory.createGoodProxy(config, DemoService.class);
		        while(true){
			        String res = demoService.sayHello("Tom");
			        System.out.println(res);
			        Thread.sleep(3000);
		        }
		    }

		}






















