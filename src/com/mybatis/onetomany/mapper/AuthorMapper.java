package com.mybatis.onetomany.mapper;

import java.util.List;


import com.mybatis.onetomany.entity.Author;

/**
 * 操作author
 * @author Administrator
 *
 */
public interface AuthorMapper {
	
	//查询所有的author数据
	public List<Author> selectAllAuthor();
	//查询所有的author数据和他们的blog(集合嵌套结果映射)
	public List<Author> selectAuthorsAndBlogs();
	//查询所有的author数据和他们的blog(集合嵌套结果映射，可重用resultmap)
	public List<Author> selectAuthorsAndBlogsResuing();
	//查询所有的author数据和他们的blog,集合的嵌套select查询byauthor_id
	public List<Author> selectAuthorsNestSelect(int id);
	//根据姓名查询author
	public Author selectByName(String name);
	
	//添加author
	public void insertAuthors(List<Author> list);
	//添加一个author
	public void insertAuthor(Author author);
}
