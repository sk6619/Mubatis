package com.mybatis.entity;
/**
 * 映射数据库的staff表
 * @author Administrator
 *
 */
public class Staff {
	
	private Integer id;
	
	private String name;
	
	private String gender;

	public Staff(Integer id, String name, String gender) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
	}

	public Staff() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	@Override
	public String toString() {
		return "Staff [id=" + id + ", name=" + name + ", gender=" + gender + "]";
	}
	
}
