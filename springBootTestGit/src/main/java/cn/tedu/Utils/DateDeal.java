package cn.tedu.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDeal {
	
	public static void main(String[] args) {
//		Date date = new Date();
//		System.out.println(date);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//		String strDate       = sdf.format(date).substring(0, 7)+"/01";
//		System.out.println(strDate);
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date(strDate));
//		cal.add(Calendar.MONTH, -1);
//		System.out.println(sdf.format(cal.getTime()));
//		String lastMonth = sdf.format(cal.getTime()); 
//		cal.setTime(new Date(lastMonth));
//		cal.add(Calendar.MONTH, -12);
//		System.out.println(sdf.format(cal.getTime()));
						
		Date date = new Date();
		System.out.println(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String strDate       = sdf.format(date).substring(0, 7)+"/01";
		System.out.println(strDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(strDate));

		cal.add(Calendar.MONTH, -12);
		System.out.println(sdf.format(cal.getTime()));
				
	}
	

}
