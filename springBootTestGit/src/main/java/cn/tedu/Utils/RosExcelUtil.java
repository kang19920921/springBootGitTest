package cn.tedu.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.alibaba.fastjson.util.IOUtils;

import com.sun.tools.internal.ws.processor.ProcessorException;

import cn.tedu.annotation.RosExcel;
import cn.tedu.pojo.RosExcelBaseDTO;
import cn.tedu.pojo.RosExcelDemoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RosExcelUtil<T extends RosExcelBaseDTO > {
	
	public static final int DEFAULT_MAX_ROWNUM = 65525;
	
	private Class<T> clazz;
	
	public RosExcelUtil(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	
	public static void main(String[] args) throws Exception {
//		testExport();
		System.out.println(getExcelCol("A"));
	}


	private static void testExport() throws Exception {
		//初始化数据
		List<RosExcelDemoDTO> list  =  new ArrayList<RosExcelDemoDTO>();
		
		RosExcelDemoDTO demoDTO =  new RosExcelDemoDTO();
		demoDTO.setId(3);
		demoDTO.setName("张三");
		demoDTO.setAge(33);
		demoDTO.setClazz("三期提高班");
		demoDTO.setCompany("公司3");
		demoDTO.setDate(new Date());
		list.add(demoDTO);
		
		RosExcelDemoDTO demoDTO2 =  new RosExcelDemoDTO();
		demoDTO2.setId(4);
		demoDTO2.setName("李四");
		demoDTO2.setAge(44);
		demoDTO2.setClazz("四期提高班");
		demoDTO2.setCompany("公司4");
		demoDTO2.setDate(new Date());
		list.add(demoDTO2);
		
		
		RosExcelDemoDTO demoDTO3 =  new RosExcelDemoDTO();
		demoDTO.setId(5);
		demoDTO.setName("王五");
		demoDTO.setAge(55);
		demoDTO.setClazz("五期提高班");
		demoDTO.setCompany("公司5");
		demoDTO.setDate(new Date());
		list.add(demoDTO3);
		
		
		File  file  = new  File("E:\\excel\\test001.xls");
		System.out.println(file.getAbsolutePath());
		file.createNewFile();
		FileOutputStream  out  = null;
		try {
			out = new FileOutputStream(file);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	    RosExcelUtil<RosExcelDemoDTO> util = new RosExcelUtil<RosExcelDemoDTO>(RosExcelDemoDTO.class);//创建工具类
	    util.exportExcel(list , "学生信息" ,out);//导出数据
				
	}
    
	
	//导出数据
	/**
	 * 
	 * @author Captkang
	 * @date: 2020年9月6日下午10:05:56
	 * @param list  数据源
	 * @param string 工作表名称
	 * @param out  输出流
	 * @throws Exception 
	 * 
	 */
	private void exportExcel(List<T> dataList, String sheetName, FileOutputStream os) throws Exception {

		try {
			List<T>[] dateLsitArr = new ArrayList[1];
			dateLsitArr[0] = dataList;

			String[] sheetNames = new String[1];
			sheetNames[0] = sheetName;
			//执行导出excel文件
			excuteExportExcel(dateLsitArr, sheetNames,os);

		} catch (ProcessorException e) {
			throw e;
		}catch (Exception e) {
			log.error("导出excel文件异常", e);
			throw new RuntimeException("导出文件异常");
		}finally {
			IOUtils.close(os);
		}

	}

	
	/**
	 * 
	 * @author Captkang
	 * @date: 2020年9月8日下午8:58:49
	 * @param dateLsitArr 数据源列表
	 * @param sheetNameArr 工作表的名称列表  必传
	 * @param os
	 * 
	 */
	private void excuteExportExcel(List<T>[] dateLsitArr, String[] sheetNames, FileOutputStream os) {
		log.info("========= 执行导出excel文件start=====");
		long stratTime = System.currentTimeMillis();
		if(dateLsitArr.length != sheetNames.length) {
			throw new RuntimeException("数据源列表数量和sheet数量不一致");
		}
		for(List<T> dateList : dateLsitArr ) {
			if(dateList.size() >= DEFAULT_MAX_ROWNUM  ) {
				throw new RuntimeException("sheet中的数量不能超过最大值！");
			}
		}
		
		// 产生工作簿对象
		HSSFWorkbook  workbook  = new HSSFWorkbook();		
		try {
			
			for(int i = 0; i < dateLsitArr.length;i++) {
				excuteExportSheet(dateLsitArr[i],workbook,sheetNames[i], i);
			}
			os.flush();
			workbook.write(os);
		}catch (ProcessorException e) {
			throw e;
		}catch (Exception e) {
			throw new RuntimeException("执行导出excel文件异常");
		}finally {
			IOUtils.close(workbook);
			IOUtils.close(os);
			log.info("===========执行导出excel文件 end 耗时{}毫秒=== ",System.currentTimeMillis() - stratTime);
		}
	}

	/**
	 * 导出excel文件中的一个sheet
	 * 
	 * @author Captkang
	 * @date: 2020年9月9日下午10:09:55
	 * @param list
	 * @param workbook
	 * @param sheetName
	 * @param sheetIndex
	 */
	private void excuteExportSheet(List<T> list, HSSFWorkbook workbook, String sheetName, int sheetIndex) {
		//创建sheet对象
		HSSFSheet  sheet  =  workbook.createSheet();
		workbook.setSheetName(sheetIndex, sheetName);
		
		//创建标题行
		List<Field> fieldList = createTitleRow(workbook,sheet);	
	}

	//创建标题行
	private List<Field> createTitleRow(HSSFWorkbook workbook, HSSFSheet sheet) {
	   //得到实体类所有通过注解映射了数据表的字段
		List<Field> fields = getAnnotatonField(clazz , null);
		HSSFRow   titleRow = sheet.createRow(0);
		HSSFCell  cell;
		HSSFCellStyle  cellStyle;
		HSSFDataFormat format = workbook.createDataFormat();
		HSSFCellStyle style1  = workbook.createCellStyle();
		style1.setDataFormat(format.getFormat("@"));//设置格式为文本格式
		//写入个字段的列头名称
		for(int i = 0; i< fields.size(); i++) {
			Field field = fields.get(i);
			RosExcel  annotation = field.getAnnotation(RosExcel.class); //获取这个字段被注解的值
			// 设置单元格宽度
			sheet.setColumnWidth(i, annotation.width());
			//设置单元格样式等
			int  col = getExcelCol(annotation.column());// 获得第几列
			cell = titleRow.createCell(col);// 第一行 第col列  的一个单元格
			cell.setCellType(CellType.STRING); //设置列(单元格)中写入内容为String类型
			cell.setCellValue(annotation.name());//写入列名
			
			//如果设置了提示信息则鼠标放上去提示
			if(!annotation.prompt().trim().equals("")) {
				setHSSFPrompt(sheet,"",annotation.prompt(),1,10000,col,col); //这里默认设置了 2-100001
			}
			
			
			//如果设置了combo 属性，则本列只能选择不能输入
			if(annotation.combo().length > 0) {
				setHSSFValidation(sheet, annotation.combo(),1,10000,col,col); //这里默认设置了 2-100001
			}
			
			
			cellStyle = createTitleCellStyle(workbook,annotation);
			cell.setCellStyle(cellStyle);
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		return null;
	}

	/**
     * 得到实体类所有通过注解映射了数据表的字段
     * @author Captkang
     * @date: 2020年9月12日下午3:38:07
     * @param clazz
     * @param fields
     * @return
     */
	private List<Field> getAnnotatonField(Class clazz, List<Field> fields) {
		//得到实体类所有通过注解映射了数据表的字段
		if (fields == null) {
			fields = new ArrayList<Field>();
		}
		Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段

		for (Field field : allFields) {
			if (field.isAnnotationPresent(RosExcel.class)) {// 是否 被RosExcel注解
				fields.add(field);
			}
		}
		if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)) {
			getAnnotatonField(clazz.getSuperclass(), fields);// 递归方式 获取父类被注解的字段
		}

		return fields;
	}
	
	
	
   /**
    * 将excel中的A,B,C,D,E列映射成0,1,2,3····
    * @author Captkang
    * @date: 2020年9月12日下午2:47:47
    * @param column
    * @return
    */
	private static int getExcelCol(String col) {
		col = col.toUpperCase();
		// 从-1 开始计算,字母从1开始运算。这样总数下来算数正好相同
		int count = -1;
		char[] cs = col.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
		}

		return count;
	}

	

    /**
     * 设置单元格上的提示
     * 
     * @author Captkang  
     * @date: 2020年9月12日下午4:20:
     * @param sheet  要设置的sheet
     * @param promptTitle  标题
     * @param prompt       内容
     * @param firstRow     开始行  
     * @param lastRow      终止行          
     * @param firstCol     开始列 
     * @param lastCol      结束列
     * 
     * return 设置好的sheet
     */
    private HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle, String promptContent, int firstRow, int lastRow, int firstCol, int lastCol) {
		
    	//构造constrant 对象
    	DVConstraint  constraint = DVConstraint.createCustomFormulaConstraint("DD1");
    	//四个参数分别是： 起始行、终止行、起始列、终止列
    	CellRangeAddressList  regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
    	//数据有效性对象
    	HSSFDataValidation validation = new HSSFDataValidation(regions, constraint);
    	validation.createPromptBox(promptTitle, promptContent);
    	sheet.addValidationData(validation);
    	
    	return  sheet;		
	}
    

     /**
      * 设置某些列只能输入预制的数据，显示下拉框
      * @author Captkang
      * @date: 2020年9月12日下午4:53:23
      * @param sheet
      * @param string
      * @param prompt
      * @param i
      * @param j
      * @param col
      * @param col2
      */
	private HSSFSheet setHSSFValidation(HSSFSheet sheet, String[] testLsit, int firstRow, int lastRow, int firstCol, int lastCol) {
    	//构造constrant 对象
    	DVConstraint  constraint = DVConstraint.createExplicitListConstraint(testLsit);
    	//四个参数分别是： 起始行、终止行、起始列、终止列
    	CellRangeAddressList  regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
    	//数据有效性对象
    	HSSFDataValidation validation = new HSSFDataValidation(regions, constraint);

    	sheet.addValidationData(validation);
		
		
		return sheet;	
	}
	
	

     /**
      * 创建标题单元格样式
      * @author Captkang
      * @date: 2020年9月12日下午5:11:34
      * @param workbook  工作簿
      * @param annotation  
      * @return
      */
	private HSSFCellStyle createTitleCellStyle(HSSFWorkbook workbook, RosExcel annotation) {
		
		// 创建普通单元格的样式
		HSSFCellStyle titleStyle = createCommonCellStyle(workbook , HorizontalAlignment.CENTER);
		//设置水平对齐
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		
		// 设置标题单元格字体样式
		HSSFFont titleFont = createFont(workbook);
		// 设置标题单元格背景色
		titleStyle.setFillForegroundColor(HSSFColorPredefined.LAVENDER.getIndex());
		titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		titleStyle.setFont(titleFont);
		return titleStyle;
	}

     /**
      * 创建标题单元格字体样式
      * @author Captkang
      * @date: 2020年9月12日下午5:58:25
      * @param workbook
      * @return
      */
	private HSSFFont createFont(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBold(true);
		// 设置字体名字
		font.setFontName("仿宋_GB2312");

		return font;
	}

    
	private HSSFCellStyle createCommonCellStyle(HSSFWorkbook workbook, HorizontalAlignment center) {
		//样式
		
		// TODO Auto-generated method stub
		return null;
	}




}

