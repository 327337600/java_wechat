package org.yangjie.util;

import java.io.File;
import java.io.InputStream;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * xml处理工具类
 * @author YangJie [2014年11月13日 下午8:35:48]
 */
public class XmlUtil {

	public static XmlMapper xmlMapper = new XmlMapper();
	
	
	/**
	 * xml转对象
	 * @author YangJie [2015年11月4日 下午1:52:59]
	 * @param xml
	 * @param valueType
	 * @return
	 */
	public static <T> T toObject(String xml, Class<T> valueType) {
		try {
			return xmlMapper.readValue(xml, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * xml文件转对象
	 * @author YangJie [2015年11月4日 下午1:52:59]
	 * @param xml
	 * @param valueType
	 * @return
	 */
	public static <T> T toObject(File file, Class<T> valueType) {
		try {
			return xmlMapper.readValue(file, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * xml文件转对象(处理复杂类型对象) 
	 * List<bean> : json, List.class, Bean.class
	 * Map<bean1, bean2> : json, Map.class, Bean1.class, Bean2.class
	 * @author YangJie [2015年11月9日 下午4:19:04]
	 * @param json
	 * @param valueTypeRef
	 * @return
	 */
	public static <T> T toObject(InputStream is, Class<?> collectionClass, Class<?>... elementClasses) {
		try {
			return xmlMapper.readValue(is, xmlMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
