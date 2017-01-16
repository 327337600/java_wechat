package org.yangjie.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yangjie.config.AppConfig;
import org.yangjie.dao.AuthDao;
import org.yangjie.entity.AuthBean;
import org.yangjie.util.HttpUtil;
import org.yangjie.util.JsonUtil;

/**
 * 微信网页授权
 * @author YangJie [2016年5月3日 下午5:40:57]
 */
@Service
public class AuthService {
	
	private Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	/** 授权地址 */
	public static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
	/** 通过code换取网页授权access_token地址 */
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	/** 拉取用户信息地址 */
	public static final String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";
	
	/** 自定义分隔符 */
	private final String appkey_separator = "@!@";

	@Value("${config.domain}")
	private String domain;
	
	@Value("${account.appid}")
	private String appid;
	@Value("${account.secret}")
	private String secret;
	
	@Autowired
	private AppConfig appConfig;
	@Autowired
	private AuthDao authDao;
	
	
	/**
	 * 获取微信授权链接
	 * @author YangJie [2016年5月3日 下午6:15:20]
	 * @param appkey
	 * @param type
	 * @param state
	 * @return
	 */
	public String authUrl(String appkey, int type, String state){
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(AUTHORIZE_URL)
			.append("?appid=").append(appid)
			.append("&redirect_uri=").append(domain+"/auth/callback")
			.append("&response_type=").append("code")
			.append("&scope=").append((type==AuthBean.AUTH_TYPE_OPENID ? "snsapi_base" : "snsapi_userinfo"))
			.append("&state=").append(appkey + appkey_separator + state) // 附加信息添加appkey
			.append("#wechat_redirect");
		logger.info("微信授权链接: {}", urlBuilder);
		return urlBuilder.toString();
	}

	/**
	 * 授权回调
	 * @author YangJie [2016年5月3日 下午6:16:25]
	 * @param code
	 * @param state
	 * @return
	 */
	public String authCallback(String code, String state) {
		String appkey = state.split(appkey_separator)[0];
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(ACCESS_TOKEN_URL)
			.append("?appid=").append(appid)
			.append("&secret=").append(secret)
			.append("&code=").append(code)
			.append("&grant_type=").append("authorization_code");
		String result = HttpUtil.get(urlBuilder.toString());
		logger.info("微信授权返回数据: {}", result);
		AuthBean authBean = JsonUtil.toObject(result, AuthBean.class);
		if (authBean.getOpenid()==null || authBean.getOpenid().trim().isEmpty()) {
			return null;
		}
		authDao.addOpenid(authBean); // 将授权信息存入缓存列表
		String returnUrl = appConfig.getConfig(appkey).getReturnUrl();
		StringBuilder returnBuilder = new StringBuilder();
		returnBuilder.append(returnUrl)
			.append("?openid=").append(authBean.getOpenid())
			.append("&state=").append(state.substring(appkey.length() + appkey_separator.length()));
		logger.info("授权成功后回调业务地址: {}", returnBuilder);
		return returnBuilder.toString();
	}
	
	/**
	 * 判断openid是否真实 (防止业务回调地址被攻击)
	 * @author YangJie [2016年5月3日 下午7:37:19]
	 * @param openid
	 * @return
	 */
	public boolean isExist(String openid){
		boolean flag = authDao.isExist(openid);
		logger.info("验证授权openid状态: {}", flag);
		return flag;
	}
	
	/**
	 * 拉取微信用户信息(暂为实现)
	 * 只有之前授权是scope传入snsapi_userinfo才可调用
	 * 由于授权成功后马上调用此接口(如果需要)
	 * 直接使用之前的access_token, 暂不考虑失效问题(有效期2小时)
	 * 此处有一个refresh_token机制, 刷新后access_token有效期达到30天, 还不清楚有什么用
	 * @author YangJie [2016年5月5日 下午9:03:36]
	 * @param openid
	 */
	public void getUserinfo(String openid){

	}
	
}