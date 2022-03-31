package cn.tedu.config;


import cn.tedu.config.filter.OtLogbackFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Autowired
    private OtLogbackFilter otLogbackFilter;

    /**
     * 添加日志唯一id
     * @return
     */
    public FilterRegistrationBean<OtLogbackFilter> buildLogbackFilter() {
        FilterRegistrationBean<OtLogbackFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setOrder(1);
        registrationBean.setFilter(otLogbackFilter);
        registrationBean.setName("otLogbackFilter");


        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

}
