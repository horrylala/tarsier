package com.sf.tarsier.mvc.system.mapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;

import com.sf.tarsier.mvc.system.entity.LoggerType;

public class RefreshMybatisMapper {
	
	protected static Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	@Autowired
	private SqlSessionFactory mySessionFactory;
	private Resource[] mapperLocations;

	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	public SqlSessionFactory getMySessionFactory() {
		return mySessionFactory;
	}

	public void setMySessionFactory(SqlSessionFactory mySessionFactory) {
		this.mySessionFactory = mySessionFactory;
	}

	private void clearMap(Class<?> classConfig, Configuration configuration,
			String fieldName) throws Exception {
		Field field = classConfig.getDeclaredField(fieldName);
		field.setAccessible(true);
		Map<?, ?> mapConfig = (Map<?, ?>) field.get(configuration);
		mapConfig.clear();
	}

	private void clearSet(Class<?> classConfig, Configuration configuration,
			String fieldName) throws Exception {
		Field field = classConfig.getDeclaredField(fieldName);
		field.setAccessible(true);
		Set<?> setConfig = (Set<?>) field.get(configuration);
		setConfig.clear();
	}

	/**
	 * 清空Configuration中几个重要的缓存
	 * 
	 * @param configuration
	 * @throws Exception
	 */
	private void removeConfig(Configuration configuration) throws Exception {
		Class<?> classConfig = configuration.getClass();
		clearMap(classConfig, configuration, "mappedStatements");
		clearMap(classConfig, configuration, "caches");
		clearMap(classConfig, configuration, "resultMaps");
		clearMap(classConfig, configuration, "parameterMaps");
		clearMap(classConfig, configuration, "keyGenerators");
		clearMap(classConfig, configuration, "sqlFragments");
		clearSet(classConfig, configuration, "loadedResources");
	}

	public void refreshMapper() {
		try {
			Configuration configuration = mySessionFactory.getConfiguration();

			this.removeConfig(configuration);

			if (!ObjectUtils.isEmpty(this.mapperLocations)) {
				for (Resource mapperLocation : this.mapperLocations) {
					if (mapperLocation != null) {
						try {
							XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
									mapperLocation.getInputStream(),
									configuration, mapperLocation.toString(),
									configuration.getSqlFragments());

							xmlMapperBuilder.parse();
						} catch (Exception e) {
							logger.error("Failed to parse mapping resource: '"
									+ mapperLocation + "'", e);
						} finally {
							ErrorContext.instance().reset();
						}
					}
				}
			}

			// step.重新加载
			for (Resource configLocation : mapperLocations) {
				try {
					XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
							configLocation.getInputStream(), configuration,
							configLocation.toString(),
							configuration.getSqlFragments());
					xmlMapperBuilder.parse();
					logger.info("mapper文件[" + configLocation.getFilename()
							+ "]缓存加载成功");
				} catch (IOException e) {
					logger.error("mapper文件[" + configLocation.getFilename()
							+ "]不存在或内容格式不对",e);
					continue;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
