package com.jblog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jblog.service.UserService;
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
	
	
	@RequestMapping(value="/join", method={RequestMethod.POST})
	public String join(@ModelAttribute UserVo user) {
		System.out.println("user > join");
		
		String result = uService.join(user);
		
		if (result == "success") return "/user/joinSuccess";
		else return "/user/joinForm";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/idcheck", method={RequestMethod.POST})
	public boolean idCheck(@RequestBody UserVo test) {
		System.out.println("user > idcheck");
		
		boolean result = uService.IDcheck(test.getId());
		
		return result;
	}
	
	
	@RequestMapping(value="/loginForm")
	public String loginForm() {
		System.out.println("user > loginForm");
		
		return "/user/loginForm";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/logincheck", method={RequestMethod.POST})
	public boolean loginCheck(@RequestBody UserVo login) {
		System.out.println("user > logincheck");
		
		boolean result = uService.loginCheck(login);
		
		return result; 
	}
	
	
	@RequestMapping(value="/login", method={RequestMethod.POST})
	public String login(@ModelAttribute UserVo login, HttpSession session) {
		System.out.println("user > login");
		
		UserVo authUser = uService.getAuthUser(login);
		session.setAttribute("authUser", authUser);
		
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		System.out.println("user > logout");
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/";
	}

}
