package com.jblog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jblog.service.UserService;
import com.jblog.vo.PostVo;
import com.jblog.vo.UserVo;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserService uService;
		
	@RequestMapping(value="/joinForm")
	public String joinForm() {
		System.out.println("user > joinForm");
		
		return "/user/joinForm";
	}
	
	
	@PostMapping("/join")
	public String join(@ModelAttribute UserVo user) {
		System.out.println("user > join");
		
		String result = uService.join(user);
		
		if (result == "success") return "/user/joinSuccess";
		else return "/user/joinForm";
	}
	
	
	@ResponseBody
	@PostMapping("/idcheck")
	public boolean idCheck(@RequestBody UserVo test) {
		System.out.println("user > idcheck");
		
		boolean result = uService.IDcheck(test.getId());
		
		return result;
	}
	
	
	@RequestMapping(value="/loginForm")
	public String loginForm(@ModelAttribute PostVo post, HttpServletRequest request, Model model) {
		System.out.println("user > loginForm");
		
		String referer = (String) request.getHeader("REFERER");
		int idx = referer.indexOf("jblog");
		referer = referer.substring(idx + 5);
		
		model.addAttribute("address", referer);
		model.addAttribute("post", post);
		
		return "/user/loginForm";
	}
	
	
	@ResponseBody
	@PostMapping("/logincheck")
	public boolean loginCheck(@RequestBody UserVo login) {
		System.out.println("user > logincheck");
		
		boolean result = uService.loginCheck(login);
		
		return result; 
	}
	
	
	@PostMapping(value="/login")
	public String login(@ModelAttribute UserVo login, HttpSession session, @RequestParam("address") String address, @RequestParam("cateNo") int cateNo, @RequestParam("pageNo") String pageNo, Model model) {
		System.out.println("user > login");
		
		UserVo authUser = uService.getAuthUser(login);
		session.setAttribute("authUser", authUser);
		
		model.addAttribute("cateNo", cateNo);
		model.addAttribute("pageNo", pageNo);
				
		return "redirect:" + address;
	}
	
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session, HttpServletRequest request) {
		System.out.println("user > logout");
				
		session.removeAttribute("authUser");
		session.invalidate();
		
		String referer = (String) request.getHeader("REFERER");
		int idx = referer.indexOf("jblog");
		referer = referer.substring(idx + 5);
		
		return "redirect:" + referer;
	}

}
