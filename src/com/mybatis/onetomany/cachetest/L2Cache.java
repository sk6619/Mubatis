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

public class L2Cache {
	static SqlSessionFactory factory;

	// 加载唯一一个factory，因为二级缓存依赖factory
	static {
		try {
			String path = "mybatis.xml";
			InputStream inputStream = Resources.getResourceAsStream(path);
			factory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//创建sqlsession
	public static SqlSession getSqlsession() {
		//默认关闭事务自动提交
		SqlSession sqlSession = factory.openSession();
		return sqlSession;
	}

	/**
	 * 二级缓存测试（先开启全局二级缓存设置，在具体使用二级缓存的mapper文件天剑<cache/>） 测试一：使用两个不同的sqlsession执行相同的查询
	 * 结果：发送两条sql，缓存命中率为0即缓存中没有数据；报异常
	 * 原因：sqlsession关闭或者commit才会提交到二级缓存；报异常是因为实体类没有序列化
	 * 
	 * @throws IOException
	 * 
	 */
	@Test
	public void testL2Cache1() throws IOException {
		// 第一次查询
		SqlSession sqlSession1 = getSqlsession();
		AuthorMapper mapper1 = sqlSession1.getMapper(AuthorMapper.class);
		List<Author> list1 = mapper1.selectAllAuthor();
		for (Author author : list1) {
			System.out.println(author);
		}
		// 不同sqlsession第二次查询
		SqlSession sqlSession2 = getSqlsession();
		AuthorMapper mapper2 = sqlSession2.getMapper(AuthorMapper.class);
		List<Author> list2 = mapper2.selectAllAuthor();
		for (Author author : list2) {
			System.out.println(author);
		}
		sqlSession1.close();
		sqlSession2.close();
	}

	/**
	 * 二级缓存测试（先开启全局二级缓存设置，在具体使用二级缓存的mapper文件添加<cache/>）
	 * 测试二：使用两个不同的sqlsession执行相同的查询(一个连接关闭后在执行第二个连接) 
	 * 结果： 执行一条sql
	 * 原因：成功
	 * 二级缓存依赖SqlsessionFactory
	 * @throws IOException
	 */
	@Test
	public void testL2Cache2() throws IOException {
		SqlSession sqlSession1 = getSqlsession();
		SqlSession sqlSession2 = getSqlsession();
		// 第一次查询
		AuthorMapper mapper1 = sqlSession1.getMapper(AuthorMapper.class);
		List<Author> list1 = mapper1.selectAllAuthor();
		for (Author author : list1) {
			System.out.println(author);
		}
		// 关闭第一个session会隐式的执行commit,并且把数从一级缓存更新到二级缓存
		sqlSession1.commit();
		sqlSession1.close();
		// 换个不同的sqlsession第二次查询
		AuthorMapper mapper2 = sqlSession2.getMapper(AuthorMapper.class);
		List<Author> list2 = mapper2.selectAllAuthor();
		for (Author author : list2) {
			System.out.println(author);
		}
		sqlSession2.commit();
		sqlSession2.close();
	}

	//关联查询
	@Test
	public void testL2Cache3() throws IOException {
		SqlSession sqlSession = getSqlsession();
		AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
		List<Author> list = mapper.selectAuthorsAndBlogsResuing();
		for (Author author : list) {
			System.out.println(author);
		}
		sqlSession.close();
		sqlSession = getSqlsession();
		mapper = sqlSession.getMapper(AuthorMapper.class);
		list = mapper.selectAuthorsAndBlogsResuing();
		for (Author author : list) {
			System.out.println(author);
		}
		sqlSession.close();
	}

}
