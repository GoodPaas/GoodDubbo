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
public class ClientConfig extends Config {

	protected Map<String, String> referenceParameterMap = new HashMap<String, String>();

	public ClientConfig putReferenceParameter(String key, String value) {
		this.referenceParameterMap.put(key, value);
		return this;
	}

	public Map<String, String> getReferenceParameters() {
		return this.referenceParameterMap;
	}

}
