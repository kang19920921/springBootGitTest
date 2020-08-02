package cn.tedu.Utils;
import cn.tedu.pojo.Company;
import cn.tedu.pojo.User;
import cn.tedu.pojo.UserTest;

public class test {
	//
	public static void main(String[] args) {
		User user = new User();
		user.setAge(18).setId(1).setName("test").setSex("男");
		Company  c = new Company();
		c.setCompanyCode("123");
		c.setName("测试公司");
		user.setCompany(c);
		System.out.println(user.toString());
		
		User userTest = RosBeanUtil.copyEntry(user,User.class);
		System.out.println(user.getDate());
		System.out.println(userTest.getDate());
		System.out.println(user.getDate() ==userTest.getDate());
		System.out.println(user.getCompany() ==userTest.getCompany());
		
		
		
		User deepClone = RosBeanUtil.deepClone(user);
		System.out.println(user.getCompany() ==deepClone.getCompany());
	
		
		
	}
	
}
