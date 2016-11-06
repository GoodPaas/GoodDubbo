/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.demo.simple;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gper
 *
 * @date 2016年11月6日
 */
public class MainSpringService1 {
	
	public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext(new String[] {"demo/simple/service1.xml"});
        context1.start();
 
        System.in.read(); // 按任意键退出
    }

}
