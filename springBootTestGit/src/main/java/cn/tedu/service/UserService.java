package cn.tedu.service;

import java.util.List;

import cn.tedu.pojo.User;

public interface UserService {
	
	List<User> findAll(Integer id );
	
	List<User> findAllById(Integer id );
	
	
//	List<User> selectList(Integer id );
	

}
