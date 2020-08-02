package cn.tedu.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Company implements Serializable {
	
	private static final long serialVersionUID = -6491870211429431922L;

	private String name;

	private String companyCode;

}
