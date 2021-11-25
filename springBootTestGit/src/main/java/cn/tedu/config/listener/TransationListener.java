package cn.tedu.config.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import cn.tedu.event.AssignmentEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 事务监听器
 * 
 * @author Captkang
 * @date: 2021年11月3日下午10:30:03
 */

@Slf4j
@Component
public class TransationListener {

   
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	private void onAssignmentEvent(AssignmentEvent assignmentEvent) {
		Object idRepairOutsideInfo = assignmentEvent.getSource();
		try {
			log.info("进入了事务监听器");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	
	
}
