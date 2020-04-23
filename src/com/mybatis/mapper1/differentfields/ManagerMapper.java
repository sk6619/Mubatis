package com.mybatis.mapper1.differentfields;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mybatis.entity.Manager;

public interface ManagerMapper {
	//增加一条数据
	void insertOne(Manager manager);
	//添加多条数据
	void insertList(List<Manager> list);
	//查询所有数据
	List<Manager> selectAll();
	//根据条件查询
	List<Manager> selectOne(@Param("name")String name,@Param("id")Integer id);
	//通过id和姓名修改性别
	void updatGenderByIdAndName(@Param("map")Map<String, Object> map,@Param("gender")String gender);
	//通过id或者性别删除
	void deleteByIdOrgender(@Param("map")Map<String,Object> map);
}
