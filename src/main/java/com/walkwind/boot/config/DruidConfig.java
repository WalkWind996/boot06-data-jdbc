package com.walkwind.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author walkwind
 * @Description
 * @Date 2020-4-18-17:20
 **/
@Configuration
public class DruidConfig {

    //将druid中的属性绑定绑定到spring.datasource
    @ConfigurationProperties("spring.datasource")
    @Bean
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    /**
     * 配置druid连接池的监控
     * 1.配置druid后台管理的Servlet
     * 2.配置web监控的filter
     * */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> statViewServlet = new ServletRegistrationBean<>(new
                 StatViewServlet(),"/druid/*");
        Map<String,String> initParams = new HashMap<>();
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        initParams.put("allow","");//默认就是允许所有用户登陆
        initParams.put("deny","");//拒绝指定用户访问
        statViewServlet.setInitParameters(initParams);
        return statViewServlet;
    }

    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean webStatFilter = new FilterRegistrationBean();
        webStatFilter.setFilter( new WebStatFilter());
        Map<String,String> initParams = new HashMap<>();
        //不拦截的请求
        initParams.put("exclusions","*.js,*.css,/druid/*");
        webStatFilter.setInitParameters(initParams);
        webStatFilter.setUrlPatterns(Arrays.asList("/*"));
        return webStatFilter;
    }
}
