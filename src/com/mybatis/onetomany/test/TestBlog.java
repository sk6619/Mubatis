package com.mybatis.onetomany.test;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.mybatis.onetomany.entity.Blog;
import com.mybatis.onetomany.mapper.BlogMapper;

public class TestBlog {
	//<!-- 查询所有blog -->
	@Test
	public void selectBlogs() throws IOException {
		SqlSession sqlSession = TestAuthor.getSqlSession();
		BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
		List<Blog> list = mapper.selectBlogs();
		for (Blog blog : list) {
			System.out.println(blog);
		}
		sqlSession.close();
		
	}
	//查询blogs的同时把author查出来（结果集嵌套）
	@Test
	public void selectBlogsAndAuthor() throws IOException {
		SqlSession sqlSession = TestAuthor.getSqlSession();
		BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
		List<Blog> list = mapper.selectBlogsAndAuthorByResultNest();
		for (Blog blog : list) {
			System.out.println(blog);
		}
		sqlSession.close();
	}
	//查询blogs的同时把author查出来（select嵌套）byauthor_id
	@Test
    public void selectBlogsAndAuthorBySelectNest() throws IOException {
		SqlSession sqlSession = TestAuthor.getSqlSession();
		BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
		List<Blog> list = mapper.selectBlogsAndAuthorBySelectNest(2);
		for (Blog blog : list) {
			System.out.println(blog);
		}
		sqlSession.close();
	}
}
