package com.sf.tarsier.mvc.system.filter;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.alibaba.druid.filter.config.ConfigTools;
import com.sf.tarsier.mvc.system.entity.LoggerType;

/**
 * 资源文件拦截，并对特定配置项进行解密
 */
public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{
	private static final Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException{
		try {
			Set<Map.Entry<Object, Object>> entrySet = props.entrySet();//返回的属性键值对实体
		    for (Map.Entry<Object, Object> entry : entrySet) {
		    	if(StringUtils.isNotBlank((String)entry.getKey())
		    			&& StringUtils.isNotBlank((String)entry.getValue())
		    			&& entry.getValue().toString().startsWith("enc:"))
		    	{
		    		props.setProperty(entry.getKey().toString(), ConfigTools.decrypt(entry.getValue().toString().substring(4)));
		    	}
		    }
		    super.processProperties(beanFactory, props);
		} catch (Exception e) {
			logger.error("读取配置文件进行解密时出错", e);
		}
	}
	
}
