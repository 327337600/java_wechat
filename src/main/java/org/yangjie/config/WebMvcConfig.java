package org.yangjie.config;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * web mvc 配置类
 * @author YangJie [2015年11月4日 下午1:30:08]
 */
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override // 默认首页
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("/index.html");
	}
	
}
