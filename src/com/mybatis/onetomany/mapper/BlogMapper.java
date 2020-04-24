package com.mybatis.onetomany.mapper;

import java.util.List;

import com.mybatis.onetomany.entity.Blog;
/**
 * 操作blog
 * @author Administrator
 *
 */
public interface BlogMapper {
	
	//查询所有blogs
	public List<Blog> selectBlogs();
	//查询blogs的同时把author查出来（结果集嵌套）
	public List<Blog> selectBlogsAndAuthorByResultNest();
	//查询blogs的同时把去偷红人查出来（select嵌套）byauthor_id
	public List<Blog> selectBlogsAndAuthorBySelectNest(int id);

}
