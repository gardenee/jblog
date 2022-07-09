package com.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jblog.service.CommentsService;
import com.jblog.vo.CommentsVo;
import com.jblog.vo.PostVo;

@RequestMapping("/comments")
@Controller
public class CommentsController {
	
	@Autowired
	private CommentsService cmtService;

	
	@ResponseBody
	@PostMapping("/loadComment")
	public List<CommentsVo> selectComment(@RequestBody PostVo post) {
		System.out.println("comments > loadComment");
		
		List<CommentsVo> cmtList = cmtService.selectComment(post.getPostNo());
		
		return cmtList;
	}
	
	
	@ResponseBody
	@PostMapping("/addComment")
	public CommentsVo addComment(@RequestBody CommentsVo cmt) {
		System.out.println("comments > addComment");
		
		CommentsVo newCmt = cmtService.insertComment(cmt);
		
		return newCmt;
	}
	
	
	@ResponseBody
	@PostMapping("/deleteComment")
	public boolean deleteComment(@RequestBody CommentsVo cmt) {
		System.out.println("comments > deleteComment");
		
		System.out.println(cmt.getCmtNo());
		boolean result = cmtService.deleteComment(cmt.getCmtNo());
		
		return result;
	}
}
