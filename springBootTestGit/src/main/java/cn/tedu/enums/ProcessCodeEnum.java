package cn.tedu.enums;

import cn.tedu.exception.ProcessException;
import cn.tedu.pojo.ResultVO;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ProcessCodeEnum {

	SUCCESS("0000", "成功"), FAIL("9999", "失败"), UNKNOWN("1111", "未知"), PROCESS_ERR("P999",
			"业务处理异常"), INVALID_TOKEN("11100", "token已经失效,请重新获取"),

	// 抛出异常时，GlobalExceptionAdvice中打印Error级别日志(新增的枚举请添加到warmLogEnumList中)
	PARM_WARM("A999", "参数异常"),

	// 抛出异常时，GlobalExceptionAdvice中不需要打印日志(新增的枚举请添加到noLogEnumList中)
	CONFIRM("0001", "确认框提出");

	/** 抛出异常时，GlobalExceptionAdvice中打印warm级别日志 ***/
	public static final List<ProcessCodeEnum> warnLogEnumList;

	public static final List<ProcessCodeEnum> noLogEnumList;

	static {
		warnLogEnumList = new ArrayList<>();
		warnLogEnumList.add(PARM_WARM);

		noLogEnumList = new ArrayList<>();
		noLogEnumList.add(CONFIRM);
	}

	public static boolean warmLogEnumListContains(String code) {
		return contains(warnLogEnumList, code);
	}

	public static boolean noLogEnumListContains(String code) {
		return contains(noLogEnumList, code);
	}

	private static boolean contains(List<ProcessCodeEnum> enumList, String code) {
		for (ProcessCodeEnum tempEnum : enumList) {
			if (tempEnum.equalsCode(code)) {
				return true;
			}
		}
		return false;
	}

	// 构造处理异常信息
	public ProcessException buildException() {
		return new ProcessException(code, message);
	}

	public ProcessException buildException(String message) {
		return new ProcessException(code, message);
	}

	public ProcessException buildException(String mesage, Throwable e) {
		return new ProcessException(code, message, e);
	}

	public boolean equalsCode(String code) {
		return this.code.equals(code);
	}

	/***
	 * 构造成功的返回结果
	 * 
	 * @author Captkang
	 * @date: 2020年11月28日下午12:59:26
	 * @param resultMsgs
	 * @return
	 */
	public final <T> ResultVO<T> buildSuccessResultVO(T... resultMObjs) {
		try {
			ResultVO<T> resultVO = new ResultVO<T>(code, message);
			T resultObj = ArrayUtils.isNotEmpty(resultMObjs) ? resultMObjs[0] : null;
			resultVO.setResultObject(resultObj);
			return resultVO;
		} catch (Exception e) {
			throw PROCESS_ERR.buildException("构造成功的返回结果异常", e);
		}
	}

	/**
	 * 构造成功的返回结果
	 * 
	 * @author Captkang
	 * @date: 2020年11月24日下午10:24:58
	 * @param resultMsgs
	 * @return
	 */
	public final <T> ResultVO<T> buildSuccessVOWWithMsg(String... resultMsgs) {
		try {
			String resultMsg = ArrayUtils.isNotEmpty(resultMsgs) ? resultMsgs[0] : null;
			return new ResultVO<T>(code, resultMsg);
		} catch (Exception e) {
			throw PROCESS_ERR.buildException("构造成功的返回结果异常", e);
		}
	}

	/**
	 * 构造失败的返回结果
	 * 
	 * @author Captkang
	 * @date: 2020年11月28日上午9:52:01
	 * @param resultMsgs
	 * @return
	 */
	public final <T> ResultVO<T> buildFailResultVO(String... resultMsgs) {
		try {
			String resultMsg = ArrayUtils.isNotEmpty(resultMsgs) ? resultMsgs[0] : null;
			if (StringUtils.isEmpty(resultMsg)) {
				resultMsg = message;
			}
			ResultVO<T> resultVO = new ResultVO<>(code, resultMsg);
			return resultVO;
		} catch (Exception e) {
			throw PROCESS_ERR.buildException("构造失败的返回结果异常", e);
		}
	}

	// 应答码
	private String code;
	// 应答内容
	private String message;

	private ProcessCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
}