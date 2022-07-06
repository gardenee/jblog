package com.jblog.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jblog.service.BlogService;
import com.jblog.vo.BlogVo;

@RequestMapping("/blog")
@Controller
public class BlogController {

	@Autowired
	private BlogService bService;
	
	@RequestMapping(value = "/{id}", method={RequestMethod.GET, RequestMethod.POST})
	public String main(@PathVariable("id") String id, Model model) {
		System.out.println("blog > " + id + " > main");

		BlogVo bVo = bService.blogInfo(id);
		model.addAttribute("bVo", bVo);
						
		return "/blog/blog-main";
	}
	
	
	@RequestMapping(value="/{id}/admin/basic", method={RequestMethod.GET, RequestMethod.POST})
	public String adminBasic(@PathVariable("id") String id, Model model) {
		System.out.println("blog >" + id + "> admin > basic");
		
		BlogVo bVo = bService.blogInfo(id);
		model.addAttribute("bVo", bVo);
		
		return "/blog/admin/blog-admin-basic";
	}
	
	
	@RequestMapping(value="/{id}/basicChange", method={RequestMethod.GET, RequestMethod.POST})
	public String basicChange(@PathVariable("id") String id, @RequestParam("file") MultipartFile file, @RequestParam("blogTitle") String title) throws UnsupportedEncodingException {
		System.out.println("blog >" + id + "> admin > basicChange");

		BlogVo bVo = bService.blogInfo(id);
		
		bService.blogBasicUpdate(bVo, file, title);
		
		id = URLEncoder.encode(id, "UTF-8");
		
		return "redirect:/blog/" + id + "/admin/basic";
	}
	
	
	@RequestMapping(value="/{id}/admin/category", method={RequestMethod.GET, RequestMethod.POST})
	public String adminCategory(@PathVariable("id") String id, Model model) {
		System.out.println("blog >" + id + "> admin > category");
		
		BlogVo bVo = bService.blogInfo(id);
		model.addAttribute("bVo", bVo);
		
		return "/blog/admin/blog-admin-cate";
	}
}
