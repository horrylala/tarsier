package com.sf.tarsier.mvc.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.tarsier.mvc.entity.MarketUsers;
import com.sf.tarsier.mvc.system.base.BaseService;
import com.sf.tarsier.mvc.system.entity.LoggerType;
import com.sf.tarsier.mvc.system.entity.Result;
import com.sf.tarsier.mvc.system.util.ResultUtil;

@Service("marketUsersService")
public class MarketUsersService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	@Autowired
	private MarketBaseService marketBaseService;
	
	private static final String LEAVE_COUNT = "leave_count"; 
	
	/**
	 * <b>功能：保存参加集货拼团，参团信息
	 */
	public Result<Object> saveMarketUsers(MarketUsers users) {
		try {
			String returnMsg = "参团成功！";
			//检查当前要参团的时间周期和参团人员
			@SuppressWarnings("unchecked")
			Map<String,Long> check = (Map<String,Long>)getBaseDAO().selectOne("MarketUsersMapper.selectMarketUsersLimit", users.getMktId());
			if(null==check || check.isEmpty())
			{
				//参团数据不存在
				return ResultUtil.error("集货拼团信息不存在，请进入拼团首页后重试！","marketNull");
			}
			else if(check.containsKey("is_passed") && check.get("is_passed") < 0 )
			{
				//旧团已过期，自动创建新团，并加入新团
				String uuid = marketBaseService.createNewMarket();
				users.setMktId(uuid);
				returnMsg = "集货旧团已过期，自动拼入新团且操作成功！";
			}
			else if(check.containsKey(LEAVE_COUNT))
			{
				if(check.get(LEAVE_COUNT) <= 0)
				{
					//旧团人数已满，自动创建新团，并加入新团
					String uuid = marketBaseService.createNewMarket();
					users.setMktId(uuid);
					returnMsg = "集货旧团已满员，自动拼入新团且操作成功！";
				}
				else if(check.get(LEAVE_COUNT) == 1)
				{
					//旧团人数将满，自动创建新团
					marketBaseService.createNewMarket();
				}
			}
			//参团人员保存
			getBaseDAO().update("MarketUsersMapper.saveMarketUsers", users);
			return ResultUtil.success(returnMsg);
		} catch (Exception e) {
			logger.error("集货拼团，参团操作失败", e);
			return ResultUtil.error("集货拼团参团操作失败，请重试！");
		}
	}
	
}
