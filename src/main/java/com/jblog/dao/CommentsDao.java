package com.jblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jblog.vo.CommentsVo;

@Repository
public class CommentsDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<CommentsVo> selectComment(int postNo) {
		return sqlSession.selectList("comments.selectComment", postNo);
	}
	
	public CommentsVo selectRecent(int cmtNo) {
		return sqlSession.selectOne("comments.selectRecent", cmtNo);
	}
	
	public int insertComment(CommentsVo cmt) {
		return sqlSession.insert("comments.insertComment", cmt);
	}
	
	public int deleteComment(int cmtNo) {
		return sqlSession.delete("comments.deleteComment", cmtNo);
	}
	
}
