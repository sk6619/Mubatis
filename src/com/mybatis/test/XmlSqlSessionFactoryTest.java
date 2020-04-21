package com.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.mybatis.entity.Staff;

/**
 * 创建SQLSessionFactory和sqlsession
 * 
 * @author Administrator
 *
 */
public class XmlSqlSessionFactoryTest {

	public static SqlSession getSession() throws IOException {
		// 数据库配置路径
		String path = "mybatis.xml";
		// 1将文件加载到流中
		InputStream inputStream = Resources.getResourceAsStream(path);
		// 2从流中解析创建sqlsessionfactory对象
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 3获取sqlsession
		return sqlSessionFactory.openSession();
	}

	@Test
	public void test1() throws IOException {
		// 1将文件加载到流中
		InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
		// 2从流中解析创建sqlsessionfactory对象
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 3获取sqlsession
		SqlSession session = sqlSessionFactory.openSession();
		// com.mybatis.mapper.StaffMapper.selectAllStaff
		// 命名空间加上语句id属性
		Staff staff = session.selectOne("com.mybatis.mapper.StaffMapper.selectOne",1);
		System.out.println(staff);
		session.close();
	}
	
	//查询所有员工
	@Test
	public void testSelectAll() throws IOException {
		SqlSession session = getSession();
		List<Staff> list = session.selectList("com.mybatis.mapper.StaffMapper.selectAllStaff");
		for (Staff staff : list) {
			System.out.println(staff);
		}
		session.close();
	}

	// 添加一个员工
	@Test
	public void testAddaStaff() throws IOException {
		SqlSession session = getSession();
		Staff staff = new Staff();
		staff.setName("李四");
		staff.setGender("男");
		System.out.println(session.insert("com.mybatis.mapper.StaffMapper.addOneStaff",staff));
		session.commit();
		session.close();
	}
	//删除部分员工
	@Test
	public void testDeleteSome() throws IOException {
		SqlSession session = getSession();
		//返回影响了多少行
		int count = session.delete("com.mybatis.mapper.StaffMapper.deleteSome", 1);
		System.out.println(count);
		session.commit();
		session.close();
	}
	//修改一个员工信息
	@Test
	public void testUpdate() throws IOException {
		SqlSession session = getSession();
		Staff staff = new Staff();
		staff.setName("张小三");
		staff.setId(1);
		int count = session.update("com.mybatis.mapper.StaffMapper.updateOne", staff);
		System.out.println(count);
		session.commit();
		session.close();
	}
}
