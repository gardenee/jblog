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
		
	@RequestMapping(value="/joinForm") // 회원가입페이지 이동
	public String joinForm() {
		System.out.println("user > joinForm");
		
		return "/user/joinForm";
	}
	
	
	@PostMapping("/join") // 회원가입
	public String join(@ModelAttribute UserVo user) {
		System.out.println("user > join");
		
		String result = uService.join(user);
		
		if (result.equals("success")) return "/user/joinSuccess";
		else return "/user/joinForm";
	}
	
	
	@ResponseBody
	@PostMapping("/idcheck") // 아이디 중복 확인용
	public boolean idCheck(@RequestBody UserVo test) {
		System.out.println("user > idcheck");
		
		return uService.IDcheck(test.getId());
	}
	
	
	@RequestMapping(value="/loginForm") // 로그인 페이지 연결
	public String loginForm(@ModelAttribute PostVo post, HttpServletRequest request, Model model) {
		System.out.println("user > loginForm");
		
		String referer = request.getHeader("REFERER"); // 직전 주소 저장용
		int idx = referer.indexOf("jblog");
		referer = referer.substring(idx + 5);

		model.addAttribute("address", referer);
		model.addAttribute("post", post);
		
		return "/user/loginForm";
	}
	
	
	@ResponseBody
	@PostMapping("/logincheck") // 아이디-비밀번호 일치 확인
	public boolean loginCheck(@RequestBody UserVo login) {
		System.out.println("user > logincheck");
		
		boolean result = uService.loginCheck(login);
		
		return result; 
	}
	
	
	@PostMapping(value="/login") // 로그인
	public String login(@ModelAttribute UserVo login, HttpSession session, @RequestParam("address") String address, @RequestParam("cateNo") int cateNo, @RequestParam("pageNo") String pageNo, Model model) {
		System.out.println("user > login");
		
		UserVo authUser = uService.getAuthUser(login);
		session.setAttribute("authUser", authUser);
		
		model.addAttribute("cateNo", cateNo);
		model.addAttribute("pageNo", pageNo);
				
		return "redirect:" + address;
	}
	
	
	@RequestMapping(value="/logout") // 로그아웃
	public String logout(HttpSession session, HttpServletRequest request) {
		System.out.println("user > logout");
				
		session.removeAttribute("authUser");
		session.invalidate();
		
		String referer = request.getHeader("REFERER"); // 주소 저장용
		int idx = referer.indexOf("jblog");
		referer = referer.substring(idx + 5);
		
		if (referer.contains("/admin/")) { // 본인 관리 페이지에서 로그아웃한 경우 403이 아닌 블로그 메인으로 이동
			idx = referer.indexOf("/admin/");
			referer = referer.substring(0, idx+1);
		}
		
		return "redirect:" + referer;
	}

}
