package cn.tedu.testMybatisPlus;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.GitMain;
import cn.tedu.mapper.UserMapper;
import cn.tedu.pojo.User;

@RunWith(SpringRunner.class)	
@SpringBootTest
public class test {
	
	
	@Autowired
	private UserMapper userMapper;
	
//	@Test
	public void test1() {
		
		System.out.println("~~~~~~1234789~~~~~");
		
		List<User>  list  =	userMapper.findAll(0);
		System.out.println(list);
	}
	
	
	/**
	 * 查询所有对象
	 */
	@Test
	public void test2() {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~123456789~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");	
	List<User>  list  =	userMapper.selectList(null);
	System.out.println(list);
	
	}
	
	

}
