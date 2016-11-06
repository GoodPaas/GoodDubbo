/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.registry;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;

/**
 * @author gper
 *
 * @date 2016年11月6日
 */
public class GoodLocalRegistryFactory implements RegistryFactory  {

	@Override
	public Registry getRegistry(URL url) {
		return new GoodLocalRegistry(url);
	}

}
