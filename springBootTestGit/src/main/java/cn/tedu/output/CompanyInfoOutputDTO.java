package cn.tedu.output;

import java.text.Collator;
import java.util.Locale;
import cn.tedu.pojo.BaseObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyInfoOutputDTO  extends BaseObject  implements Comparable<CompanyInfoOutputDTO> {

	private static final long serialVersionUID = 688275042429545007L;

	public String companyName;

	public String insurance;

	public Integer rank;
	
		
//	@Override //升序
//	public int compareTo(CompanyInfoOutputDTO o) {
//		// TODO Auto-generated method stub
//		return this.rank.compareTo(o.rank);
//	}
		
	@Override //降序
	public int compareTo(CompanyInfoOutputDTO o) {
		final Collator  pinYinComparator  = Collator.getInstance(Locale.CHINA);
         int num = o.rank.compareTo(this.rank);
         if(num == 0) {
        	 return pinYinComparator.compare(this.companyName,o.companyName);
         }

		
		return num;
	}
}