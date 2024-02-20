package cn.tedu.config.listener;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * springBoot 启动时监听
 */
@Component
public class AiasRosAsyncStartAppListener  implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("11111111111111111111");
        System.out.println("22222222222222222222");
        System.out.println("33333333333333333333");
        System.out.println("44444444444444444444");
        System.out.println("555555555555555555555");
    }

}
