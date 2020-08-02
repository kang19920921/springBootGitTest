package cn.tedu.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalenderUtil {

	public static void main(String[] args) {
		System.out.println(getDayAfterMonths("2019/04/01", "yyyy/MM/dd", -12));
		System.out.println(getLastDayOfMonth("2019/02/01"));
	}
	
	/**
	 * 获取对应月份后的日期  yyyy/MM/dd
	 * @author Captkang
	 * @param date
	 * @param formate
	 * @return
	 */
	public static String getDayAfterMonths(String date,String formate,int mon) {

		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(date));
		cal.add(Calendar.MONTH, mon);
		return sdf.format(cal.getTime());
		
	}
	
	/**
	 * 获取对应MINUTE 后的时间
	 * @author Captkang
	 * @param date
	 * @param formate
	 * @return
	 */
	public static String getDayTimeAfterMINUTE(String date,String formate,int MINUTE) {

		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(date));
		cal.add(Calendar.MINUTE, MINUTE);
		return sdf.format(cal.getTime());
		
	}
	
	/**
	 * 取得date对应月份的最后一天
	 * @param date
	 * @return
	 */
	public static String  getLastDayOfMonth(String date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat(Constant.DEFAULT_DATEFORMATE);
		Calendar cal  = Calendar.getInstance();
		cal.setTime(new Date(date));
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
	
		return sdf.format(cal.getTime());		
	}
	
	/**
	 * 返回两个日期之间相差多少月
	 * @param strD1
	 * @param strD2
	 * @param format
	 * @return
	 */
	public static int  getDateDiffInMonth(String strD1,String strD2 ,String format) {
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		Date date1 =new Date();
		Date date2 =new Date();
		int diff = (date1.getYear()-date2.getYear())*12 + date1.getMonth() - date2.getMonth();
		
		if (date1.after(date2)&&(date1.getDate()>date2.getDate())) {
			diff++;
		}
		if (date1.before(date2)&&(date1.getDate()<date2.getDate())) {
			diff--;
		}
				
		return 0;
		
	}
}
