package cn.tedu.controller;

import cn.tedu.inputDTO.FindAllListInputDTO;
import cn.tedu.Utils.ObjectMapperUtil;
import cn.tedu.enums.ProcessCodeEnum;
import cn.tedu.pojo.ResultVO;
import cn.tedu.pojo.User;
import cn.tedu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;	
	@Autowired
	private JedisCluster jedis;
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	
	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResultVO<List<User>> findAllUser(@RequestBody @Validated FindAllListInputDTO inputDTO) {
		log.info("FindAllListInputDTO={}", inputDTO);

		log.debug("debug");

		log.info("info");

		log.error("error");

		log.warn("warn");
//		throw ProcessCodeEnum.PARM_WARM.buildException("测试一下异常");
		List<User> result = userService.findAll(inputDTO.getIdUser());
		return ProcessCodeEnum.SUCCESS.buildSuccessResultVO(result);
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