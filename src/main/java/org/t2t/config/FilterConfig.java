package org.t2t.config;


import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    // 필터 등록 (bean으로 리턴)
    @Bean
    public FilterRegistrationBean<Filter> logFilter() {
        FilterRegistrationBean<Filter> filterBean = new FilterRegistrationBean<>();

        filterBean.setFilter(new IncludingUrlFilter());
        filterBean.addUrlPatterns("/*"); // 필터 적용할 URL 패턴 지정 (*: 바로 하위 경로 / **: 그 하위 경로 전체)
        filterBean.setOrder(1); // 필터 순서
        return filterBean;
    }
}
