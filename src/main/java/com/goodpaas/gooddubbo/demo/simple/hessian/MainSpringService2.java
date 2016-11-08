/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.demo.simple.hessian;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gper
 *
 */
public class MainSpringService2 {
	
	public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context2 = new ClassPathXmlApplicationContext(new String[] {"demo/simple/hessian_service2.xml"});
        context2.start();
 
        System.in.read(); // 按任意键退出
    }

}
