package com.sf.tarsier.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sf.tarsier.mvc.system.base.BaseController;
import com.sf.tarsier.mvc.system.entity.Content;
import com.sf.tarsier.mvc.system.entity.LoggerType;
import com.sf.tarsier.mvc.system.entity.Result;
import com.sf.tarsier.mvc.system.util.HttpUtils;
import com.sf.tarsier.mvc.system.util.ResultUtil;

@Controller
@RequestMapping("/acode")
public class WXAcodeController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(LoggerType.COMMON);
	
	@Value("${appid}")
	private String appid;
	
	@Value("${appsecret}")
	private String appsecret;
	
	@Value("${wx_token_url}")
	private String tokenUrl;
	
	@Value("${wx_acode_url}")
	private String acodeUrl;
	
	/**
	 * 生成二维码
	 */
	@RequestMapping(value="getAcode",method=RequestMethod.POST)
	@ResponseBody
	public Result<Object> createAcode(@RequestBody Map<String,String> paramMap) {
		if(paramMap==null||paramMap.get("path")==null){
			return ResultUtil.error("传参为空","param is null");
		}
		String params="grant_type=client_credential&appid="+appid+"&secret="+appsecret;
		String result=HttpUtils.get(tokenUrl, params);
		logger.info("获取token结果:{}",result);
		if(StringUtils.isBlank(result) || result.indexOf("errcode")>=0 || result.indexOf("access_token")<0){
			logger.info("获取token失败");
			return ResultUtil.error(Content.ACODEERRORMSG,Content.ACODEERRORCODE);
		}
		JSONObject json=JSONObject.parseObject(result);
		String token=json.getString("access_token");
		if(StringUtils.isBlank(token)){
			logger.info("获取token为空值");
			return ResultUtil.error(Content.ACODEERRORMSG,Content.ACODEERRORCODE);
		}
		Map<String,Object> map=new HashMap<>();
		map.put("path", paramMap.get("path"));
		map.put("width", 450);
		map.put("auto_color", true);
		Object o=HttpUtils.post(acodeUrl+token, map);
		logger.info(result);
		if(StringUtils.isBlank(result)||result.indexOf("errcode")>=0){
			logger.info("获取二维码图片url失败");
			return ResultUtil.error(Content.ACODEERRORMSG,Content.ACODEERRORCODE);
		}
		return ResultUtil.success(o);
	}
}
