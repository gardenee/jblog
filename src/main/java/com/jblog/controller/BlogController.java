package com.jblog.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jblog.service.BlogService;
import com.jblog.vo.BlogVo;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.PostVo;
import com.jblog.vo.UserVo;

@RequestMapping("/blog")
@Controller
public class BlogController {

	@Autowired
	private BlogService bService;
	
	@RequestMapping({"/{id}/{postNo}", "/{id}"})
	public String main(@PathVariable("id") String id, @PathVariable(required=false) Integer postNo, @RequestParam(required=false) Integer cateNo, @RequestParam(required=false) Integer pageNo, Model model, HttpServletRequest request) {
		System.out.println("blog > " + id + " > main");
		
		Map<String, Object> map = bService.blogInfo(new PostVo(postNo, cateNo, pageNo, id));
		model.addAllAttributes(map);
		
		if (map.containsKey("error")) return "/error/404";
				
		return "/blog/blog-main";
	}

	
	@RequestMapping("/{id}/admin/basic")
	public String adminBasic(@PathVariable("id") String id, Model model, HttpSession session) {
		System.out.println("blog > " + id + "> admin > basic");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if (authUser == null || !authUser.getId().equals(id)) {
			
			return "/error/403";
		}
		
		Map<String, Object> map = bService.blogInfo(id);
		model.addAttribute("bVo", (BlogVo)map.get("bVo"));
		
		return "/blog/admin/blog-admin-basic";
	}
	
	
	@RequestMapping("/{id}/basicChange")
	public String basicChange(@PathVariable("id") String id, @RequestParam("file") MultipartFile file, @RequestParam("blogTitle") String title) throws UnsupportedEncodingException {
		System.out.println("blog > " + id + "> admin > basicChange");

		Map<String, Object> map = bService.blogInfo(id);
		bService.blogBasicUpdate((BlogVo)map.get("bVo"), file, title);
		
		id = URLEncoder.encode(id, "UTF-8");
		return "redirect:/blog/" + id + "/admin/basic";
	}
	
	
	@RequestMapping("/{id}/admin/category")
	public String adminCategory(@PathVariable("id") String id, Model model, HttpSession session) {
		System.out.println("blog > " + id + "> admin > category");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if (authUser == null || !authUser.getId().equals(id)) {
			
			return "/error/403";
		}
		
		Map<String, Object> map = bService.blogInfo(id);
		model.addAllAttributes(map);
		
		return "/blog/admin/blog-admin-cate";
	}
	
	
	@ResponseBody
	@RequestMapping("/{id}/admin/deleteCategory")
	public boolean deleteCategory(@RequestBody PostVo post) {
		System.out.println("blog > admin > deleteCategory");
		
		boolean result = bService.deleteCategory(post.getCateNo());
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/{id}/admin/addCategory")
	public int addCategory(@RequestBody CategoryVo cate) {
		System.out.println("blog > admin > addCategory");
		
		int cateNo = bService.addCategory(cate);
		
		return cateNo;
	}
	
	
	@RequestMapping("/{id}/admin/writeForm")
	public String adminWrite(@PathVariable("id") String id, Model model, HttpSession session) {
		System.out.println("blog > " + id + "> admin > write");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if (authUser == null || !authUser.getId().equals(id)) {
			
			return "/error/403";
		}
		
		Map<String, Object> map = bService.blogInfo(id);
		model.addAllAttributes(map);
		
		return "/blog/admin/blog-admin-write";
	}
	
	
	@RequestMapping("/{id}/admin/write")
	public String writePost(@PathVariable("id") String id, @ModelAttribute PostVo post) throws UnsupportedEncodingException {
		System.out.println("blog > write");
		
		bService.writePost(post);
		
		id = URLEncoder.encode(id, "UTF-8");
		return "redirect:/blog/" + id + "/admin/writeForm";
	}
}
