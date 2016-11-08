/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.demo.simple.hessian;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.goodpaas.gooddubbo.demo.DemoService;

/**
 * @author gper
 *
 */
public class MainSpringClient {
	
	public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"demo/simple/hessian_client.xml"});
        context.start();
        DemoService demoService = (DemoService)context.getBean("demoProxy");
        while(true){
	        String res = demoService.sayHello("Tom");
	        System.out.println(res);
	        Thread.sleep(3000);
        }
    }

}
