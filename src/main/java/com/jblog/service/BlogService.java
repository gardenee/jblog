package com.jblog.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jblog.dao.BlogDao;
import com.jblog.dao.CategoryDao;
import com.jblog.dao.PostDao;
import com.jblog.vo.BlogVo;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.PagingVo;
import com.jblog.vo.PostVo;

@Service
public class BlogService {

	@Autowired
	private BlogDao bDao;
	@Autowired
	private CategoryDao cDao;
	@Autowired
	private PostDao pDao;
	
	
	public Map<String, Object> blogInfo(String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("bVo", bDao.selectBlog(id));
		map.put("cList", cDao.selectCateName(id));
		
		return map;
	}
	
	
	public Map<String, Object> blogInfo(PostVo post) {
		System.out.println(post.toString());
		Integer postNo = post.getPostNo();
		Integer cateNo = post.getCateNo();
		int pageNo = post.getPageNo();
		String id = post.getId();
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("bVo", bDao.selectBlog(id));
		map.put("cList", cDao.selectCate(id));
		
		
		if (cateNo == 0 && pageNo == 0 && postNo != 0) {
			cateNo = pDao.selectFromPost(new PostVo(postNo, id));
			
			if (cateNo == null) {
				map.put("error", "404error");
				
				return map;
			}
			
			int temp = pDao.selectTemp(new PostVo(postNo, cateNo));
			pageNo = (temp - 1) / 5 + 1;
		}
		

		if (cateNo == 0) cateNo = cDao.selectRecent(id);
		map.put("cateName", cDao.selectName(cateNo));

		if (pageNo == 0) pageNo = 1;
		List<PostVo> pList = pDao.selectCatePost(new PagingVo(cateNo, pageNo, 5));
		map.put("pList", pList);
				
		
		if (pList.isEmpty()) {
			map.put("post", null);
			map.put("paging", null);
			
		} else {
			if (postNo == 0) postNo = pDao.selectRecent(cateNo);
			map.put("post", pDao.selectPost(postNo));
			
		    int totCnt = pDao.selectCnt(cateNo);
			map.put("paging", new PagingVo(totCnt, pageNo));
		}

		return map;
	}
	
	
	public Map<String, Object> search(String keyword, String option, int pageNo) {		
		Map<String, Object> map = new HashMap<>();
		List<BlogVo> bList = new ArrayList<>();
		int count = 0;
		
		if (option != null) {
			map.put("pageNo", pageNo);
			map.put("keyword", keyword);
						
			if (option.equals("title")) {
				bList = bDao.selectByTitle(map);
				count = bDao.selectTitleCnt(keyword);
				
			} else if (option.equals("name")) {
				bList = bDao.selectByName(map);
				count = bDao.selectNameCnt(keyword);
			}
			map.put("bList", bList);
			map.put("paging", new PagingVo(count, pageNo));
		}
		return map;
	}

	
	public void blogBasicUpdate(BlogVo bVo, MultipartFile file, String title) {
		if (!file.isEmpty()) {
			String orgName = file.getOriginalFilename();
			String exName = orgName.substring(orgName.lastIndexOf("."));
			String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
			
			String saveDir = "C:\\javastudy\\upload";
			String filePath = saveDir + "\\" + saveName;
		
		try {
			byte[] fileData = file.getBytes();
			BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(filePath));
			
			bs.write(fileData);
			System.out.println("[저장완료]");
			bs.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
			bVo.setLogoFile("/upload/"+saveName);
		}
		
		if (title.length() != 0) bVo.setBlogTitle(title);
		int count = bDao.updateBasic(bVo);
		
		if (count > 0) System.out.println("[업데이트가 완료되었습니다.]");
		else System.out.println("[업데이트가 실패했습니다.]");
	}
	
	
	public void writePost(PostVo post) {
		post.setPostContent(post.getPostContent().replace("\n", "<br>"));
		int count = pDao.insertPost(post);
		
		if (count > 0) System.out.println("[게시글이 등록되었습니다.]");
		else System.out.println("[게시글이 등록되지 않았습니다.]");
	}
	
	
	public boolean deleteCategory(int cateNo) {
		boolean result = false;
		int count = cDao.deleteCategory(cateNo);
		
		if (count > 0) {
			System.out.println("[카테고리 삭제 완료]");
			result = true;
		} else System.out.println("[카테고리 삭제 실패]");
		
		return result;
	}
	
	
	public int addCategory(CategoryVo cate) {
		int cateNo = -1;
		int count = cDao.insertCategory(cate);
		
		if (count > 0) {
			System.out.println("[카테고리 추가 완료]");
			cateNo = cate.getCateNo();
			
		} else System.out.println("[카테고리 추가 실패]");
		
		return cateNo;
	}
	
	
	public List<CategoryVo> getCategory(String id) {
		System.out.println("id " + id);
		List<CategoryVo> cList = cDao.selectCate(id);
		System.out.println(cList.toString());
		System.out.println("[" + cList.size() + "건 불러옴]");
		
		return cList;
	}
}
