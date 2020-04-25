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

	// 测试:根据姓名查询，或者根据姓名和id一起查询author
	@Test
	public void selectByName() throws IOException {
		SqlSession sqlSession = getSqlSession();
		AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
		Author author = new Author();
		author.setName("邵奎");
		// 只根据名字查找
		Author author1 = mapper.selectByNameOrId(author);
		System.out.println(author1);
		// 根据id和名字一起查询
		author.setId(5);
		author1 = mapper.selectByNameOrId(author);
		System.out.println(author1);
		sqlSession.close();
	}
	//如过传入了id就按id查找，如果传入了name就按照name查找    choose when otherwise
	@Test
	public void seleAuthorIfIdORIfName() throws IOException {
		SqlSession sqlSession = getSqlSession();
		AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
		Author author = new Author();
		//只传入id，预计只按照id查找
		//author.setId(4);
		//只传入名字
		//author.setName("邵奎");
		//传空
		System.out.println(mapper.selectAuthorIfIdORIfName(author));
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
			System.out.print(author.getName() + ":");
			List<Blog> boList = author.getList();
			for (Blog blog : boList) {
				System.out.print(blog.getName() + "，");
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
