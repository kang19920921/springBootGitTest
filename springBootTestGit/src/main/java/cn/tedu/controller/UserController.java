package cn.tedu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.tedu.pojo.User;
import cn.tedu.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/findAll")
	public List<User> findAllUser()  {
	
		return userService.findAll(1);
		
	}
	
	@RequestMapping("/findAll/{id}")
	public List<User> findAllUserById(@PathVariable Integer id)  {
	
		return userService.findAll(id);
		
	}
	
	@RequestMapping("/findAll/{id}")
	public List<User> findAllUserByRedis(@PathVariable Integer id)  {
	
		return userService.findAll(id);
		
	}
	
	
	
	

}