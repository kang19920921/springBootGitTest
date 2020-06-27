package cn.tedu.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	
	@Bean
	public Queue getQueue() {
			
		Queue q = new Queue("testQueue",true);//是否需要持久化 
				
		return q;		
	}
	
	
	@Bean
	public Queue getQueue1() {
			
		Queue q = new Queue("testQueue1",true);//是否需要持久化 
				
		return q;		
	}
	
	
	

}
