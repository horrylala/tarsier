package com.sf.tarsier.mvc.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sf.tarsier.mvc.entity.MarketBase;
import com.sf.tarsier.mvc.entity.QueryMarketBaseRequest;
import com.sf.tarsier.mvc.entity.QueryMarketBaseResponse;
import com.sf.tarsier.mvc.system.base.BaseService;
import com.sf.tarsier.mvc.system.entity.LoggerType;
import com.sf.tarsier.mvc.system.entity.Result;
import com.sf.tarsier.mvc.system.util.ResultUtil;

@Service("marketBaseService")
public class MarketBaseService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	@Autowired
	private MarketPropService marketPropService;
	
	public Result<Object> queryMarketBaseInfo(QueryMarketBaseRequest request){
		QueryMarketBaseResponse response = new QueryMarketBaseResponse();
		MarketBase marketBase = (MarketBase) getBaseDAO().selectOne("MarketBaseMapper.selectMarketBaseInfo", request);
		String marketBaseJson = JSON.toJSONString(marketBase);
		logger.info("marketBase :{}", marketBaseJson);

		// 根据mktId查询参团的人数和头像地址
		List<Map<String, String>> users = getBaseDAO().selectList("MarketBaseMapper.selectMarketUsersByMktId", marketBase.getMktId());
		response.setMarketBase(marketBase);
		response.setUserCount(users.size());
		response.setUsers(users);

		response.setFreeCount(marketBase.getGroupLimit() - users.size());
		response.setCompletePercent(100 * users.size()/marketBase.getGroupLimit());
		// 计算截止日期
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(marketBase.getCreateTime()); 
		calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) + marketBase.getGroupDuration());  
		response.setDeadline(DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
		
		if(Objects.nonNull(request) && !StringUtils.isEmpty(request.getMktId())){
			// 拼团满人数
			if(marketBase.getGroupLimit() <= users.size()){
				return ResultUtil.error("100001", "newMarket");
			}
			// 拼团超时
			if(calendar.getTime().compareTo(new Date()) < 0){
				return ResultUtil.error("100002", "newMarket");
			}
		}
		return ResultUtil.success(response);
	}
	
	/**
	 * 创建新团
	 * @return
	 */
	public String createNewMarket()
	{
		Map<String,String> params = marketPropService.selectPropList();
		if(null == params || params.isEmpty()){
			return "";
		}
		String currUuid = (String) getBaseDAO().selectOne("MarketBaseMapper.selectMarketBaseUUID", null);
		params.put("curr_uuid", currUuid);
		logger.info("生成的新团数据 : {} " , JSON.toJSONString(params));
		getBaseDAO().update("MarketBaseMapper.insertMarketBase", params);
		return currUuid;
	}
}
