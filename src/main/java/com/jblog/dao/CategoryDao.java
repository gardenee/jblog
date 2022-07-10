package com.jblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jblog.vo.CategoryVo;

@Repository
public class CategoryDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int insertCategory(CategoryVo cate) {
		return sqlSession.insert("category.insertCategory", cate);
	}
	
	public int checkCategory(CategoryVo cate) {
		return sqlSession.selectOne("category.checkCategory", cate);
	}
	
	public List<CategoryVo> selectCate(String id) {
		return sqlSession.selectList("category.selectCate", id);

	}
	
	public List<CategoryVo> selectCateName(String id) {
		return sqlSession.selectList("category.selectCate", id);

	}	
	
	public int deleteCategory(int cateNo) {
		return sqlSession.delete("category.deleteCategory", cateNo);
	}
	
	public int selectRecent(String id) {
		return sqlSession.selectOne("category.selectRecent", id);
	}
	
	public String selectName(int cateNo) {
		return sqlSession.selectOne("category.selectName", cateNo);
	}
	
}
