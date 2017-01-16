package org.yangjie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yangjie.service.TokenService;

/**
 * 微信通用access_token
 * @author YangJie [2016年5月3日 下午3:47:55]
 */
@RestController
@RequestMapping("/token")
public class TokenController{

	@Autowired
	private TokenService tokenService;
	
	
	/**
	 * 获取可用的AccessToken
	 * @author YangJie [2017年1月16日 下午3:07:36]
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String getToken(){
		return tokenService.getAccessToken();
	}
	
}