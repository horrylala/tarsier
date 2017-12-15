package com.sf.tarsier.mvc.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * 查询拼团信息
	 * @param request
	 * @return
	 */
	public Result<Object> queryMarketBaseInfo(QueryMarketBaseRequest request){
		if(!StringUtils.isEmpty(request.getMktId()))
		{
			//查询旧团信息
			return queryMaretByMktId(request);
		}
		
		//当没有传市场ID时，查询当前有效的一个团，或创建新团
		MarketBase marketBase = (MarketBase) getBaseDAO().selectOne("MarketBaseMapper.selectMarketBaseInfo", null);
		if(StringUtils.isEmpty(marketBase.getMktId())){
			//如果当前没有有效的集货拼团，就创建一个
			request.setMktId(createNewMarket());
			marketBase = (MarketBase) getBaseDAO().selectOne("MarketBaseMapper.selectMarketBaseInfo", request);
		}
		
		QueryMarketBaseResponse response = selectMarketInfo(marketBase);
		return ResultUtil.success(response);
	}
	
	/**
	 * 存在MKTID的直接返回
	 * @param request
	 * @return
	 */
	private Result<Object> queryMaretByMktId(QueryMarketBaseRequest request){
		MarketBase marketBase = (MarketBase) getBaseDAO().selectOne("MarketBaseMapper.selectMarketBaseInfo", request);
		if(null== marketBase || StringUtils.isEmpty(marketBase.getMktId())){
			return ResultUtil.error("查询的集货拼团不存在，请重试！","marteNull");
		}
		
		String marketBaseJson = JSON.toJSONString(marketBase);
		logger.info("marketBase :{}", marketBaseJson);
		
		QueryMarketBaseResponse response = selectMarketInfo(marketBase);
		return ResultUtil.success(response);
	}
	
	/**
	 * 补充首页集货信息
	 * @param marketBase
	 * @return
	 */
	private QueryMarketBaseResponse selectMarketInfo(MarketBase marketBase)
	{
		QueryMarketBaseResponse response = new QueryMarketBaseResponse();
		// 根据mktId查询参团的人数和头像地址
		List<Map<String, String>> users = getBaseDAO().selectList("MarketBaseMapper.selectMarketUsersByMktId", marketBase.getMktId());
		response.setMarketBase(marketBase);
		response.setUserCount(users.size());
		response.setUsers(users);
		
		//还差多少人成团
		response.setFreeCount(marketBase.getGroupLimit() - users.size());
		//完成百分比
		response.setCompletePercent(100 * users.size()/marketBase.getGroupLimit());
		// 计算截止日期
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(marketBase.getCreateTime()); 
		calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) + marketBase.getGroupDuration());  
		response.setDeadline(DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
		return response;
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
