package com.mybatis.test;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

import com.mybatis.entity.Staff;
import com.mybatis.mapper.StaffMapper;
import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

/**
 * 不使用xml创建SqlsessionFactory
 * 
 * @author Administrator
 *
 */
public class JavaCodeSqlsessionFactory {
	// 返回创建好的session
	public static SqlSession getSqlSession() {
		// 从内到外
		DataSource dataSource = new PooledDataSource("com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://192.168.3.103:3306/mybatis?serverTimezone=Asia/Shanghai", "shaokui", "15280936619");
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("development", transactionFactory, dataSource);
		Configuration configuration = new Configuration(environment);
		configuration.addMapper(StaffMapper.class);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		SqlSession session = sqlSessionFactory.openSession();// 默认自动提交事务关闭
		return session;
	}

	// 测试查询是一个数据能否连接数据库
	@Test
	public void test1() {
		SqlSession sqlSession = getSqlSession();
		StaffMapper staffMapper = sqlSession.getMapper(StaffMapper.class);
		Staff staff = staffMapper.selectOne(1);
		System.out.println(staff);
		sqlSession.close();
	}

	// 根据id 和名字查询

	@Test
	public void test2() {
		SqlSession sqlSession = getSqlSession();
		StaffMapper staffMapper = sqlSession.getMapper(StaffMapper.class);
		Staff staff1 = staffMapper.seleStaffByIdAndName(1,"邵洁");
		System.out.println(staff1);
		sqlSession.close();
	}

	// 添加员工,测试返回主键
	@Test
	public void testInsert() {
		SqlSession sqlSession = getSqlSession();
		Staff staff = new Staff();
		staff.setName("张三");
		staff.setGender("男");
		StaffMapper staffMapper = sqlSession.getMapper(StaffMapper.class);
		staffMapper.addOneStaff(staff);
		System.out.println(staff.getId());
		sqlSession.commit();
		sqlSession.close();
	}

	// 查询所有员工
	@Test
	public void testSelectAll() throws IOException {
		SqlSession sqlsession = getSqlSession();
		StaffMapper staffMapper = sqlsession.getMapper(StaffMapper.class);
		// 查看获得的mapper是jdk代理
		System.out.println(staffMapper.getClass().getName());
		List<Staff> list = staffMapper.selectAllStaff();
		for (Staff staff : list) {
			System.out.println(staff);
		}
		sqlsession.close();
	}

	// @select注解
	@Test
	public void test() {
		SqlSession session = getSqlSession();
		StaffMapper staffMapper = session.getMapper(StaffMapper.class);
		Staff staff = new Staff();
		staff.setName("邵洁");
		staff.setId(1);
		;
		staffMapper.update(staff);
		session.commit();
		session.close();
	}
}
