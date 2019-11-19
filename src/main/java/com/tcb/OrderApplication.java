package com.tcb;

import com.tcb.config.SystemConfig;
import com.tcb.pojo.UserPojo;
import com.tcb.service.IUserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import javax.annotation.Resource;

@SpringBootApplication
@MapperScan("com.tcb.dao")
public class OrderApplication extends SpringBootServletInitializer {

    @Resource
    private IUserService userService;

    public static void main(String[] args) {
//		SpringApplication.run(OrderApplication.class, args);
        SpringApplication springApplication = new SpringApplication(OrderApplication.class);
        springApplication.addListeners(new SystemConfig());
        springApplication.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(OrderApplication.class);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.setSessionTimeout(3600);//单位为S
            }
        };
    }

}
