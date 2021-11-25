package cn.tedu.event;

import org.springframework.context.ApplicationEvent;


/**
 * 派工事件
 * @author Captkang
 * @date: 2021年11月1日下午10:37:17
 */
public class AssignmentEvent extends ApplicationEvent {

	private String messageId;

	public AssignmentEvent(Object source, String messageId) {
		super(source);
		this.messageId = messageId;
	}

}
