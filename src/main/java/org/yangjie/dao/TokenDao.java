package org.yangjie.dao;

import org.springframework.stereotype.Repository;
import org.yangjie.entity.TokenBean;

@Repository
public class TokenDao {

	/** 缓存Token信息 */
	private TokenBean tokenBean;

	
	/**
	 * 获取Token信息
	 * @author YangJie [2016年5月4日 下午2:43:53]
	 * @param appid
	 * @return
	 */
	public TokenBean getToken(){
		return tokenBean;
	}
	
	/**
	 * 缓存新获得的token
	 * @author YangJie [2016年5月4日 下午2:45:34]
	 * @param tokenBean
	 * @return
	 */
	public void setToken(TokenBean tokenBean){
		this.tokenBean = tokenBean;
	}
	
}
