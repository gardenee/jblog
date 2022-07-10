package com.jblog.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jblog.service.BlogService;

@Controller
public class MainController {
	
	@Autowired
	private BlogService bService;
		
	@RequestMapping("/")
	public String main(@RequestParam(value="keyword", defaultValue="") String keyword, @RequestParam(value="pageNo", defaultValue="1") int pageNo, @RequestParam(value="option", required=false) String option, Model model) {
		System.out.println("main > search " + keyword + "(" + option + ")");
		
		Map<String, Object> map = bService.search(keyword, option, pageNo);
		model.addAllAttributes(map);
		
		return "/main/index";
	}

}
