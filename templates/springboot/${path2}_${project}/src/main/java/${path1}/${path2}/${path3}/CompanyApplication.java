package com.ihrm.${ClassName};

import com.ihrm.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

//1.配置springboot的包扫描
@SpringBootApplication(scanBasePackages = "com.ihrm")
//2.配置jpa注解的扫描
@EntityScan(value="com.ihrm.domain.${ClassName}")
//注册到Eureka
@EnableEurekaClient
public class ${ClassName}Application {

    /**
     * 启动方法
     */
    public static void main(String[] args) {
        SpringApplication.run(${ClassName}Application.class,args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
