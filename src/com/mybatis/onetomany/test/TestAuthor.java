package com.mybatis.onetomany.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.mybatis.onetomany.entity.Author;
import com.mybatis.onetomany.entity.Blog;
import com.mybatis.onetomany.mapper.AuthorMapper;
import com.mybatis.onetomany.mapper.BlogMapper;

/**
 * 测试author类的所有操作
 * 
 * @author Administrator
 *
 */
public class TestAuthor {
	// mybatis配置文件
	final static String path = "mybatis.xml";

	// 封装获取sqlsession
	public static SqlSession getSqlSession() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream(path);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		// 默认关闭事务自动提交
		SqlSession sqlSession = factory.openSession();
		return sqlSession;
	}

	
	// 测试selectAllAuthor
	@Test
	public void selectAllAuthor() throws IOException {
		SqlSession sqlSession = getSqlSession();
		AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
		List<Author> list = mapper.selectAllAuthor();
		for (Author author : list) {
			System.out.println(author);
		}
		sqlSession.close();
	}

	// 测试selectByName
	@Test
	public void selectByName() throws IOException {
		SqlSession sqlSession = getSqlSession();
		AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
		Author author = mapper.selectByName("邵奎");
		System.out.println(author);
		sqlSession.close();
	}

	// 查询所有的author数据和他们的blog(集合嵌套结果映射)
	@Test
	public void selectAuthorsAndBlogs() throws IOException {
		SqlSession sqlSession = getSqlSession();
		AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
		List<Author> list = mapper.selectAuthorsAndBlogs();
		for (Author author : list) {
			System.out.println(author);
		}
		sqlSession.close();
	}

	// 查询所有的author数据和他们的blog(集合嵌套结果映射，可重用resultmap)
	@Test
	public void selectAuthorsAndBlogsResuing() throws IOException {
		SqlSession sqlSession = getSqlSession();
		AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
		List<Author> list = mapper.selectAuthorsAndBlogsResuing();
		for (Author author : list) {
			System.out.println(author);
		}
		sqlSession.close();
	}
	// 查询所有的author数据和他们的blog(集合嵌套select查询)
		@Test
		public void selectAuthorsNestSelect() throws IOException {
			SqlSession sqlSession = getSqlSession();
			AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
			List<Author> list = mapper.selectAuthorsNestSelect(1);
			for (Author author : list) {
				System.out.print(author.getName()+":");
				List<Blog> boList = author.getList();
				for (Blog blog : boList) {
					System.out.print(blog.getName()+"，");
				}
			}
			sqlSession.close();
		}

		
	// 添加author
	@Test
	public void insertAuthors() throws IOException {
		SqlSession sqlSession = getSqlSession();
		AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
		List<Author> list = new ArrayList<Author>();
		Author author = new Author();
		author.setName("邵洁");
		Author author2 = new Author();
		author2.setName("林颖");
		list.add(author);
		list.add(author2);
		try {
			mapper.insertAuthors(list);
		} catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		}
		sqlSession.commit();
		sqlSession.close();
	}

}
