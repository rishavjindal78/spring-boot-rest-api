package com.demo.blogging.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class BloggingSwaggerConfiguration {
	
	  @Bean
	    public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption, @Value("${application-version}") String appVersion) {
	        return new OpenAPI()
	                .info(new Info()
	                                .title("Blogging application Service API")
	                                .version(appVersion)
	                                .description(appDesciption)
	                );
	    }

}
