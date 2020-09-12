package cn.tedu.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RosExcelBaseDTO extends BaseObject {

	private static final long serialVersionUID = 7837938538901402594L;

	/** excel文件中第几行 */
	private int lineNo;

}
