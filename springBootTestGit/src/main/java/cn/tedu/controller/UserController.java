package cn.tedu.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.tedu.pojo.User;

@RestController
@RequestMapping("/user")
public class UserController {
	
	
	
	@RequestMapping("/findAll")
	public List<User> findAllUser() throws InterruptedException {
		boolean lock =true;
		
		int i = 0;
		List<User> list =  new ArrayList<User>();
		while(lock) {
		list.add(new User(1,"alex",18,"mm"));
		i++;
		Thread.sleep(1000);
//		this.wait();
		if(i>=9) {
			lock = false;
		}
		}
				
		return list;
		
	}
	
	
	
	

}
