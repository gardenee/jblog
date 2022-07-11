package com.jblog.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

@Controller
public class BlogController {

	@Autowired
	private BlogService bService;
			
	@RequestMapping({"/{id:(?! assets|upload|temp).*}/{postNo}", "/{id:(?! assets|upload|temp).*}"}) // 블로그 메인
	public String main(@PathVariable("id") String id, @PathVariable(required=false) Integer postNo, @RequestParam(required=false) Integer cateNo, @RequestParam(required=false) Integer pageNo, Model model, HttpServletRequest request) {
		System.out.println("blog > " + id + " > main");
		
		Map<String, Object> map = bService.blogInfo(new PostVo(postNo, cateNo, pageNo, id));
		model.addAllAttributes(map);
		
		if (map.containsKey("error")) return "/error/404"; // 해당 id에 해당 번호의 포스트가 없는 경우 404로
				
		return "/blog/blog-main";
	}

	
	@RequestMapping("/{id}/admin/basic") // 기본 설정 변경
	public String adminBasic(@PathVariable("id") String id, Model model, HttpSession session) {
		System.out.println("blog > " + id + "> admin > basic");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if (authUser == null || !authUser.getId().equals(id)) {
			model.addAttribute("id", id);
			
			return "/error/403"; // 로그인 풀렸거나 남의 관리 페이지 접근 시도하면 403
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
		return "redirect:/" + id + "/admin/basic";
	}
	
	
	@RequestMapping("/{id}/admin/category") // 카테고리 설정 변경
	public String adminCategory(@PathVariable("id") String id, Model model, HttpSession session) {
		System.out.println("blog > " + id + "> admin > category");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if (authUser == null || !authUser.getId().equals(id)) {
			model.addAttribute("id", id);

			return "/error/403"; // 로그인 풀렸거나 남의 관리 페이지 접근 시도하면 403
		}
		
		Map<String, Object> map = bService.blogInfo(id);
		model.addAllAttributes(map);
		
		return "/blog/admin/blog-admin-cate";
	}
	
	
	@ResponseBody
	@PostMapping("/viewImg") // 이미지 미리보기
	public String viewImg(BlogVo fileInfo) {
		System.out.println("fileaddress");
		
		MultipartFile file = fileInfo.getFile();
		System.out.println(file.isEmpty());
		
		String path = bService.viewImg(file);
		
		return path;
	}
	
	
	@ResponseBody
	@RequestMapping("/{id}/admin/categoryList") // 카테고리 목록 불러오기
	public List<CategoryVo> categoryList(@PathVariable("id") String id) {
		System.out.println("blog > admin > categoryList");
		
		List<CategoryVo> cList = bService.getCategory(id);
		
		return cList; 
	}
	
	
	@ResponseBody
	@RequestMapping("/{id}/admin/deleteCategory") // 카테고리 삭제
	public boolean deleteCategory(@RequestBody PostVo post) {
		System.out.println("blog > admin > deleteCategory");
		
		boolean result = bService.deleteCategory(post.getCateNo());
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/{id}/admin/addCategory") // 카테고리 추가
	public int addCategory(@RequestBody CategoryVo cate) {
		System.out.println("blog > admin > addCategory");
		
		int cateNo = bService.addCategory(cate);
		
		return cateNo;
	}
	
	
	@RequestMapping("/{id}/admin/writeForm") // 글 작성 페이지
	public String adminWrite(@PathVariable("id") String id, Model model, HttpSession session) {
		System.out.println("blog > " + id + "> admin > write");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if (authUser == null || !authUser.getId().equals(id)) {
			model.addAttribute("id", id);
 
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
		return "redirect:/" + id + "/admin/writeForm";
	}
}
