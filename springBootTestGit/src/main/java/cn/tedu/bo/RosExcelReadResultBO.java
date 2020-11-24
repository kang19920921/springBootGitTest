package cn.tedu.bo;

import java.util.ArrayList;
import java.util.List;

import cn.tedu.pojo.BaseObject;

/**
 * RosExcelUtil工具类读取excel文件结果
 * @author Captkang
 * @date: 2020年11月22日下午4:06:11
 */
public class RosExcelReadResultBO<T> extends BaseObject {

	private static final long serialVersionUID = -7824921363132949327L;

	public static final int Fail_info_max_nums = 50;

	private List<T> dataList;

	private List<RosExcelReadFailInfoBO> failInfoBOList;

	public RosExcelReadResultBO() {
		this.dataList = new ArrayList<>();
		this.failInfoBOList = new ArrayList<>();
	}

	/**
	 * 添加业务数据
	 * 
	 * @author Captkang
	 * @date: 2020年11月22日下午4:27:52
	 * @param entity
	 */
	public void addData(T entity) {
		if (entity != null) {
			dataList.add(entity);
		}
	}

	/**
	 * 添加失败信息
	 * 
	 * @author Captkang
	 * @date: 2020年11月22日下午4:29:09
	 * @param i
	 * @param failReason
	 */
	public void addFailInfo(int i, String failReason) {
		int lineNo = i + 1;// excel 行数从0开始
		RosExcelReadFailInfoBO failInfoBO = new RosExcelReadFailInfoBO(lineNo, failReason);
		failInfoBOList.add(failInfoBO);
	}

}