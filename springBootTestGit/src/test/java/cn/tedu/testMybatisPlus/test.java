package cn.tedu.testMybatisPlus;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

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
	 * 
	 */
//	@Test
	public void test2() {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~123456789~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");	
	List<User>  list  =	userMapper.selectList(null);
	System.out.println(list);
	
	}
	
	
	/**
	 * 插入数据
	 * 
	 */
//	@Test
	public void test3() {
	 
		 User  user = new User();
		 user.setId(60);
		 user.setAge(18);
		 user.setName("pretty girl");
		 user.setSex("女");
		 userMapper.insert(user);	
	}
	
	/**
	 * 删除数据
	 * 
	 */
//	@Test
	public void test5() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(58);
		list.add(59);
	   Integer  status =	userMapper.deleteBatchIds(list);
 		
		System.out.println(status);
		
	}
	
	/**
	 * 更新数据
	 */
//	@Test
	public void test6() {
		User user = new User();
		user.setId(53);
		user.setName("韩美娟1");
		user.setSex("女");
		
	    Integer status = userMapper.updateById(user);	
		
	}
	
	/**
	 * 条件构造器查询数据
	 */
//	@Test
	public void test7() {
		User user = new User();
		user.setId(53);
		user.setName("韩美娟1");
		//传入user参数
		QueryWrapper<User> wrapper = new QueryWrapper<User>(user);
		
		//根据其中不为null的属性,当做where条件
	    User userDB = userMapper.selectOne(wrapper);	
		System.out.println(userDB);
	    
	}
	
	
	/**
	 * 条件构造器查询数据
	 */
//	@Test
	public void test8() {
		QueryWrapper<User> wrapper = new  QueryWrapper<User>();
		wrapper.eq("name", "pretty girl").eq("age", 18);
		User user = userMapper.selectOne(wrapper);
		
		
	}
	
	
	/**
	 * 条件构造器
	 * 	//1.查询age > 100   >gt <lt =eq >=ge <=le
	 * //2.查询age >100 or age<18
	 * //3.between
	 * //4.查询 name=null的数据
     * 
	 */
//	@Test
	public void  test9() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
//		wrapper.isNull("name");
//		System.out.println(userMapper.selectList(wrapper));		
		//ge
//		wrapper.ge("age", 100);
//		List<User> list = userMapper.selectList(wrapper);	
//		System.out.println(list.toString());

		
		//betewen
		wrapper.between("age", 1, 100);
		List<User> list = userMapper.selectList(wrapper);	
		System.out.println(list.toString());
		
		
		
		
	}
	
	
	
	/**
	 * 要求将name 为null的信息,name设为苏乞儿
	 * entity: 要修改后的数据信息
	 * updateWrapper: 更新的条件构造器
	 */
	@Test
	public void test10() {
		
		User user = new User();
		user.setName("苏乞儿");        		
		UpdateWrapper<User> uWrapper = new UpdateWrapper<>();
		uWrapper.isNull("name");
      	Integer status = 	userMapper.update(user, uWrapper);		
		System.out.println(status);		
	}
	
}