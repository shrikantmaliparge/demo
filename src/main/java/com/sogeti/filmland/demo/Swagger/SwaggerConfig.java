package com.sogeti.filmland.demo.Swagger;

import com.sogeti.filmland.demo.Constant.ConstantValue;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket produceApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.sogeti.filmland.demo.model")).paths(paths()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("FilmLand Rest APIs")
				.description("Lists all the rest apis for Filmland App.").version("0.0.1-SNAPSHOT").build();
	}

	private Predicate<String> paths() {
		return Predicates.and(PathSelectors.regex(ConstantValue.MAPPING_URI + ".*"),
				Predicates.not(PathSelectors.regex("/error.*")));
	}

}
