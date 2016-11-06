/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.support;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gper
 *
 * @date 2016年11月6日
 */
public class ServerConfig extends Config{
	
	private String protocolHost;
	private int protocolPort = 20880;
	
	protected Map<String,String> serviceParameterMap = new HashMap<String,String>();
	
	public ServerConfig putServiceParameter(String key,String value){
		this.serviceParameterMap.put(key, value);
		return this;
	}
	
	public Map<String,String> getServiceParameters(){
		return this.serviceParameterMap;
	}

	public String getProtocolHost() {
		return protocolHost;
	}

	public void setProtocolHost(String protocolHost) {
		this.protocolHost = protocolHost;
	}

	public int getProtocolPort() {
		return protocolPort;
	}

	public void setProtocolPort(int protocolPort) {
		this.protocolPort = protocolPort;
	}

}
