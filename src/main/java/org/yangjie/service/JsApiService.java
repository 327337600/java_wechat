package org.yangjie.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yangjie.dao.JsApiDao;
import org.yangjie.entity.JsApiConfig;
import org.yangjie.entity.TicketBean;
import org.yangjie.util.HttpUtil;
import org.yangjie.util.JsonUtil;
import org.yangjie.util.RandomUtil;


/**
 * 微信jsapi相关
 * @author YangJie [2016年5月3日 下午5:40:57]
 */
@Service
public class JsApiService {
	
	private Logger logger = LoggerFactory.getLogger(JsApiService.class);
	
	/** 获取jsapi_ticket地址 */
	public static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	
	@Value("${account.appid}")
	private String appid;
	
	@Autowired
	private JsApiDao jsApiDao;
	@Autowired
	private TokenService tokenService;
	
	
	/**
	 * 获取jsapi_ticket
	 * @author YangJie [2016年5月4日 下午3:37:56]
	 * @param appkey
	 * @return
	 */
	public String getTicket() {
		TicketBean ticketBean = jsApiDao.getTicket();
		// 判断当前ticket是否在有效期内
		if (ticketBean!=null && ticketBean.getTicket()!=null) {
			if((System.currentTimeMillis()-ticketBean.getUpdateTime().getTime())/1000 < (ticketBean.getExpiresIn()-300)){
				logger.info("返回有效期内的jsapi_ticket: {}", ticketBean.getTicket());
				return ticketBean.getTicket();
			}
		}
		// 如果没有ticket信息或者已经过期, 重新从api获取
		String accessToken = tokenService.getAccessToken();
		if (accessToken != null && !accessToken.trim().isEmpty()) {
			StringBuilder urlBuilder = new StringBuilder(JSAPI_TICKET_URL);
			urlBuilder.append("?access_token=").append(accessToken);
			urlBuilder.append("&type=").append("jsapi");
			logger.info("获取jsapi_ticket请求地址: {}", urlBuilder);
			String result = HttpUtil.get(urlBuilder.toString());
			logger.info("获取jsapi_ticket返回数据: {}", result);
			ticketBean = JsonUtil.toObject(result, TicketBean.class);
			if (ticketBean!=null && ticketBean.getTicket()!=null) {
				ticketBean.setUpdateTime(new Date());
				jsApiDao.setTicket(ticketBean); // 缓存获取的ticket信息
				logger.info("返回新获取的jsapi_ticket: {}", ticketBean.getTicket());
				return ticketBean.getTicket();
			}
		}
		logger.error("获取jsapi_ticket信息失败!, 返回null");
		return null;
	}
	
	/**
	 * 获取jsapi初始化参数
	 * @author YangJie [2016年5月5日 上午11:49:23]
	 * @param url
	 * @return
	 */
	public JsApiConfig getConfig(String url){
		// 去除url中#部分(或是由微信等添加的标识信息)
		url = url.indexOf("#")>0 ? url.substring(0, url.indexOf("#")) : url;
		String jsapiTicket = getTicket();
		if (jsapiTicket != null && !jsapiTicket.trim().isEmpty()) {
			// 封装加密参数
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("jsapi_ticket", jsapiTicket);
			paramMap.put("timestamp", getTimeStamp());
			paramMap.put("noncestr", getNonceStr());
			paramMap.put("url", url); // 当前网页的URL，不包含#及其后面部分
			// 封装js初始化信息
			JsApiConfig jsapiConfig = new JsApiConfig();
			jsapiConfig.setAppId(appid);
			jsapiConfig.setTimestamp(paramMap.get("timestamp"));
			jsapiConfig.setNonceStr(paramMap.get("noncestr"));
			jsapiConfig.setSignature(getJsSign(paramMap));
			return jsapiConfig;
		}
		return null;
	}
	
	/**
	 * 获取时间戳
	 * @return 精确到秒
	 * @author YangJie
	 * @createTime 2015年3月26日 下午5:12:47
	 */
	private String getTimeStamp(){
		return String.valueOf(System.currentTimeMillis()/1000);
	}
	
	/**
	 * 获取随机字符串
	 * @return 32字符以内
	 * @author YangJie
	 * @createTime 2015年3月26日 下午5:12:47
	 */
	private String getNonceStr(){
		return RandomUtil.getRandomStr(32);
	}
	
	/**
	 * 获取微信jsapi 初始化签名(sha1)
	 * @author YangJie [2016年3月10日 下午6:17:14]
	 * @param paramMap
	 * @return
	 */
	private String getJsSign(Map<String, String> paramMap) {
		List<String> keyList = new ArrayList<String>(paramMap.keySet());	// 获取参数key
		Collections.sort(keyList);	// key 排序
		StringBuffer paramBuffer = new StringBuffer(); 
		for (String key : keyList) {	// 循环封装非空参数
			if (key != null && paramMap.get(key)!=null && !paramMap.get(key).toString().isEmpty()) {
				paramBuffer.append(key).append("=").append(paramMap.get(key)).append("&");
			}
		} // 去掉最后一个&
		String paramStr = paramBuffer.substring(0, paramBuffer.length()-1);
		logger.info("微信jsapi签名字符串: {}", paramStr);
		return DigestUtils.sha1Hex(paramStr);
	}
	
}
