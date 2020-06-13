package cn.tedu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.tedu.pojo.User;

//@Repository
@Mapper
public interface UserMapper  extends BaseMapper<User> {	
	
	List<User> findAll(Integer id);
	
	List<User> findAllById(Integer id);
	

}
