package com.frankmoley.lil.fid.config;

import org.springframework.context.annotation.*;

@Configuration
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:log4j.properties")
})
@ComponentScan(basePackages = "com.frankmoley.lil.fid")
@EnableAspectJAutoProxy
public class ApplicationConfig {


}
