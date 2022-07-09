package com.jblog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jblog.dao.SearchDao;
import com.jblog.vo.BlogVo;

@Service
public class SearchService {

	@Autowired
	private SearchDao sDao;
	
	public List<BlogVo> search(String keyword, String option) {		
		List<BlogVo> bList = new ArrayList<>();
		
		if (option != null) {
			if (option.equals("title")) {
				bList = sDao.selectByTitle(keyword);
				
			} else if (option.equals("name")) {
				bList = sDao.selectByName(keyword);
			}
		}
		return bList;
	}
}
