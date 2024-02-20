package cn.tedu.config.consumer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class DispatchWorksConsumer {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 0l,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());


    /**
     * 执行消费任务
     */
    public void excute() {
        for (int i = 0; i < 8; i++) {
            executor.execute(new DispatchWorksThread());
        }
    }

    private class DispatchWorksThread implements Runnable {

        @Override
        public void run() {
            log.info("启动消费者线程啦+++++++");
            for (int i = 0; i < 1000; i++) {
                //
                log.info("派工一次");
                try {
                    Thread.sleep(new Random().nextInt(500000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    continue;
                }
            }
            executor.execute(new DispatchWorksThread()); //生产环境频繁full jc ，看看是否可以避免

        }
    }


}
