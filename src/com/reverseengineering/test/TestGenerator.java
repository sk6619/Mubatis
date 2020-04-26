package com.reverseengineering.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.reverseengineering.entity.TbReverse;
import com.reverseengineering.entity.TbReverseExample;
import com.reverseengineering.mapper.TbReverseMapper;

public class TestGenerator {

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
	//测试一个查询
	@Test
	public void test() throws IOException {
		SqlSession sqlSession = getSqlSession();
		TbReverseMapper mapper = sqlSession.getMapper(TbReverseMapper.class);
		System.out.println(mapper.countByExample());
		sqlSession.close();
		
	}

}
