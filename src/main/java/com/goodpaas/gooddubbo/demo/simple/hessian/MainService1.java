/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.demo.simple.hessian;

import com.goodpaas.gooddubbo.GConstants;
import com.goodpaas.gooddubbo.demo.Demo1ServiceImpl;
import com.goodpaas.gooddubbo.demo.DemoService;
import com.goodpaas.gooddubbo.support.GLocalServerConfig;
import com.goodpaas.gooddubbo.support.GoodSupportFactory;

/**
 * @author gper
 *
 */
public class MainService1 {
	
	public static void main(String[] args) throws Exception {
		DemoService serviceImpl1 = new Demo1ServiceImpl();
		GLocalServerConfig config1 = new GLocalServerConfig();
		config1.setRpcProtocol(GConstants.HESSIAN_RPC_PROTOCOL);
        config1.setProtocolPort(20880);
        GoodSupportFactory.exportService(config1, DemoService.class,serviceImpl1);
 
        
        System.in.read(); // 按任意键退出
    }

}
