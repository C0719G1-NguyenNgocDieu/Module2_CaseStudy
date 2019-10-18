package com.codegym;

import com.codegym.service.Impl.ProductServiceImpl;
import com.codegym.service.ProductService;
import com.codegym.service.UserDetailsServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CaseStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaseStudyApplication.class, args);
    }

    @Bean
	public ProductService productService(){
    	return new ProductServiceImpl();
	}

	@Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }


    @Configuration
    class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

        private ApplicationContext appContext;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            appContext = applicationContext;
        }

//        @Override
//        public void addFormatters(FormatterRegistry registry) {
//            LocationService locationService = appContext.getBean(LocationService.class);
//            Formatter locationFormatter = new LocationFormatter(locationService);
//            registry.addFormatter(locationFormatter);
//        }

//        @Override
//        public void addInterceptors(InterceptorRegistry registry) {
//            LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
//            interceptor.setParamName("lang");
//            registry.addInterceptor(interceptor);
//        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {


            // Image resource.
            registry.addResourceHandler("/image/**").addResourceLocations("file:/home/dieunguyen/Downloads/");

        }
}}
