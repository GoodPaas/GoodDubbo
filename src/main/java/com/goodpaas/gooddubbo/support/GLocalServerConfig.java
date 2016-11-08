/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.support;

import com.goodpaas.gooddubbo.GConstants;

/**
 * @author gper
 *
 */
public class GLocalServerConfig extends ServerConfig{
	
	private static final String DEFAULT_APPNAME = "default-server";
	
	public GLocalServerConfig(){
		this.applicationName = DEFAULT_APPNAME;
		this.registryProtocol = GConstants.GLOCAL_PROCOTOL;
	}

}
