package com.devglan.customerservice.feign.config;

import feign.auth.BasicAuthRequestInterceptor;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;

public class CustomFeignConfig {

    // We done need this, this causes error, you can try
    // and solution s here : https://github.com/spring-cloud/spring-cloud-netflix/issues/805
//    @Bean
//    public Contract feignContract() {
//        return new feign.Contract.Default();
//    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "password");
    }

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

}
