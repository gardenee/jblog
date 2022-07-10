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
		return sqlSession.insert("user.insertUser", user);
	}
	
	public int IDcheck(String check) {
		return sqlSession.selectOne("user.IDcheck", check);
	}
	
	public int loginCheck(UserVo login) {
		return sqlSession.selectOne("user.loginCheck", login);
	}
	
	public UserVo selectAuthUser(UserVo login) {
		return sqlSession.selectOne("user.selectAuthUser", login);
	}
	
}
