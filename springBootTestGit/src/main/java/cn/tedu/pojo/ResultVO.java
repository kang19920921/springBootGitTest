package cn.tedu.pojo;

import lombok.Getter;
import lombok.Setter;

import java.beans.Transient;

import org.apache.commons.lang3.StringUtils;
import cn.tedu.exception.ProcessException;
import cn.tedu.enums.ProcessCodeEnum;


@Getter
@Setter
public class ResultVO<T>  extends BaseObject {

	private static final long serialVersionUID = -1052682414144162466L;
	
	public String resultCode = null;
	public String resultMsg  = null;
	public T resultObject = null;

	public ResultVO() {
		// TODO Auto-generated constructor stub
	}

	
	public ResultVO(String resultCode, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}
	
    @Transient  //不需要序列化
	public boolean isSuccess() {
		if(ProcessCodeEnum.SUCCESS.equalsCode(this.resultCode)){
			return true;
		}
        return false;		
	}
	
	/**
	 * 校验是否成功
	 * @author Captkang
	 * @date: 2020年11月24日下午10:13:31
	 */
	public void isValidSucess() {
		if(isSuccess()) {
			return ;
		}
		if(StringUtils.isBlank(resultCode)) {
			throw ProcessCodeEnum.UNKNOWN.buildException();
		}
		throw new ProcessException(resultCode, resultMsg);	
	}
}