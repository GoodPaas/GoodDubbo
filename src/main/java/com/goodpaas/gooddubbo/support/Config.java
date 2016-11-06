/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.utils.NetUtils;

/**
 * @author gper
 *
 * @date 2016年11月6日
 */
public class Config {
	
	protected String applicationName;
	protected String registryProtocol;
	protected String registryAddress;
	protected String rpcProtocol = Constants.DEFAULT_PROTOCOL;
	protected Map<String,String> registryParameterMap = new HashMap<String,String>();
	
	public Config putRegistryParameter(String key,String value){
		this.registryParameterMap.put(key, value);
		return this;
	}
	
	public Map<String,String> getRegistryParameters(){
		return this.registryParameterMap;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getRegistryProtocol() {
		return registryProtocol;
	}

	public void setRegistryProtocol(String registryProtocol) {
		this.registryProtocol = registryProtocol;
	}

	public String getRegistryAddress() {
		return registryAddress;
	}

	public void setRegistryAddress(String registryAddress) {
		checkAddress(registryAddress);
		this.registryAddress = registryAddress;
	}

	public String getRpcProtocol() {
		return rpcProtocol;
	}

	public void setRpcProtocol(String rpcProtocol) {
		this.rpcProtocol = rpcProtocol;
	}

	protected void checkAddressList(List<String> addressList) {
		if (addressList == null || addressList.size() == 0) {
			throw new IllegalStateException("serviceAddressList is null");
		}
		for (String address : addressList) {
			checkAddress(address);
		}
	}

	protected void checkAddress(String address) {
		if (!NetUtils.isValidAddress(address)) {
			throw new IllegalStateException("remote service address:" + address
					+ " is not valid,such as 192.168.1.100:20880,192.168.1.101:20880");
		}
	}

}
