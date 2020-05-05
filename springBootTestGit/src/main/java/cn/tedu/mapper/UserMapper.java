package cn.tedu.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.tedu.pojo.User;

//@Repository
//@Component
public interface UserMapper{
	
	List<User> findAll(Integer id);
	

}
