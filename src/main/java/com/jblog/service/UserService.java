package com.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jblog.dao.BlogDao;
import com.jblog.dao.UserDao;
import com.jblog.vo.BlogVo;
import com.jblog.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao uDao;
	
	@Autowired
	private BlogDao bDao;
	
	public String join(UserVo user) {
		String result = "fail";
		
		int count = uDao.insertUser(user);
		
		if (count > 0) {
			System.out.println("[회원가입 성공]");
			result = "success";
			
			BlogVo newBlog = new BlogVo(user.getId(), user.getUserName()+"의 블로그입니다.", "/assets/images/spring-logo.jpg");
			count = bDao.insertBlog(newBlog);
			if (count > 0) System.out.println("[블로그 개설 성공]");
			
		} else System.out.println("[회원가입 실패]");
				
		return result;
	}
	
	
	public boolean IDcheck(String check) {
		int count = uDao.IDcheck(check);
		boolean result = false;
		
		if (count > 0) System.out.println("[이미 사용중인 아이디입니다.]");
		else {
			System.out.println("[사용 가능한 아이디입니다.]");
			result = true;
		}
		
		return result;
	}
	
	
	public boolean loginCheck(UserVo login) {
		int count = uDao.loginCheck(login);
		boolean result = false;
		
		if (count > 0) {
			System.out.println("[비밀번호 일치]");
			result = true;
		}
		else System.out.println("[비밀번호 불일치]");
		
		return result;
	}
	
	
	public UserVo getAuthUser(UserVo login) {
		UserVo authUser = uDao.selectAuthUser(login);
		System.out.println("[로그인 성공]");
		
		return authUser;
	}

}
