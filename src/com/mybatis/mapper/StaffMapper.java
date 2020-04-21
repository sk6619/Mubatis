package com.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.mybatis.entity.Staff;

public interface StaffMapper {
	//查询一个员工
	Staff selectOne(int id);
	//查询所有员工
	List<Staff> selectAllStaff();
	//修改员工信息
	@Select(value = "update staff set name = #{name} where id = #{id}")
	void update(Staff staff);

}
