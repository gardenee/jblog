package com.jblog.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jblog.dao.BlogDao;
import com.jblog.vo.BlogVo;

@Service
public class BlogService {

	@Autowired
	private BlogDao bDao;
	
	public BlogVo blogInfo(String id) {
		BlogVo bVo = bDao.selectBlog(id);
		
		return bVo;
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
		
		if (title.length() != 0 ) bVo.setBlogTitle(title);
		int count = bDao.updateBasic(bVo);
		
		if (count > 0) System.out.println("[업데이트가 완료되었습니다.]");
		else System.out.println("[업데이트가 실패했습니다.]");
		
	}
}
