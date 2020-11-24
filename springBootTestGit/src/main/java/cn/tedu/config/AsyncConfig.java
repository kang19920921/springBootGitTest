package cn.tedu.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Configuration
@EnableAsync
@Slf4j
@Getter
@Setter
public class AsyncConfig implements AsyncConfigurer{
	
	// 核心线程数
	@Value("${async.corePoolSize:10}")
	private int corePoolSize;
	
	//最大线程数
	@Value("${async.maxPoolSize:100}")
	private int maxPoolSize;
	
	// 阻塞队列长度
	@Value("${async.queueCapacity:10}")
	private int queueCapacity;
	
	

	

	@Bean("taskExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor  executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		// 发生拒绝时，重试添加当前的任务,他会自动重复调用 excute()方法，直到成功。
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setThreadNamePrefix("SpringAsyncThread-");
		executor.initialize();
		
		return executor;
	}



	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return null;
	}


}
