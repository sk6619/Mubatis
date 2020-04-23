package com.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mybatis.entity.Staff;

public interface StaffMapper {
	//查询一个员工，根据id
	Staff selectOne(int id);
	//查询一个员工，根据id和名字
	Staff seleStaffByIdAndName(@Param("id")int id, @Param("name1")String name);
	//查询所有员工
	List<Staff> selectAllStaff();
	//添加员工
	void addOneStaff(Staff staff);
	//修改员工信息
	@Select(value = "update staff set name = #{name} where id = #{id}")
	void update(Staff staff);

}
