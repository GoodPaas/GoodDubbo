/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.demo.simple;

import com.goodpaas.gooddubbo.GConstants;
import com.goodpaas.gooddubbo.demo.DemoService;
import com.goodpaas.gooddubbo.support.GLocalClientConfig;
import com.goodpaas.gooddubbo.support.GoodSupportFactory;

/**
 * @author gper
 *
 * @date 2016年11月6日
 */
public class MainClient {
	
	public static void main(String[] args) throws Exception {
        GLocalClientConfig config = new GLocalClientConfig();
        config.setRpcProtocol(GConstants.HESSIAN_RPC_PROTOCOL);
        config.addServiceAddress("127.0.0.1:20880").addServiceAddress("127.0.0.1:20881");
        DemoService demoService = GoodSupportFactory.createGoodProxy(config, DemoService.class);
        while(true){
	        String res = demoService.sayHello("Tom");
	        System.out.println(res);
	        Thread.sleep(3000);
        }
    }

}
