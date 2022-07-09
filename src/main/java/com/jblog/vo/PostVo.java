package com.jblog.vo;

public class PostVo {

	private int postNo;
	private int cateNo;
	private int pageNo;
	private String id;
	private String userName;
	private String cateName;
	private String postTitle;
	private String postContent;
	private String regDate;
	
	public PostVo() {
		
	}
	
	public PostVo(int postNo, String id) {
		this.postNo = postNo;
		this.id = id;
	}
	
	public PostVo(int postNo, int cateNo) {
		this.postNo = postNo;
		this.cateNo = cateNo;
	}
		
	public PostVo(Integer postNo, Integer cateNo, Integer pageNo, String id) {
		if (pageNo == null) pageNo = 0;
		if (cateNo == null) cateNo = 0;
		if (postNo == null) postNo = 0;
		
		this.postNo = postNo;
		this.cateNo = cateNo;
		this.pageNo = pageNo;
		this.id = id;
	}

	public PostVo(int postNo, int cateNo, String cateName, String postTitle, String postContent, String regDate) {
		this.postNo = postNo;
		this.cateNo = cateNo;
		this.cateName = cateName;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.regDate = regDate;
	}

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(Integer postNo) {
		if (postNo == null) postNo = 0;

		this.postNo = postNo;
	}

	public int getCateNo() {
		return cateNo;
	}

	public void setCateNo(Integer cateNo) {
		if (cateNo == null) cateNo = 0;

		this.cateNo = cateNo;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		if (pageNo == null) pageNo = 0;
		
		this.pageNo = pageNo;
	}
	
	@Override
	public String toString() {
		return "PostVo [postNo=" + postNo + ", cateNo=" + cateNo + ", pageNo=" + pageNo + ", id=" + id + ", userName="
				+ userName + ", cateName=" + cateName + ", postTitle=" + postTitle + ", postContent=" + postContent
				+ ", regDate=" + regDate + "]";
	}
	
}
