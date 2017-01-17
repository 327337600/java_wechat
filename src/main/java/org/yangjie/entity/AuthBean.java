package org.yangjie.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthBean {
	
	/** 授权类型 - 只需要openid */
	public static final int AUTH_TYPE_OPENID = 1;
	/** 授权类型 - 需要userinfo */
	public static final int AUTH_TYPE_USERINFO = 2;
	
	/** 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID */
	private String openid;
	
	/** 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同 */
	@JsonProperty("access_token")
	private String accessToken;
	
	/** access_token接口调用凭证超时时间，单位（秒） */
	@JsonProperty("expires_in")
	private int expiresIn;
	
	/** 用户刷新access_token */
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	/** 用户授权的作用域，使用逗号（,）分隔 */
	private String scope;
	
	/** 区分同一用户，对同一个微信开放平台下的不同应用 */
	private String unionid;
	
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

}