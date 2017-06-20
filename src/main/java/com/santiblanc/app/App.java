package com.santiblanc.app;

import com.santiblanc.app.converter.MessageConverter;
import com.santiblanc.app.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    AuthFilter authFilter;

    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter((Filter) authFilter);
        registration.addUrlPatterns("/api/*");
        return registration;
    }

    @Bean(name = "messageConverter")
    public MessageConverter getMessageConverter(){
        return new MessageConverter();
    }

    @Bean(name = "userConverter")
    public UserConverter getUserConverter(){
        return new UserConverter();
    }
}
