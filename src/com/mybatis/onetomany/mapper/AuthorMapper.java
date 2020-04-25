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
	//根据姓名查询，或者根据姓名和id一起查询author  if标签
	public Author selectByNameOrId(Author author);
	//如过传入了id就按id查找，如果传入了name就按照name查找    choose when otherwise
	public Author selectAuthorIfIdORIfName(Author author);
	//添加author
	public void insertAuthors(List<Author> list);
	//添加一个author
	public void insertAuthor(Author author);
}
