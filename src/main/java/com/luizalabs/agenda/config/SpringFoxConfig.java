package com.luizalabs.agenda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
	
	 @Value("${developer.name}")
	  private String name;

	  @Value("${developer.email}")
	  private String email;

	  @Value("${title.swagger}")
	  private String title;

	  @Value("${description.swagger}")
	  private String description;

	  @Value("${license.swagger}")
	  private String license;

	  @Value("${license.url.swagger}")
	  private String licenseUrl;

	  @Value("${version.software}")
	  private String version;

	
	@Bean
	public Docket swagger() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.luizalabs.agenda.controller"))
				.paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder().contact(new Contact(name,null, email)).description(description)
	        .license(license).licenseUrl(licenseUrl).version(version).title(title).build();
	  }

}

