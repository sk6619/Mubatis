package com.mybatis.onetomany.entity;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * 博客作者 对应数据库tb_authot
 * @author Administrator
 *
 */
@Alias("author")
public class Author implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private List<Blog> list;
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
	public List<Blog> getList() {
		return list;
	}
	public void setList(List<Blog> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + ", list=" + list + "]";
	}
	

}
