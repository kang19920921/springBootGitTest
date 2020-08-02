package cn.tedu.Utils;
import java.util.ArrayList;
import java.util.List;
import cn.tedu.pojo.Company;
import cn.tedu.pojo.User;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class test2 {
	//
	public static void main(String[] args) {
		List<User> list = new ArrayList<User>();
			
		User user = new User();
		user.setAge(18).setId(1).setName("test").setSex("男");
		Company  c = new Company();
		c.setCompanyCode("123");
		c.setName("测试公司");
		user.setCompany(c);
		
		System.out.println(user.toString());
		list.add(user);
		list.add(user);
		User user2 = RosBeanUtil.copyEntry(user, User.class);
		list.add(user2);
		log.info(list.toString());
		List<User> list2 = ListUtils.limitArray(list, 2);
		log.info(list2.toString());
    
	
		
		
	}
	
}
