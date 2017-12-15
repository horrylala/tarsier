package com.sf.tarsier.mvc.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.tarsier.mvc.entity.MarketUsers;
import com.sf.tarsier.mvc.service.MarketUsersService;
import com.sf.tarsier.mvc.system.base.BaseController;
import com.sf.tarsier.mvc.system.entity.Result;
import com.sf.tarsier.mvc.system.util.ResultUtil;
import com.sf.tarsier.mvc.system.util.ValidateResult;
import com.sf.tarsier.mvc.system.util.ValidatorUtils;

@Controller
@RequestMapping("/users")
public class MarketUsersControl extends BaseController{
	
	@Autowired
	private MarketUsersService marketUsersService;
	
	/**
	 * 保存参团用户信息
	 * @param users
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result<Object> saveMarketUsers(@RequestBody MarketUsers users) {
		//入参校验，空判断，长度判断
		List<ValidateResult> list = ValidatorUtils.validate(users);
		if (CollectionUtils.isNotEmpty(list)) {
			return ResultUtil.error(list.get(0).getMessage());
		}
		//调用处理逻辑
		return marketUsersService.saveMarketUsers(users);
	}
}
