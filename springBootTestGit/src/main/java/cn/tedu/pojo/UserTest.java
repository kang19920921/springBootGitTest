package cn.tedu.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Getter
@Setter
public class UserTest extends BaseObject implements Serializable {
	

	private static final long serialVersionUID = -5082205083787861269L;
	private Date date = new Date();
//	private Integer id ;
	private String  name;
	private Integer age;
	private String  sex;
	

}
