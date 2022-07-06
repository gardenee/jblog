package com.jblog.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jblog.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int insertUser(UserVo user) {
		int count = -1;
		count = sqlSession.insert("user.insertUser", user);
		
		return count;
	}
	
	
	public int IDcheck(String check) {
		int count = sqlSession.selectOne("user.IDcheck", check);
		
		return count;
	}
	
	
	public int loginCheck(UserVo login) {
		int count = sqlSession.selectOne("user.loginCheck", login);
		
		return count;
	}
	
	
	public UserVo selectAuthUser(UserVo login) {
		UserVo authUser = sqlSession.selectOne("user.selectAuthUser", login);
		
		return authUser;
	}
}
