package org.yangjie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yangjie.entity.JsApiConfig;
import org.yangjie.service.JsApiService;

/**
 * 微信js api
 * @author YangJie [2016年5月3日 下午3:47:55]
 */
@RestController
@RequestMapping("/jsapi")
public class JsApiController{

	@Autowired
	private JsApiService jsApiService;
	
	
	/**
	 * 获取jsapi_ticket
	 * @author YangJie [2017年1月16日 下午3:45:06]
	 * @return
	 */
	@RequestMapping(value="/ticket", method=RequestMethod.GET)
	public String getTicket(){
		return jsApiService.getTicket();
	}
	
	/**
	 * 获取jsapi初始化参数
	 * @author YangJie [2017年1月16日 下午3:47:21]
	 * @param url 需要初始化页面的url
	 * @return
	 */
	@RequestMapping(value="/config", method=RequestMethod.GET)
	public JsApiConfig getConfig(@RequestParam String url){
		return jsApiService.getConfig(url);
	}
	
}