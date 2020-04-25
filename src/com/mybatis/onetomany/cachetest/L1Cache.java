package com.mybatis.onetomany.cachetest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.mybatis.onetomany.entity.Author;
import com.mybatis.onetomany.mapper.AuthorMapper;

/**
 * 一级缓存测试
 * @author Administrator
 *
 */
public class L1Cache {
	
	static final String path = "mybatis.xml"; 
	
	public static SqlSession getSqlSession() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream(path);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		//默认关闭事务的自动提交
		SqlSession sqlSession = factory.openSession();
		return sqlSession;
	}

	/**
	 * 一级缓存 sqlsession会话级别 测试一级缓存
	 * @throws IOException
	 * 测试一：同一个sqlsession执行两次相同的查询
	 * 		测试结果：同一个sqlsession会话只会发送一条sql
	 * 原因：一级缓存默认永远开启，不能修改
	 */
	@Test
	 public void testL1Cache1() throws IOException {
		SqlSession sqlSession = getSqlSession();
		AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
		List< Author> list = mapper.selectAllAuthor();
		for (Author author : list) {
			System.out.println(author);
		}
		System.out.println("==================同一个会话第二次查询");
		List<Author> list2 = mapper.selectAllAuthor();
		for (Author author : list2) {
			System.out.println(author);
		}
		sqlSession.close();
	}
	/**
	 * 一级缓存 sqlsession会话级别 测试一级缓存
	 * @throws IOException
	 * 测试二：同一个sqlsession执行两次相同的查询，中间插入一个清空缓存操作
	 * 		测试结果：同一个sqlsession会话发送了两条sql
	 * 原因：因为缓存被清空，直接在去数据库查询，所以发送两次sql
	 */
	@Test
	 public void testL1Cache2() throws IOException {
		SqlSession sqlSession = getSqlSession();
		AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
		List< Author> list = mapper.selectAllAuthor();
		for (Author author : list) {
			System.out.println(author);
		}
		System.out.println("清空缓存，在执行查询");
		//清空缓存，在执行查询
		sqlSession.clearCache();
		System.out.println("==================同一个会话第二次查询");
		List<Author> list2 = mapper.selectAllAuthor();
		for (Author author : list2) {
			System.out.println(author);
		}
		sqlSession.close();
	}
	/**
	 * 一级缓存 sqlsession会话级别 测试一级缓存
	 * @throws IOException
	 * 测试三：同一个sqlsession执行两次相同的查询，中间插入一个增删改操作
	 * 		测试结果：同一个sqlsession会话发送了三条sql
	 * 原因：增删改操作都会刷新缓存，所以一共3条
	 */
	@Test
	 public void testL1Cache3() throws IOException {
		SqlSession sqlSession = getSqlSession();
		AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
		List< Author> list = mapper.selectAllAuthor();
		for (Author author1 : list) {
			System.out.println(author1);
		}
		System.out.println("两次查询中间增加一个添加操作");
		//两次查询中间增加一个添加操作
		Author author = new Author();
		author.setName("爱因斯坦");
		mapper.insertAuthor(author);
		sqlSession.commit();
		System.out.println("==================同一个会话第二次查询");
		List<Author> list2 = mapper.selectAllAuthor();
		for (Author author2 : list2) {
			System.out.println(author2);
		}
		sqlSession.close();
	}
	/**
	 * 一级缓存 sqlsession会话级别 测试一级缓存
	 * @throws IOException
	 * 测试四：不同sqlsession执行两次相同的查询
	 * 		测试结果：发送两条sql
	 * 原因：一级缓存是sqlsession级别
	 */
	@Test
	 public void testL1Cache4() throws IOException {
		SqlSession sqlSession1 = getSqlSession();
		SqlSession sqlSession2 = getSqlSession();
		AuthorMapper mapper1 = sqlSession1.getMapper(AuthorMapper.class);
		AuthorMapper mapper2 = sqlSession2.getMapper(AuthorMapper.class);
		List< Author> list = mapper1.selectAllAuthor();
		for (Author author1 : list) {
			System.out.println(author1);
		}
		System.out.println("==================不同会话进行两次相同的查询");
		List<Author> list2 = mapper2.selectAllAuthor();
		for (Author author2 : list2) {
			System.out.println(author2);
		}
		sqlSession1.close();
		sqlSession2.close();
	}

}
