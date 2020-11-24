package cn.tedu.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.tedu.Utils.ObjectMapperUtil;
import cn.tedu.pojo.User;
import cn.tedu.service.UserService;
import redis.clients.jedis.JedisCluster;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;	
	@Autowired
	private JedisCluster jedis;
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	
	
	@RequestMapping("/findAll")
	@ResponseBody
	public List<User> findAllUser()  {
	
		return userService.findAll(1);
		
	}
	
	
	@RequestMapping("/findRedis/{id}")
	@ResponseBody //避免被当做路径，不走视图解析器
	public List<User>  findAllUserByRedis(@PathVariable String id)  {
		List<User>  list  = new ArrayList<>();
		System.out.println(jedis.toString());
		
		//1 先从缓存中获取
         String userList =  jedis.get("User:"+id);
		if (userList!=null) {
			System.out.println("userList " + userList);
			return ObjectMapperUtil.toObject(userList, list.getClass());
		}	
		//缓存查询不到结果再从数据库中查询
		list = userService.findAllById(Integer.valueOf(id));		
		//存入缓存
		userList  =  ObjectMapperUtil.toJSON(list);
		jedis.set("User:"+id,userList);
		
	    //返回结果
		return list;
		
	}
	
	  @RequestMapping("/sendUserByMq/{name}/{age}/{sex}")
	  @ResponseBody
      public String insertUser(@Validated  User user) {
    	  
		  amqpTemplate.convertAndSend("testQueue1",user);
		  System.out.println(user);
    	      	  
		return "发送数据到mq成功！！！";   	     	  
      }
	
	
//	  @RequestMapping("/sendUserByMq/{name}/{age}/{sex}")
//	  @ResponseBody
//      public String   insertUser(User user) {
//    	  
//		  amqpTemplate.convertAndSend("testQueue",user);
//		  System.out.println(user);
//    	      	  
//		return "发送数据到mq成功！！！";   	     	  
//      }
//	
	
}