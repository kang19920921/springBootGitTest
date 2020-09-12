package cn.tedu.pojo;

import java.util.Date;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

import cn.tedu.annotation.RosExcel;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Captkang
 * @date: 2020年9月6日下午5:34:06
 */

@Getter
@Setter
public class RosExcelDemoDTO extends RosExcelBaseDTO {

	private static final long serialVersionUID = -5220295492986205691L;
	
	
	@RosExcel(name = "姓名", column = "B" ,isEXport = true )
	private String name;
	
    @RosExcel(name = "序号" ,column= "A")
    private Integer id;
    
    @RosExcel(name = "年龄", column = "C" ,prompt = "年龄保密哦" , isEXport = false )
    private Integer age;
    
    @RosExcel(name = "班级",column = "D" ,width = 6000,horizontal = HorizontalAlignment.LEFT, combo = {"三期提高班", "四期提高班","五期提高班"  })
    private String clazz;
	
    @RosExcel(name = "公司", column = "E" ,width = 6000 ,horizontal = HorizontalAlignment.LEFT)
	private String  company;
	
    @RosExcel(name = "成功" ,column = "F")
    private Boolean success;
    
    @RosExcel(name = "日期", column = "G" , width = 6000)
    private Date date;
    


}
