package cn.tedu.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncImportServiceImpl  implements AsyncImportService{
	
	
	
    @Async("taskExecutor")
	@Override
	public void importTest(String str) {
		for (int i = 0;  i< 100 ; i++) {
			try {
				System.out.println(i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
