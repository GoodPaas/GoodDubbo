/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.demo.simple;

import com.goodpaas.gooddubbo.demo.Demo2ServiceImpl;
import com.goodpaas.gooddubbo.demo.DemoService;
import com.goodpaas.gooddubbo.support.GLocalServerConfig;
import com.goodpaas.gooddubbo.support.GoodSupportFactory;

/**
 * @author gper
 *
 */
public class MainService2 {
	
	public static void main(String[] args) throws Exception {
        DemoService serviceImpl2 = new Demo2ServiceImpl();
		GLocalServerConfig config2 = new GLocalServerConfig();
        config2.setProtocolPort(20881);
        GoodSupportFactory.exportService(config2, DemoService.class,serviceImpl2);
        System.in.read(); // 按任意键退出
    }
}
