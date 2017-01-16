package org.yangjie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yangjie.service.AuthService;

/**
 * 微信网页授权
 * @author YangJie [2016年5月3日 下午3:47:55]
 */
@Controller
@RequestMapping("/auth")
public class AuthController{

	@Autowired
	private AuthService authService;
	
	
	/**
	 * 微信网页授权
	 * @author YangJie [2016年5月3日 下午6:13:16]
	 * @param appkey
	 * @param type
	 * @param state
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String auth(@RequestParam String appkey, 
			@RequestParam(required=false, defaultValue="1") int type, 
			@RequestParam(required=false, defaultValue="") String state){
		if (appkey!=null && !appkey.trim().isEmpty()) {
			return "redirect:" + authService.authUrl(appkey, type, state);
		}
		return "/error.html";
	}
	
	/**
	 * 微信网页授权回调
	 * @author YangJie [2016年5月3日 下午6:15:02]
	 * @param code
	 * @param state
	 * @return
	 */
	@RequestMapping(value="/callback", method=RequestMethod.GET)
	public String callback(@RequestParam String code, 
			@RequestParam(required=false) String state){
		if (code!=null && !code.trim().isEmpty()) {
			return "redirect:" + authService.authCallback(code, state);
		}
		return "/error.html";
	}
	
	/**
	 * 验证openid是否存在
	 * @author YangJie [2017年1月16日 下午3:49:19]
	 * @param openid
	 * @return
	 */
	@RequestMapping(value="/exist/{openid}")
	public boolean isExist(@PathVariable("openid")String openid){
		return authService.isExist(openid);
	}
	
}