package com.bon.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 类描述信息
 * com.bon.common
 *
 * @author pengwen
 * @create 2017/8/10 0010
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    // http://localhost:8080/swagger-ui.html
    // Swagger2默认将所有的Controller中的RequestMapping方法都会暴露，
    // 然而在实际开发中，我们并不一定需要把所有API都提现在文档中查看，这种情况下，使用注解
    // @ApiIgnore来解决，如果应用在Controller范围上，则当前Controller中的所有方法都会被忽略，
    // 如果应用在方法上，则对应用的方法忽略暴露API
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.bon"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("微信公众平台管理")
                .description("微信公众平台管理后台")
                .termsOfServiceUrl("http://bon")
                .contact("Bon")
                .version("1.0")
                .build();
    }

}
