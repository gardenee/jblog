package com.jblog.service;

import java.io.BufferedOutputStream;
import java.io.File;
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
		
	public Map<String, Object> search(String keyword, String option, int pageNo) {	// 메인에서 블로그 검색
		Map<String, Object> map = new HashMap<>();
		List<BlogVo> bList = new ArrayList<>();
		int count = 0;
		
		if (option != null) { // 검색을 한 경우
			map.put("pageNo", pageNo);
			map.put("keyword", keyword);
			map.put("option", option);
						
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
	
	
	public Map<String, Object> blogInfo(String id) { // 관리 페이지에서 불러올 정보
		Map<String, Object> map = new HashMap<>();
		map.put("bVo", bDao.selectBlog(id));
		map.put("cList", cDao.selectCateName(id));
		
		return map;
	}
	
	
	public Map<String, Object> blogInfo(PostVo post) { // 메인에서 불러올 정보
		Integer postNo = post.getPostNo();
		Integer cateNo = post.getCateNo();
		int pageNo = post.getPageNo();
		String id = post.getId();
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("bVo", bDao.selectBlog(id));
		map.put("cList", cDao.selectCate(id));
		
		
		if (cateNo == 0 && pageNo == 0 && postNo != 0) { // 주소창에 포스트 번호 검색해서 들어온 경우
			cateNo = pDao.selectFromPost(new PostVo(postNo, id)); // 포스트 번호 이용해서 카테고리 번호 찾음
			
			if (cateNo == null) { // 해당 블로그에 없는 게시글 번호 > 404
				map.put("error", "404error");
				
				return map;
			}
			
			int temp = pDao.selectTemp(new PostVo(postNo, cateNo)); // 해당 카테고리 전체 게시글 수
			pageNo = (temp - 1) / 5 + 1; // 계산해서 현재 페이지 번호 얻음
		}
		

		if (cateNo == 0) cateNo = cDao.selectRecent(id); // 카테고리 번호 없으면 최신 카테고리 번호 선택
		map.put("cateName", cDao.selectName(cateNo));

		if (pageNo == 0) pageNo = 1; // 페이지 번호 없으면 1페이지로 설정
		List<PostVo> pList = pDao.selectCatePost(new PagingVo(cateNo, pageNo, 5)); // 불러올 postvo list로 가져오기
		map.put("pList", pList);
						
		if (!pList.isEmpty()) { // 게시글이 있다면
			if (postNo == 0) postNo = pDao.selectRecent(cateNo); // 게시글 번호 없으면 첫 게시글 선택
			map.put("post", pDao.selectPost(postNo));
			
		    int totCnt = pDao.selectCnt(cateNo); // 카테고리 내 전체 게시글 수 불러오기
			map.put("paging", new PagingVo(totCnt, pageNo)); // 페이징 관련 vo 저장
		}

		return map;
	}
	
	
	public String viewImg(MultipartFile file) {
		String path = null;
		
		if (!file.isEmpty()) {
			String orgName = file.getOriginalFilename();
			String exName = orgName.substring(orgName.lastIndexOf("."));
			String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
			
			String saveDir = "C:\\javastudy\\temp";
			String filePath = saveDir + "\\" + saveName;
			path = "/temp/" + saveName;
			
			try {
				byte[] fileData = file.getBytes();
				BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(filePath));
				
				bs.write(fileData);
				System.out.println("[저장완료]");
				bs.close();
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		} 
		
		return path;
	}

	
	public void blogBasicUpdate(BlogVo bVo, MultipartFile file, String title) { // 블로그 기본 업데이트
		if (!file.isEmpty()) { // 파일 있는 경우 db + 현실 저장
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
		
		if (title.length() != 0) bVo.setBlogTitle(title); // 블로그 이름 바꼈으면 db 업데이트
		int count = bDao.updateBasic(bVo);
		
		if (count > 0) System.out.println("[업데이트가 완료되었습니다.]");
		else System.out.println("[업데이트가 실패했습니다.]");
		
		File rootDir = new File("C:/javaStudy/temp/"); // 미리보기 사진들 삭제
		File[] fileList = rootDir.listFiles();
		for (File delFile: fileList) delFile.delete();
	}
	
	
	public boolean deleteCategory(int cateNo) { // 카테고리 삭제
		boolean result = false;
		int count = cDao.deleteCategory(cateNo);
		
		if (count > 0) {
			System.out.println("[카테고리 삭제 완료]");
			result = true;
		} else System.out.println("[카테고리 삭제 실패]");
		
		return result;
	}
	
	
	public int addCategory(CategoryVo cate) { // 카테고리 추가
		int cateNo = -1;
		
		int count = cDao.checkCategory(cate);	// 해당 블로그에 중복된 이름의 카테고리 있는 지 확인	
		
		if (count == 0) count = cDao.insertCategory(cate); // 추가 가능하면 db에 카테고리 추가
		else count = -1;
		
		if (count > 0) {
			System.out.println("[카테고리 추가 완료]");
			cateNo = cate.getCateNo();
			
		} else System.out.println("[카테고리 추가 실패]");
		
		return cateNo;
	}
	
	
	public List<CategoryVo> getCategory(String id) { // 무거운 카테고리 가져오기
		return cDao.selectCate(id);
	}
	
	
	public void writePost(PostVo post) { // 게시글 작성
		post.setPostContent(post.getPostContent().replace("\n", "<br>"));
		int count = pDao.insertPost(post);
		
		if (count > 0) System.out.println("[게시글이 등록되었습니다.]");
		else System.out.println("[게시글이 등록되지 않았습니다.]");
	}
}
