package cn.tedu.enums;

import org.springframework.context.ApplicationEvent;

import cn.tedu.event.AssignmentEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum MessageTypeEnum {

	ASSIGNMENT_TASK("1", "订单任务消息"),
	OVERTIME_TASK("2", "订单超时任务消息"),
	FEEDBACK_TASK("3", "订单反馈任务消息");

	private String code;
	private String desc;

	private MessageTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	
	public ApplicationEvent createEvent(Object source, String messageId) {
		log.info("根据消息类型枚举创建监听事件对象 source={}, messageId={}", source, source);
		
		if(this.equals(ASSIGNMENT_TASK)) {
			return new AssignmentEvent(source, messageId);
		}
		

		return null;

	}

}
