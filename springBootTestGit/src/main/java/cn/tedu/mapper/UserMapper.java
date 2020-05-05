package cn.tedu.mapper;

import java.util.List;

import cn.tedu.pojo.User;

//@Repository
//@Component
public interface UserMapper{
	
	List<User> findAll(Integer id);
	
	List<User> findAllById(Integer id);
	

}
