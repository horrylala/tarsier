package com.sf.tarsier.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sf.tarsier.mvc.service.BookService;
import com.sf.tarsier.mvc.system.base.BaseController;
import com.sf.tarsier.mvc.system.entity.Book;
import com.sf.tarsier.mvc.system.entity.Result;
import com.sf.tarsier.mvc.system.util.ResultUtil;


@Controller
@RequestMapping("/book")
public class BookControl extends BaseController{
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	@ResponseBody
	public Result<Integer> test(@RequestBody Book book) {
		logger.info("入参..................."+ JSON.toJSONString(book));
		return ResultUtil.success(bookService.selectTest(book));
	}
	
	@RequestMapping("/show")
	@ResponseBody
	public String show() {
		logger.info("....xxxxxxx...............");
		return "show替顶替仍";
	}
	
	@RequestMapping(value="getNum",method=RequestMethod.POST)
	@ResponseBody
	public Result<String> getNum() {
		logger.info("getNum");
		return ResultUtil.success("统计结果："+String.valueOf(bookService.getCount()));
	}

}
