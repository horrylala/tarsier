package com.sf.tarsier.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.tarsier.mvc.entity.QueryMarketBaseRequest;
import com.sf.tarsier.mvc.service.MarketBaseService;
import com.sf.tarsier.mvc.system.base.BaseController;
import com.sf.tarsier.mvc.system.entity.LoggerType;
import com.sf.tarsier.mvc.system.entity.Result;


@Controller
@RequestMapping("/market")
public class MarketBaseControl extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	@Autowired
	private MarketBaseService marketBaseService;
	
	@RequestMapping(value = "/queryMarketBaseInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result<Object> queryMarketBaseInfo(@RequestBody QueryMarketBaseRequest request) {
		return marketBaseService.queryMarketBaseInfo(request);
	}

}
