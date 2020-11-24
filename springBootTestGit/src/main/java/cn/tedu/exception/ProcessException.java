package cn.tedu.exception;


/**
 * 后台接口调用异常类
 * @author Captkang
 * @date: 2020年11月23日下午10:27:36
 * 
 */
public class ProcessException extends RuntimeException {


	private static final long serialVersionUID = -1368395466839905675L;

	private String errCode;

	public String getErrCode() {
		return errCode;
	}

	public ProcessException(String errMsg, Throwable cause) {
		super(errMsg, cause);
	}

	public ProcessException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
	}

	public ProcessException(String errCode, String errMsg, Throwable cause) {
		super(errMsg, cause);
		this.errCode = errCode;
	}

	public String getErrorCode() {

		return errCode;
	}

}