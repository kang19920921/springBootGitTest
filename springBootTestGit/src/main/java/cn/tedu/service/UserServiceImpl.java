package cn.tedu.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.tedu.mapper.UserMapper;
import cn.tedu.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> findAll(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.findAll(id);
	}
	
	
	
	public List<User> findAllById(Integer id) {
		// TODO Auto-generated method stub
		
		return userMapper.findAllById(id);
	}

}
