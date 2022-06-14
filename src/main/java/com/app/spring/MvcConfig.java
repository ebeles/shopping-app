package com.app.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc

public class MvcConfig implements WebMvcConfigurer {
    public MvcConfig() {
        super();
    }

    @Override
    public void addViewControllers(final org.springframework.web.servlet.config.annotation.ViewControllerRegistry registry) {
        registry.addViewController("/addProduct.html");
        registry.addViewController("/index.html");
    }

    @Override
    public void addResourceHandlers(final org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry registry) { //tell us from where we take the resource
        registry.addResourceHandler( "/**",
                "/images/**",
                "/css/**",
                "/resources/**",
                "/js/**",
                "/api/**",
                "/font-awesome/**"
//        ).resourceChain(true)
//                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
        ).addResourceLocations( // where exactly is the location give the class path
                "classpath:/static/images/",
                "classpath:/static/css/",
                "classpath:/static/js/",
                "classpath:/static/api/",
                "classpath:/resources/",
                "classpath:/static/font-awesome/"
        );
    }

//    @Override
//    public void configureDefaultServletHandling(final org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
//
//    @Override
//    public void addInterceptors(final org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
//          final LocaleChangeInterceptor localeChangeInterceptor =
//                  new LocaleChangeInterceptor();
//            registry.addInterceptor(localeChangeInterceptor);
//    }
}



