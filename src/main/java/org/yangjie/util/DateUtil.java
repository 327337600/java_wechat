package org.yangjie.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具类 
 * 非数据库操作 & 非实例bean操作
 * @author YangJie [2015年11月4日 上午11:43:41]
 */
public class DateUtil {

	
	public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat SHORT_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat SHORT_MILLISECOND_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	/**
	 * 格式化日期
	 * @author YangJie [2016年2月23日 下午3:37:05]
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		return DATE_FORMAT.format(date);
	}
	
	/**
	 * 格式化日期+时间
	 * @author YangJie [2016年2月23日 下午3:37:05]
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date){
		return TIME_FORMAT.format(date);
	}

}