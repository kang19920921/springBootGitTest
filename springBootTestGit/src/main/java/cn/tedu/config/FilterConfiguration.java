package cn.tedu.config;


import cn.tedu.config.filter.OTEncryptFilter;
import cn.tedu.config.filter.OTExceptionFilter;
import cn.tedu.config.filter.OtLogbackFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Autowired
    private OTExceptionFilter exceptionFilter;

    @Autowired
    private OtLogbackFilter logbackFilter;

    @Autowired
    private OTEncryptFilter encryptFilter;



    /**
     * 添加日志唯一id
     * @return
     */

    @Bean
    public FilterRegistrationBean<OTExceptionFilter> buildExceptionFilter() {
        FilterRegistrationBean<OTExceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setOrder(1);
        registrationBean.setFilter(exceptionFilter);
        registrationBean.setName("exceptionFilter");
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    /**
     * 添加日志唯一id
     * @return
     */

    @Bean
    public FilterRegistrationBean<OtLogbackFilter> buildLogbackFilter() {
        FilterRegistrationBean<OtLogbackFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setOrder(2);
        registrationBean.setFilter(logbackFilter);
        registrationBean.setName("logbackFilter");
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    /**
     * 添加日志唯一id
     * @return
     */
    @Bean
    public FilterRegistrationBean<OTEncryptFilter> buildEncryptFilter() {
        FilterRegistrationBean<OTEncryptFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setOrder(3);
        registrationBean.setFilter(encryptFilter);
        registrationBean.setName("encryptFilter");
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
