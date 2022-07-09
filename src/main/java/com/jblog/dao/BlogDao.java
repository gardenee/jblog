package com.jblog.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jblog.vo.BlogVo;

@Repository
public class BlogDao {

	@Autowired
	private SqlSession sqlSession;
	
	public BlogVo selectBlog(String id) {
		return sqlSession.selectOne("blog.selectBlog", id);
	}
	
	
	public int insertBlog(BlogVo newBlog) {
		return sqlSession.insert("blog.insertBlog", newBlog);
	}
	
	
	public int updateBasic(BlogVo bVo) {
		return sqlSession.update("blog.updateBasic", bVo);
	}
}
