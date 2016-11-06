/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.support;

import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.goodpaas.gooddubbo.registry.GoodLocalRegistry;

/**
 * @author gper
 *
 * @date 2016年11月6日
 */
public class GoodSupportFactory {
	
	
	public static <T> T createProxy(ClientConfig config,Class<T> iface){
		ApplicationConfig appConfig = createApplicationConfig(config);
		
		RegistryConfig registryConfig = createRegistryConfig(config);
		
		String protocol = config.getRpcProtocol();
		Map<String,String> refParameterMap = config.getReferenceParameters();
		ReferenceConfig<T> referenceConfig = new ReferenceConfig<T>();
		referenceConfig.setProtocol(protocol);
		referenceConfig.setInterface(iface);
		referenceConfig.setApplication(appConfig);
		referenceConfig.setRegistry(registryConfig);
		if(refParameterMap != null && refParameterMap.size() > 0){
			referenceConfig.setParameters(refParameterMap);
		}
		
		return referenceConfig.get();
	}
	
	public static <T> T createGoodProxy(GLocalClientConfig config,Class<T> iface){
		config.putReferenceParameter(GoodLocalRegistry.GREMOTE_KEY, config.getGRemoteKey());
		StringBuilder addrSB = new StringBuilder();
		List<String> addrList = config.getServiceAddressList();
		config.checkAddressList(addrList);
		for(String addr : addrList){
			addrSB.append(addr).append(",");
		}
		addrSB.deleteCharAt(addrSB.length()-1);
		config.putRegistryParameter(config.getGRemoteKey(), addrSB.toString());
		return createProxy(config,iface);
	}
	
	public static void exportService(ServerConfig config,Class<?> iface,Object serviceImpl){
		checkService(iface,serviceImpl);
		ApplicationConfig appConfig = createApplicationConfig(config);
		
		RegistryConfig registryConfig = createRegistryConfig(config);
		
		ProtocolConfig protocolConfig = createProtocolConfig(config);
		
		ServiceConfig serviceConfig = new ServiceConfig();
		serviceConfig.setInterface(iface);
		serviceConfig.setRef(serviceImpl);
		serviceConfig.setApplication(appConfig);
		serviceConfig.setRegistry(registryConfig);
		serviceConfig.setProtocol(protocolConfig);
		
		serviceConfig.export();
	}
	
	private static void checkService(Class<?> iface,Object serviceImpl){
		if(! iface.isAssignableFrom(serviceImpl.getClass())){
			throw new IllegalStateException("serviceImpl:"+serviceImpl.getClass().getName()+" must implement iface:"+iface.getName());
		}
	}
	
	private static ApplicationConfig createApplicationConfig(Config config){
		String appName = config.getApplicationName();
		ApplicationConfig appConfig = new ApplicationConfig();
		appConfig.setName(appName);
		return appConfig;
	}
	
	private static RegistryConfig createRegistryConfig(Config config){
		String registryProtocol = config.getRegistryProtocol();
		String registryAddress = config.getRegistryAddress();
		Map<String,String> regParameterMap = config.getRegistryParameters();
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setProtocol(registryProtocol);
		registryConfig.setAddress(registryAddress);
		if(regParameterMap != null && regParameterMap.size() > 0){
			registryConfig.setParameters(regParameterMap);
		}
		return registryConfig;
	}
	
	private static ProtocolConfig createProtocolConfig(ServerConfig config){
		ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName(config.getRpcProtocol());
        protocol.setHost(config.getProtocolHost());
        protocol.setPort(config.getProtocolPort());
        return protocol;
	}

}
