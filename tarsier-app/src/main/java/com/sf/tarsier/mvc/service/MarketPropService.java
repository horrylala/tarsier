package com.sf.tarsier.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sf.tarsier.mvc.entity.MarketProp;
import com.sf.tarsier.mvc.system.base.BaseService;
import com.sf.tarsier.mvc.system.entity.LoggerType;

@Service("marketPropService")
public class MarketPropService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	/**
	 * <b>功能：保存参加集货拼团，参团信息
	 */
	public Map<String,String> selectPropList() {
		try {
			Map<String,String> params = new HashMap<>();
			Map<String,String> resultMap = new HashMap<>();
			
			StringBuilder useRequire = new StringBuilder();
			params.put("propKeyLike", "use_require");
			List<MarketProp> propReqList = getBaseDAO().selectList("MarketPropMapper.selectPropList", params);
			for (MarketProp prop : propReqList) {
				useRequire.append(prop.getPropVal()).append("\n");
			}
			String useRequireTmp = useRequire.toString();
			
			String mktNameShow = "";
			String mktNameNum = "";
			params.clear();
			params.put("attr", "pro_market_base");
			//检查当前要参团的时间周期和参团人员
			List<MarketProp> propList = getBaseDAO().selectList("MarketPropMapper.selectPropList", params);
			for (MarketProp prop : propList) {
				if("mkt_name_show".equals(prop.getPropKey()))
				{
					mktNameShow = prop.getPropVal();
				}
				else if("mkt_name_num".equals(prop.getPropKey()))
				{
					mktNameNum = prop.getPropVal();
				}
				else
				{
					resultMap.put(prop.getPropKey(), prop.getPropVal());
				}
				useRequireTmp = useRequireTmp.replaceAll("#"+prop.getPropKey()+"#", prop.getPropVal());
			}
			
			logger.info("用户要求：" + useRequireTmp);
			resultMap.put("use_require", useRequireTmp);
	        
	        mktNameShow = mktNameShow.replace("#val#", mktNameNum);
			logger.info("生成的拼团名称：" + mktNameShow);
			resultMap.put("mkt_name_show", mktNameShow);
			
			return resultMap;
		} catch (Exception e) {
			logger.error("集货拼团，参团操作失败", e);
			return new HashMap<String,String>();
		}
	}
	
}
