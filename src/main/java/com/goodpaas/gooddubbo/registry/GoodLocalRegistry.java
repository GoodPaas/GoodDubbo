/**
 * @Copyright: 2016-2016 <a href="https://github.com/goodpaas/gooddubbo">gooddubbo</a>. All rights reserved.
 */
package com.goodpaas.gooddubbo.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.registry.NotifyListener;
import com.alibaba.dubbo.registry.Registry;

/**
 * @author gper
 *
 * @date 2016年11月6日
 */
public class GoodLocalRegistry implements Registry {

	public final static String GREMOTE_KEY = "gremote";
	private final static String DEFAULT_ROOT = "dubbo";
	private final static String ADDRESS_KEY = "$address";
	private final static String INTERFACE_KEY = "$interface";
	private final static String PROTOCOL_KEY = "$protocol";
	private final static String URL_KEY = "$protocol://$address/$interface?anyhost=true&interface=$interface";

	private Map<String, List<URL>> urlMap = new ConcurrentHashMap<String, List<URL>>();

	private URL registryUrl;

	private final String root;

	public GoodLocalRegistry(URL url) {
		this.registryUrl = url;
		String group = url.getParameter(Constants.GROUP_KEY, DEFAULT_ROOT);
		if (!group.startsWith(Constants.PATH_SEPARATOR)) {
			group = Constants.PATH_SEPARATOR + group;
		}
		if (!group.endsWith(Constants.PATH_SEPARATOR)) {
			group = group + Constants.PATH_SEPARATOR;
		}
		this.root = group;
	}

	private String toServicePath(URL url) {
		return root + url.getServiceInterface();
	}

	@Override
	public void destroy() {

	}

	@Override
	public URL getUrl() {
		return this.registryUrl;
	}

	@Override
	public boolean isAvailable() {
		return true;
	}

	@Override
	public List<URL> lookup(URL url) {
		return null;
	}

	@Override
	public void register(URL url) {
		System.out.println(url.toFullString());
	}

	@Override
	public void subscribe(URL url, NotifyListener listener) {
		if (url == null) {
			throw new IllegalArgumentException("notify url == null");
		}
		if (listener == null) {
			throw new IllegalArgumentException("notify listener == null");
		}
		String protocol = url.getProtocol();
		if (Constants.CONSUMER_PROTOCOL.equals(protocol)) {
			String service = toServicePath(url);
			List<URL> urlList = getServiceUrl(url, service);
			listener.notify(urlList);
		}
	}

	private List<URL> getServiceUrl(URL consumerUrl, String service) {
		List<URL> urlList = this.urlMap.get(service);
		if (urlList == null) {
			urlList = new ArrayList<URL>();
			String iface = consumerUrl.getServiceInterface();
			String gremote = consumerUrl.getParameter(GREMOTE_KEY);
			String protocol = consumerUrl.getParameter(Constants.PROTOCOL_KEY,Constants.DEFAULT_PROTOCOL);
			if (gremote == null || gremote.trim().length() == 0) {
				throw new IllegalStateException("dubbo:reference dubbo:parameter key=\"gremote\" is not set");
			}
			gremote = gremote.replace("-", ".");
			String address = this.registryUrl.getParameter(gremote);
			if (address == null || address.trim().length() == 0) {
				throw new IllegalStateException(
						"dubbo:registry dubbo:parameter is not set for \"dubbo:reference dubbo:parameter value="
								+ gremote + "\"");
			}
			address = address.trim();
			String[] addressArray = null;
			if (address.indexOf(",") > -1) {
				addressArray = address.split(",");
			} else {
				addressArray = new String[] { address };
			}
			for (int i = 0; i < addressArray.length; i++) {
				String addr = addressArray[i];
				if (!NetUtils.isValidAddress(addr)) {
					throw new IllegalStateException("dubbo:registry dubbo:parameter key=" + gremote + " value="
							+ address + "address is not valid,such as 192.168.1.100:20880,192.168.1.101:20880");
				}
				URL serviceUrl = URL.valueOf(URL_KEY.replace(ADDRESS_KEY, addr).replace(INTERFACE_KEY, iface)
						.replace(PROTOCOL_KEY, protocol));
				urlList.add(serviceUrl);
			}
			this.urlMap.put(service, urlList);
		}
		return urlList;
	}

	@Override
	public void unregister(URL url) {

	}

	@Override
	public void unsubscribe(URL url, NotifyListener notiry) {

	}

}
