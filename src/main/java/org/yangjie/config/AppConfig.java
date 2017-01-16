package org.yangjie.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class AppConfig {
	
	private Map<String, AppSetting> appList;

	public Map<String, AppSetting> getAppList() {
		return appList;
	}

	public void setAppList(Map<String, AppSetting> appList) {
		this.appList = appList;
	}
	
	/**
	 * 获取业务配置信息
	 * @author YangJie [2016年5月3日 下午5:38:15]
	 * @param appkey
	 * @return
	 */
	public AppSetting getConfig(String appkey){
		if (appkey==null || appkey.trim().isEmpty()) {
			return null;
		}
		if (appList==null || appList.isEmpty()) {
			return null;
		}
		return appList.get(appkey);
	}
	
}