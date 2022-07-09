package com.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jblog.dao.CommentsDao;
import com.jblog.vo.CommentsVo;

@Service
public class CommentsService {

	@Autowired
	private CommentsDao cmtDao;
	
	
	public List<CommentsVo> selectComment(int postNo) {
		List<CommentsVo> cmtVo = cmtDao.selectComment(postNo);
		
		return cmtVo;
	}

		
	public CommentsVo insertComment(CommentsVo cmt) {
		int count = cmtDao.insertComment(cmt);
		int cmtNo = cmt.getCmtNo();
		cmt = new CommentsVo();
		
		if (count > 0) {
			System.out.println("[댓글 등록 완료]");
			cmt = cmtDao.selectRecent(cmtNo);
		}
		else System.out.println("[댓글 등록 실패]");
		
		return cmt;
	}
	
	
	public boolean deleteComment(int cmtNo) {
		boolean result = false;
		int count = cmtDao.deleteComment(cmtNo);
		
		if (count > 0) {
			System.out.println("[댓글 삭제 완료]");
			result = true;
		}
		else System.out.println("[댓글 삭제 실패]");
		
		return result;
	}
}
