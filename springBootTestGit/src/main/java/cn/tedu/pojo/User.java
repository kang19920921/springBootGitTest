package cn.tedu.pojo;

import java.io.Serializable;

import lombok.experimental.Accessors;


@Accessors(chain = true)
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5082205083787861269L;
	private Integer id ;
	private String  name;
	private Integer age;
	private String  sex;
	
	
	
	
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public User(Integer id, String name, Integer age, String sex) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
	}
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
	
	
	

}
