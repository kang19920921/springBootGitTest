package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import cn.tedu.output.CompanyInfoOutputDTO;

public class test {

	public static void main(String[] args) {
		List<CompanyInfoOutputDTO> list = new ArrayList<CompanyInfoOutputDTO>();

		CompanyInfoOutputDTO companyInfoOutputDTO = new CompanyInfoOutputDTO();
		companyInfoOutputDTO.setCompanyName("平安保险");
		companyInfoOutputDTO.setInsurance("1001");
		companyInfoOutputDTO.setRank(5);
		list.add(companyInfoOutputDTO);
		

		CompanyInfoOutputDTO companyInfoOutputDTO2 = new CompanyInfoOutputDTO();
		companyInfoOutputDTO2.setCompanyName("昆仑山保险");
		companyInfoOutputDTO2.setInsurance("1003");
		companyInfoOutputDTO2.setRank(25);
		list.add(companyInfoOutputDTO2);

		CompanyInfoOutputDTO companyInfoOutputDTO1 = new CompanyInfoOutputDTO();
		companyInfoOutputDTO1.setCompanyName("天安保险");
		companyInfoOutputDTO1.setInsurance("1002");
		companyInfoOutputDTO1.setRank(25);
		list.add(companyInfoOutputDTO1);

		
		
//		Collections.shuffle(list, new Random());
		Collections.sort(list);
//		Collections.sort(list, new Comparator<CompanyInfoOutputDTO>() {
//
//			final Collator  pinYinComparator  = Collator.getInstance(Locale.CHINA);
//			
//
//
//			@Override
//			public int compare(CompanyInfoOutputDTO o1, CompanyInfoOutputDTO o2) {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//			
//			
//			
//		});
		
		
		for (CompanyInfoOutputDTO cc : list) {
			System.out.println(cc);
		}
		
		

	}

}
