package com.mybatis.mapper1.differentfields.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.mybatis.entity.Manager;
import com.mybatis.mapper1.differentfields.ManagerMapper;

public class TestManager {

	static String path = "mybatis.xml";

	// 获取sqlsession
	public static SqlSession getSqlSession() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream(path);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		// 默认不自动提交事务
		SqlSession sqlSession = factory.openSession();
		return sqlSession;
	}

	// 表字段和对象属性不同，测试添加一条数据
	@Test
	public void testInsertOne() throws IOException {
		SqlSession sqlSession = getSqlSession();
		ManagerMapper managerMapper = sqlSession.getMapper(ManagerMapper.class);
		Manager manager = new Manager();
		manager.setName("泰迪");
		manager.setGender("男");
		managerMapper.insertOne(manager);
		sqlSession.commit();
		System.out.println("查看返回的主键" + manager.getId());
		sqlSession.close();
	}

	// 测试添加多条数据
	@Test
	public void testInsertList() throws IOException {
		SqlSession sqlSession = getSqlSession();
		ManagerMapper managerMapper = sqlSession.getMapper(ManagerMapper.class);
		Manager manager1 = new Manager();
		manager1.setName("马犬");
		manager1.setGender("女");
		Manager manager2 = new Manager();
		manager2.setGender("女");
		manager2.setName("马犬2号");
		List<Manager> list = new ArrayList<Manager>();
		list.add(manager1);
		list.add(manager2);
		// list为空sql会报错，解析不出来
		managerMapper.insertList(list);
		sqlSession.commit();
		System.out.println(manager1.getId() + "," + manager2.getId());
		sqlSession.close();
	}

	// 查询所有数据
	@Test
	public void testSelectAll() throws IOException {
		SqlSession sqlSession = getSqlSession();
		ManagerMapper managerMapper = sqlSession.getMapper(ManagerMapper.class);
		List<Manager> list = managerMapper.selectAll();
		for (Manager manager : list) {
			System.out.println(manager);
		}
		sqlSession.close();
	}

	// 查询一条数据
	@Test
	public void testSelectOne() throws IOException {
		SqlSession sqlSession = getSqlSession();
		ManagerMapper managerMapper = sqlSession.getMapper(ManagerMapper.class);
		List<Manager> list = managerMapper.selectOne("二哈", 1);
		for (Manager m : list) {
			System.out.println(m);
		}
		sqlSession.close();
	}

	// 通过id和姓名修改性别
	@Test
	public void testUpdate() throws IOException {
		SqlSession sqlSession = getSqlSession();
		ManagerMapper managerMapper = sqlSession.getMapper(ManagerMapper.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 1);
		map.put("name", "二哈");
		List<Manager> list = managerMapper.selectOne((String) map.get("name"), (Integer) map.get("id"));
		if (list.size() > 0) {
			for (Manager manager : list) {
				System.out.println("修改前" + manager);
			}
			managerMapper.updatGenderByIdAndName(map, "男");
			sqlSession.commit();
			List<Manager> list1 = managerMapper.selectOne((String) map.get("name"), (Integer) map.get("id"));
			for (Manager manager : list1) {
				System.out.println("修改后" + manager);
			}
		} else {
			System.out.println("没有这个数据");
		}
		sqlSession.close();
	}
	
	//通过id或者性别删除 map封装的key为1和2
	@Test
	public void testDelete() throws IOException {
		SqlSession sqlSession = getSqlSession();
		ManagerMapper managerMapper = sqlSession.getMapper(ManagerMapper.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",12);
		map.put("gender","男");
		managerMapper.deleteByIdOrgender(map);
		sqlSession.commit();
		for (Manager manager : managerMapper.selectAll()) {
			//删除后剩下的数据
			System.out.println(manager);
		}
		sqlSession.close();
	}

}
