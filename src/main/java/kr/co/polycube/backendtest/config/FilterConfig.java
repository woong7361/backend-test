package kr.co.polycube.backendtest.config;

import kr.co.polycube.backendtest.filter.DefaultFilterAdvice;
import kr.co.polycube.backendtest.filter.InvalidUrIFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilterConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<InvalidUrIFilter> InvalidUrIFilter() {
        FilterRegistrationBean<InvalidUrIFilter> registrationBean = new FilterRegistrationBean<>(new InvalidUrIFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<DefaultFilterAdvice> filterBean() {
        FilterRegistrationBean<DefaultFilterAdvice> registrationBean = new FilterRegistrationBean<>(new DefaultFilterAdvice());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
