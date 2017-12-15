package com.sf.tarsier.mvc.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sf.tarsier.mvc.entity.MarketBase;
import com.sf.tarsier.mvc.service.MarketBaseService;
import com.sf.tarsier.mvc.system.entity.LoggerType;

@Component
public class AutoCreateMarketTask {
	
	private final Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	@Autowired
	private MarketBaseService marketBaseService;

	public void execJob() {
		logger.info("进入自动建团JOB");
		MarketBase marketBase = (MarketBase) marketBaseService.selectOne("MarketBaseMapper.selectMarketBaseInfo", null);
		if(null== marketBase || StringUtils.isEmpty(marketBase.getMktId())){
			logger.info("进入自动建团JOB, uuid = {}",marketBaseService.createNewMarket());
		}
		logger.info("进入自动建团JOB完成");
	}

}
