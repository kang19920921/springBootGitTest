package cn.tedu.config.listener;

import cn.tedu.config.consumer.DispatchWorksConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class AiAsRosListener implements ApplicationRunner {

	@Autowired
	private DispatchWorksConsumer dispatchWorksConsumer;

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		dispatchWorksConsumer.excute();
	}

}
