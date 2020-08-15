package cn.tedu.pojo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class EarlyWarningRuleDO extends BaseObject {

	private String idEarlyWarningRule;

	private String insuranceCompany;

	private String idCompanyInfoDept2;

	private String operatorCompanyId;

	private String ruleType;

	private BigDecimal hours;

	private Integer priority;

	private String isEffective;

	private String idDeleted;

}
