package com.jblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jblog.vo.BlogVo;

@Repository
public class SearchDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<BlogVo> selectByTitle(String keyword) {
		return sqlSession.selectList("blog.selectByTitle", keyword);
	}
	
	
	public List<BlogVo> selectByName(String keyword) {
		return sqlSession.selectList("blog.selectByName", keyword);
	}
	
}
