/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.demo;

/**
 * @author gper
 *
 * @date 2016年11月6日
 */
public class Demo2ServiceImpl implements DemoService{

	@Override
	public String sayHello(String name) {
		System.out.println("service_2 say hello to "+name);
		return "sayHello service_2 has said to "+name;
	}

	@Override
	public String sayHelloWord(String name) {
		System.out.println("service_2 say hello world to "+name);
		return "sayHelloWord service_2 has said to "+name;
	}

}
