package com.sf.tarsier.mvc.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sf.tarsier.mvc.entity.MarketBase;
import com.sf.tarsier.mvc.entity.QueryMarketBaseRequest;
import com.sf.tarsier.mvc.system.base.BaseService;
import com.sf.tarsier.mvc.system.entity.LoggerType;
import com.sf.tarsier.mvc.system.entity.Result;
import com.sf.tarsier.mvc.system.util.ResultUtil;

@Service("marketBaseService")
public class MarketBaseService extends BaseService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	public Result<Object> queryMarketBaseInfo(QueryMarketBaseRequest request){
		MarketBase marketBase = (MarketBase) getBaseDAO().selectOne("MarketBaseMapper.selectMarketBaseInfo", request);
		if(Objects.nonNull(request.getMktId())){
			// 拼团超时 或者 拼团满人数
			return ResultUtil.error("100001", "newMarket");
		}
		return ResultUtil.success(marketBase);
	}
		
}
