package com.jblog.dao;

import java.util.List;
import java.util.Map;

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
	
	
	public List<BlogVo> selectByTitle(Map<String, Object> map) {
		return sqlSession.selectList("blog.selectByTitle", map);
	}
	
	
	public List<BlogVo> selectByName(Map<String, Object> map) {
		return sqlSession.selectList("blog.selectByName", map);
	}
	
	public int selectTitleCnt(String keyword) {
		return sqlSession.selectOne("selectTitleCnt", keyword);
	}
	
	public int selectNameCnt(String keyword) {
		return sqlSession.selectOne("selectNameCnt", keyword);
	}
	
}
