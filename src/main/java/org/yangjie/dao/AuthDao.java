package org.yangjie.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.yangjie.entity.AuthBean;

@Repository
public class AuthDao {

	 // 缓存数量
	private final int cache_size =80;
	 // 缓存最近的授权信息, 用于验证授权回调的真实性, 模拟二维表存储
	private List<Map<String, AuthBean>> cacheList = new ArrayList<Map<String, AuthBean>>(cache_size);
	
	
	/**
	 * 将授权信息添加到缓存列表
	 * @author YangJie [2016年5月3日 下午7:17:03]
	 * @param openid
	 */
	public boolean addOpenid(AuthBean authBean){
		Map<String, AuthBean> cacheMap = new HashMap<String, AuthBean>(1);
		cacheMap.put(authBean.getOpenid(), authBean); // 加入缓存
		cacheList.add(cacheMap); // 将新记录追加到数组最后
		if (cacheList.size() >= cache_size) {
			cacheList.remove(0); // 如果缓存满清理第一条
		} // 此处有并发bug
		return true;
	}
	
	/**
	 * 判断openid是否真实 (防止业务回调地址被攻击)
	 * @author YangJie [2016年5月3日 下午7:37:19]
	 * @param openid
	 * @return
	 */
	public boolean isExist(String openid){
		for(Map<String, AuthBean> cacheMap : cacheList){
			if(cacheMap.get(openid) != null){
				return true; // 如果存在直接返回true
			}
		}
		return false;
	}
	
}