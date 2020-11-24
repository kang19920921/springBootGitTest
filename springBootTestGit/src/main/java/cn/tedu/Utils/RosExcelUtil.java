package cn.tedu.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import cn.tedu.annotation.RosExcel;
import cn.tedu.bo.RosExcelReadResultBO;
import cn.tedu.pojo.RosExcelBaseDTO;
import cn.tedu.pojo.RosExcelDemoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RosExcelUtil<T extends RosExcelBaseDTO > {
	
	public static final int DEFAULT_MAX_ROWNUM = 65525;
	
	public static final String SUFFIX_XLS_2003 = ".xls";
	
	private final SimpleDateFormat  sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Class<T> clazz;
	
	public RosExcelUtil(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	
	public static void main(String[] args) throws Exception {
		testExport();
//		System.out.println(getExcelCol("A"));
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
		demoDTO3.setId(5);
		demoDTO3.setName("王五");
		demoDTO3.setAge(55);
		demoDTO3.setClazz("五期提高班");
		demoDTO3.setCompany("公司5");
		demoDTO3.setDate(new Date());
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
    
    /**
     * 读取excel文件
     * @author Captkang
     * @date: 2020年11月22日下午4:43:57
     * @param file
     * @return
     */
    public RosExcelReadResultBO<T> readExcel(MultipartFile file ){
		return readExcel(file,DEFAULT_MAX_ROWNUM);   	
    }
	
		
	/**
	 * 
	 * @author Captkang
	 * @date: 2020年11月22日下午4:43:35
	 * @param file
	 * @param defaultMaxRownum
	 * @return
	 */
	private RosExcelReadResultBO<T> readExcel(MultipartFile file, int defaultMaxRownum) {
		try {
			// 校验文件格式
			assertIsEXcel2003(file.getOriginalFilename());
			return readExcel(file.getInputStream(), DEFAULT_MAX_ROWNUM);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			log.error("读取excel文件数据异常", e);
			throw new RuntimeException("读取excel文件数据异常");
		}
	}


	private RosExcelReadResultBO<T> readExcel(InputStream inputStream, int defaultMaxRownum) {
		// TODO Auto-generated method stub
		return null;
	}


	private void assertIsEXcel2003(String originalFilename) {
		if(!isExcel2003(originalFilename)) {
			throw new RuntimeException("文件格式有误，仅支持" + SUFFIX_XLS_2003 +"格式");
		}
	}
	
	public static boolean isExcel2003(String fileName) {
		return StringUtils.isBlank(fileName) ? false : fileName.endsWith(SUFFIX_XLS_2003);	
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

		} catch (RuntimeException e) {
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
		}catch (RuntimeException e) {
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
	private void excuteExportSheet(List<T> dataList, HSSFWorkbook workbook, String sheetName, int sheetIndex) {
		//创建sheet对象
		HSSFSheet  sheet  =  workbook.createSheet();
		workbook.setSheetName(sheetIndex, sheetName);
		
		//创建标题行
		List<Field> fieldList = createTitleRow(workbook,sheet);	
		if(CollectionUtils.isEmpty(dataList)) {
			return;
		}
		
		log.info("准备导出的数量为:{}",dataList.size());
		// 创建单元格样式
		Map<HorizontalAlignment, HSSFCellStyle> cellStyleMap = createCellStyle(workbook);
		HSSFCellStyle style;
		HSSFRow row; // 行
		HSSFCell cell; // 单元格
		int startNo  = 0;
		int endNo    =  dataList.size();
		// 写入各条记录，每条记录对应excel表中的一行
		
		for (int i = startNo; i < endNo ;i++) {
			row = sheet.createRow(i+1 - startNo);
			T entity = (T) dataList.get(i);//获取导出对象
			for(int j = 0; j < fieldList.size(); j++) {
				Field field = fieldList.get(j); // 获取field
				field.setAccessible(true); //设置实体类
				RosExcel annotation = field.getAnnotation(RosExcel.class);//获取field上的 RosExcel注解
				style = cellStyleMap.get(annotation.horizontal());
				cell =  row.createCell(this.getExcelCol(annotation.column())); //创建 cell
				cell.setCellType(CellType.STRING);
				cell.setCellStyle(style);
				if (annotation.isEXport()) {
					//根据RosExcel中设置情况决定是否导出字段的值
					cell.setCellValue(this.getFieldValue(entity,field));
				}
								
			}			
		}		
	}

    /**
     * 获取字段的值
     * @author Captkang
     * @date: 2020年9月12日下午11:56:46
     * @param entity
     * @param field
     * @return
     */
	private String getFieldValue(T entity, Field field) {

		try {
			Object obj = field.get(entity);
			if (null == obj) {
				return null;
			}

			String value;
			if (obj instanceof Date) {
				value = sdf.format((Date) obj);
			} else if (obj instanceof String) {
				value = String.valueOf(obj);
			} else {
				value = JSON.toJSONString(obj);
			}

			// TODO Auto-generated method stub
			return value;

		} catch (RuntimeException  e) {
			throw e;
		} catch (Exception e) {
			log.warn("获取字段值失败" , e);
			throw new RuntimeException("获取字段值失败");
		}

	}


	private Map<HorizontalAlignment, HSSFCellStyle> createCellStyle( HSSFWorkbook workbook) {
		Map<HorizontalAlignment, HSSFCellStyle> styleMap  = new HashMap<HorizontalAlignment, HSSFCellStyle>();
		for(HorizontalAlignment tempEnum : HorizontalAlignment.values()) {
			HSSFCellStyle style = createCommonCellStyle(workbook, tempEnum);
			styleMap.put(tempEnum, style);
		}
		return styleMap;
	}


	/**
	 * 创建标题行
	 * @author Captkang
	 * @date: 2020年9月12日下午10:10:18
	 * @param workbook
	 * @param sheet
	 * @return
	 */
	private List<Field> createTitleRow(HSSFWorkbook workbook, HSSFSheet sheet) {
		// 得到实体类所有通过注解映射了数据表的字段
		List<Field> fields = getAnnotatonField(clazz, null);
		HSSFRow titleRow = sheet.createRow(0);
		HSSFCell cell;
		HSSFCellStyle cellStyle;
		HSSFDataFormat format = workbook.createDataFormat();
		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setDataFormat(format.getFormat("@"));// 设置格式为文本格式
		// 写入个字段的列头名称
		for (int i = 0; i < fields.size(); i++) {
			Field field = fields.get(i);
			RosExcel annotation = field.getAnnotation(RosExcel.class); // 获取这个字段被注解的值
			// 设置单元格宽度
			sheet.setColumnWidth(i, annotation.width());
			// 设置单元格样式等
			int col = getExcelCol(annotation.column());// 获得第几列
			cell = titleRow.createCell(col);// 第一行 第col列 的一个单元格
			cell.setCellType(CellType.STRING); // 设置列(单元格)中写入内容为String类型
			cell.setCellValue(annotation.name());// 写入列名

			// 如果设置了提示信息则鼠标放上去提示
			if (!annotation.prompt().trim().equals("")) {
				setHSSFPrompt(sheet, "", annotation.prompt(), 1, 10000, col, col); // 这里默认设置了 2-100001
			}

			// 如果设置了combo 属性，则本列只能选择不能输入
			if (annotation.combo().length > 0) {
				setHSSFValidation(sheet, annotation.combo(), 1, 10000, col, col); // 这里默认设置了 2-100001
			}

			cellStyle = createTitleCellStyle(workbook, annotation);
			cell.setCellStyle(cellStyle);

			// 设置列格式为文本格式
			boolean isText = annotation.isText();
			if (isText) {
				sheet.setDefaultColumnStyle(col, style1);
			}

		}

		return fields;
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

    
	private HSSFCellStyle createCommonCellStyle(HSSFWorkbook workbook, HorizontalAlignment horizontalAlignment) {
		// 创建样式
		HSSFCellStyle commonStyle = workbook.createCellStyle();

		/**
		 * 
		 * 设置边框
		 * 
		 */
		// 设置底边框
		commonStyle.setBorderBottom(BorderStyle.THIN);
		// 设置底边框颜色
		commonStyle.setBottomBorderColor(HSSFColorPredefined.BLACK.getIndex());

		// 设置左边框
		commonStyle.setBorderLeft(BorderStyle.THIN);
		// 设置左边框颜色
		commonStyle.setLeftBorderColor(HSSFColorPredefined.BLACK.getIndex());

		// 设置右边框
		commonStyle.setBorderRight(BorderStyle.THIN);
		// 设置右边框颜色
		commonStyle.setRightBorderColor(HSSFColorPredefined.BLACK.getIndex());

		// 设置顶边框
		commonStyle.setBorderTop(BorderStyle.THIN);
		commonStyle.setTopBorderColor(HSSFColorPredefined.BLACK.getIndex());

		/**
		 * 
		 * 设置字体
		 * 
		 */
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBold(false);
		// 设置名字
		font.setFontName("Arial");
		//
		commonStyle.setFont(font);

		// 设置自动换行
		commonStyle.setWrapText(false);
		// 设置水平对齐的样式为居中对齐
		commonStyle.setAlignment(horizontalAlignment);
		// 设置垂直对齐的样式为居中对齐
		commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// 防止科学计数
		HSSFDataFormat format = workbook.createDataFormat();
		commonStyle.setDataFormat(format.getFormat("@"));

		return commonStyle;
	}

}