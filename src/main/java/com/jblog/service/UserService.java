package com.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jblog.dao.BlogDao;
import com.jblog.dao.CategoryDao;
import com.jblog.dao.UserDao;
import com.jblog.vo.BlogVo;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao uDao;
	@Autowired
	private BlogDao bDao;
	@Autowired
	private CategoryDao cDao;
	
	public String join(UserVo user) { // 회원가입
		String result = "fail";
		
		int count = uDao.insertUser(user);
		
		if (count > 0) {
			System.out.println("[회원가입 성공]");
			result = "success";
			
			BlogVo newBlog = new BlogVo(user.getId(), user.getUserName()+"의 블로그입니다.", "/assets/images/spring-logo.jpg"); // 블로그 개설
			count = bDao.insertBlog(newBlog);
			if (count > 0) System.out.println("[블로그 개설 성공]");
			
			CategoryVo cate = new CategoryVo(user.getId(), "미분류", ""); // 기본 카테고리 만들기
			count = cDao.insertCategory(cate);
			if (count > 0) System.out.println("[카테고리 생성 완료]");
			
		} else System.out.println("[회원가입 실패]");
				
		return result;
	}
	
	
	public boolean IDcheck(String check) { // 아이디 중복확인
		boolean result = false;
		int count = uDao.IDcheck(check);
		
		if (count > 0) System.out.println("[이미 사용중인 아이디입니다.]");
		else {
			System.out.println("[사용 가능한 아이디입니다.]");
			result = true;
		}
		
		return result;
	}
	
	
	public boolean loginCheck(UserVo login) { // 아이디-비번 확인
		boolean result = false;
		int count = uDao.loginCheck(login);
				
		if (count > 0) {
			System.out.println("[비밀번호 일치]");
			result = true;
		}
		else System.out.println("[비밀번호 불일치]");
		
		return result;
	}
	
	
	public UserVo getAuthUser(UserVo login) { // 로그인 후 세션에 저장할 값 불러오기
		UserVo authUser = uDao.selectAuthUser(login);
		System.out.println("[로그인 성공]");
		
		return authUser;
	}

}
