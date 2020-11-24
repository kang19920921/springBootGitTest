package cn.tedu.bo;

import cn.tedu.pojo.BaseObject;

public class RosExcelReadFailInfoBO extends BaseObject {

	private static final long serialVersionUID = -4397787271471661968L;

	public RosExcelReadFailInfoBO() {
		// TODO Auto-generated constructor stub
	}
	
	/** 第几行 **/
	private int lineNo;
	
	/** 失败原因 **/ 
	private String failReason;

	public RosExcelReadFailInfoBO(int lineNo, String failReason) {
		super();
		this.lineNo = lineNo;
		this.failReason = failReason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((failReason == null) ? 0 : failReason.hashCode());
		result = prime * result + lineNo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RosExcelReadFailInfoBO other = (RosExcelReadFailInfoBO) obj;
		if (failReason == null) {
			if (other.failReason != null)
				return false;
		} else if (!failReason.equals(other.failReason))
			return false;
		if (lineNo != other.lineNo)
			return false;
		return true;
	}
	
}