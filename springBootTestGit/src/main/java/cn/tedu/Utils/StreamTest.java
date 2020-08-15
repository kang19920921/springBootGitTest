package cn.tedu.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.tedu.pojo.EarlyWarningRuleDO;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamTest {

	public static void main(String[] args) {
		List<EarlyWarningRuleDO> allPriorityList = getEarlyWarningRuleList();
		log.info("oringinal" + allPriorityList.toString());
		
		List<EarlyWarningRuleDO> lowPriorityList = allPriorityList.stream().filter(earlyWarningRuleDO -> 2 == earlyWarningRuleDO.getPriority()).collect(Collectors.toList());
		log.info("lowPriorityList" + lowPriorityList.toString());
		
		Map<String,List<EarlyWarningRuleDO>> maplowPriorityList = lowPriorityList.stream().collect(Collectors.groupingBy(EarlyWarningRuleDO::getOperatorCompanyId));
		log.info("maplowPriorityList" + maplowPriorityList.toString());
		
		
		Map<String,EarlyWarningRuleDO > maplowPriorityEarlyWarningRuleDO = lowPriorityList.stream().collect(Collectors.toMap(EarlyWarningRuleDO::getRuleType, earlyWarningRuleDO ->earlyWarningRuleDO));
		log.info("maplowPriorityEarlyWarningRuleDO" + maplowPriorityEarlyWarningRuleDO.toString());

	}

	private static List<EarlyWarningRuleDO> getEarlyWarningRuleList() {
		List<EarlyWarningRuleDO> list = new ArrayList<EarlyWarningRuleDO>();
		
		EarlyWarningRuleDO earlyWarningRuleDO03 = new EarlyWarningRuleDO();
		earlyWarningRuleDO03.setIdEarlyWarningRule("VDD12345566978ASDF");
		earlyWarningRuleDO03.setIdCompanyInfoDept2("dept2VDD12345566978ASDF");
		earlyWarningRuleDO03.setInsuranceCompany("1001");
		earlyWarningRuleDO03.setOperatorCompanyId("wxgwew232534646");
		earlyWarningRuleDO03.setPriority(1);
		earlyWarningRuleDO03.setRuleType("1");
		earlyWarningRuleDO03.setHours(new BigDecimal(300));
		earlyWarningRuleDO03.setIsEffective("1");
		earlyWarningRuleDO03.setIdDeleted("0");
		list.add(earlyWarningRuleDO03);
		
		EarlyWarningRuleDO earlyWarningRuleDO02 = new EarlyWarningRuleDO();
		earlyWarningRuleDO02.setIdEarlyWarningRule("PDD12345566978ASDF");
		earlyWarningRuleDO02.setIdCompanyInfoDept2("dept2PDD12345566978ASDF");
		earlyWarningRuleDO02.setInsuranceCompany("1001");
		earlyWarningRuleDO02.setOperatorCompanyId("wxgs1232534646");
		earlyWarningRuleDO02.setPriority(2);
		earlyWarningRuleDO02.setRuleType("1");
		earlyWarningRuleDO02.setHours(new BigDecimal(1000));
		earlyWarningRuleDO02.setIsEffective("1");
		earlyWarningRuleDO02.setIdDeleted("0");
		list.add(earlyWarningRuleDO02);

		EarlyWarningRuleDO earlyWarningRuleDO01 = new EarlyWarningRuleDO();
		earlyWarningRuleDO01.setIdEarlyWarningRule("VDD987654321ASDF");
		earlyWarningRuleDO01.setIdCompanyInfoDept2("dept2VDD987654311ASDF");
		earlyWarningRuleDO01.setInsuranceCompany("1001");
		earlyWarningRuleDO01.setOperatorCompanyId("wxgs987654321");
		earlyWarningRuleDO01.setPriority(2);
		earlyWarningRuleDO01.setRuleType("2");
		earlyWarningRuleDO01.setHours(new BigDecimal(999));
		earlyWarningRuleDO01.setIsEffective("1");
		earlyWarningRuleDO01.setIdDeleted("0");
		list.add(earlyWarningRuleDO01);
		
		return list;
	}

}
