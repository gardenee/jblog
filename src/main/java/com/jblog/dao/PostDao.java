package com.jblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jblog.vo.PagingVo;
import com.jblog.vo.PostVo;

@Repository
public class PostDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insertPost(PostVo post) {
		return sqlSession.insert("post.insertPost", post);
	}
	
	public Integer selectRecent(int cateNo) {
		return sqlSession.selectOne("post.selectRecent", cateNo);
	}
	
	public PostVo selectPost(int postNo) {
		return sqlSession.selectOne("post.selectPost", postNo);
	}
	
	public List<PostVo> selectCatePost(PagingVo post) {
		return sqlSession.selectList("post.selectCatePost", post);
	}
	
	public Integer selectCnt(int cateNo) {
		return sqlSession.selectOne("post.selectCnt", cateNo);
	}
	
	public Integer selectFromPost(PostVo post) {
		return sqlSession.selectOne("post.selectFromPost", post);
	}
	
	public int selectTemp(PostVo post) {
		return sqlSession.selectOne("post.selectTemp", post);
	}
	
}
