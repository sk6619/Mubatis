package com.mybatis.entity;

import org.apache.ibatis.type.Alias;

/**
 * 和数据库字段不一样
 * @author Administrator
 *	别名：@Alias("manager")
 */
@Alias("manager")
public class Manager {
	
	private int id;
	private String name;
	private String gender;
	@Override
	public String toString() {
		return "Manager [id=" + id + ", name=" + name + ", gender=" + gender + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	

}
