package com.sf.tarsier.mvc.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sf.tarsier.mvc.entity.MarketBase;
import com.sf.tarsier.mvc.entity.MarketUsers;
import com.sf.tarsier.mvc.entity.QueryMarketBaseRequest;
import com.sf.tarsier.mvc.entity.QueryMarketBaseResponse;
import com.sf.tarsier.mvc.system.base.BaseService;
import com.sf.tarsier.mvc.system.entity.LoggerType;
import com.sf.tarsier.mvc.system.entity.Result;
import com.sf.tarsier.mvc.system.util.ResultUtil;

@Service("marketBaseService")
public class MarketBaseService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	public Result<Object> queryMarketBaseInfo(QueryMarketBaseRequest request){
		QueryMarketBaseResponse response = new QueryMarketBaseResponse();
		MarketBase marketBase = (MarketBase) getBaseDAO().selectOne("MarketBaseMapper.selectMarketBaseInfo", request.getMktId());
		String marketBaseJson = JSON.toJSONString(marketBase);
		logger.info("marketBase :{}", marketBaseJson);
		
		if(Objects.nonNull(request.getMktId())){
			// 拼团超时 或者 拼团满人数
			return ResultUtil.error("100001", "newMarket");
		}

		// 根据mktId查询参团的人数和头像地址
		List<MarketUsers> userList = getBaseDAO().selectList("MarketBaseMapper.selectMarketUsersByMktId", marketBase.getMktId());
		response.setMarketBase(marketBase);
		response.setUserCount(userList.size());
		response.setUsers(userList);
		return ResultUtil.success(response);
	}
		
}
