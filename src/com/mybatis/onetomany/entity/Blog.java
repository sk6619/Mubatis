package com.mybatis.onetomany.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 博客实体，对应数据库tb_blog表
 * @author Administrator
 *
 */
@Alias("blog")
public class Blog implements Serializable{
	private static final long serialVersionUID = 2L;
	private Author author;
	private int id;
	private String name;
	
	public Blog(Author author, int id, String name) {
		super();
		this.author = author;
		this.id = id;
		this.name = name;
	}
	public Blog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
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
	@Override
	public String toString() {
		return "Blog [author=" + author + ", id=" + id + ", name=" + name + "]";
	}
	
	

}
