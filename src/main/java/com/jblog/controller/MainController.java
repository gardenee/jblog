package com.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jblog.service.SearchService;
import com.jblog.vo.BlogVo;

@Controller
public class MainController {
	
	@Autowired
	private SearchService sService;
	
	
	@RequestMapping("/")
	public String main(@RequestParam(value="keyword", defaultValue="") String keyword, @RequestParam(value="option", required=false) String option, Model model) {
		System.out.println("main > search " + keyword + "(" + option + ")");
		
		List<BlogVo> bList = sService.search(keyword, option);
			
		model.addAttribute("bList", bList);
		model.addAttribute("keyword", keyword);
		model.addAttribute("option", option);
		
		return "/main/index";
	}

}
