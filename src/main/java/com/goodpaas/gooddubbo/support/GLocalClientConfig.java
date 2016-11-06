/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.support;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.goodpaas.gooddubbo.GConstants;
import com.goodpaas.gooddubbo.registry.GoodLocalRegistry;

/**
 * @author gper
 *
 * @date 2016年11月6日
 */
public class GLocalClientConfig extends ClientConfig {

	private static final String DEFAULT_APPNAME = "default-client";
	private static final String GLOCAL_REGISTRY_ADDRESS = "0.0.0.0:0";
	private static final AtomicInteger addressNumber = new AtomicInteger(0);
	private List<String> serviceAddressList = new ArrayList<String>();
	private String gremoteKey;

	public GLocalClientConfig() {
		this.gremoteKey = GoodLocalRegistry.GREMOTE_KEY+"_"+addressNumber.incrementAndGet();
		this.registryAddress = GLOCAL_REGISTRY_ADDRESS;
		this.registryProtocol = GConstants.GLOCAL_PROCOTOL;
		this.applicationName = DEFAULT_APPNAME;
	}
	
	public GLocalClientConfig addServiceAddress(String address) {
		checkAddress(address);
		this.serviceAddressList.add(address);
		return this;
	}

	public List<String> getServiceAddressList() {
		return serviceAddressList;
	}

	public void addServiceAddressList(List<String> serviceAddressList) {
		if(serviceAddressList != null){
			this.serviceAddressList.addAll(serviceAddressList);
		}
	}
	
	String getGRemoteKey(){
		return this.gremoteKey;
	}

}
