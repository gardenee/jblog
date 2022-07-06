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
		BlogVo bVo = sqlSession.selectOne("blog.selectBlog", id);
		
		return bVo;
	}
	
	
	public int insertBlog(BlogVo newBlog) {
		int count = -1;
		count = sqlSession.insert("blog.insertBlog", newBlog);
		
		return count;
	}
	
	
	public int updateBasic(BlogVo bVo) {
		int count = -1;
		count = sqlSession.update("blog.updateBasic", bVo);
		
		return count;
	}
}
