package com.sf.tarsier.mvc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
//			if(StringUtils.isEmpty(marketBase.getMktId())){
//				request = new QueryMarketBaseRequest();
//				marketBase = (MarketBase) getBaseDAO().selectOne("MarketBaseMapper.selectMarketBaseInfo", request);
//			}
//			users = getBaseDAO().selectList("MarketBaseMapper.selectMarketUsersByMktId", marketBase.getMktId());   
//			calendar.setTime(marketBase.getCreateTime()); 
//			calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) + marketBase.getGroupDuration());
			// 拼团满人数 or 拼团超时
			if(marketBase.getGroupLimit() <= users.size() ||
					calendar.getTime().before(new Date())){
				request.setMktId(createNewMarket());
				marketBase = (MarketBase) getBaseDAO().selectOne("MarketBaseMapper.selectMarketBaseInfo", request);
				response.setMarketBase(marketBase);
				response.setUserCount(0);
				response.setFreeCount(marketBase.getGroupLimit());
				response.setCompletePercent(0);
				response.setUsers(new ArrayList<Map<String, String>>());  
				response.setDeadline(DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
				return ResultUtil.success(response);
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
	
	public static void main(String[] args) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("2015-12-30");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2016, 0, 1);
        Date date2 = cal.getTime();
        boolean compareTo = date.after(date2);
        System.out.println("compareTo : " + compareTo);
	}
}
