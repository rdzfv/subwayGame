package com.xyy.subway.game2d;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


//@Configuration
//@EnableSwagger2
//@EnableWebMvc
//public class SwaggerConfig implements WebMvcConfigurer {
//
//    @Value("${swagger.show}")
//    private boolean swaggerShow;
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("smartClassroom")
////                .description("红包加息券拆分")
//                .termsOfServiceUrl("https://localhost:8088/")
//                .version("1.0.0")
//                .build();
//    }
//
//
//    @Bean
//    public Docket createRestApi() {
//        //可以控制 哪些符合条件的 接口 对外暴露文档；
//        Predicate<RequestHandler> predicate = (input) -> {
//            Set<String> patterns = input.getRequestMapping().getPatternsCondition().getPatterns();
//            for (String cur : patterns) {
//                if (cur.startsWith("/api")) return true;
//            }
//            return false;
//        };
//
//        ResponseMessage responseMesssageSucc = new ResponseMessageBuilder()
//                .code(0)
//                .message("success")
//                .build();
//        ResponseMessage responseMesssageFail = new ResponseMessageBuilder()
//                .code(-1)
//                .message("failed")
//                .build();
//        List<ResponseMessage> list = new ArrayList();
//        list.add(responseMesssageSucc);
//        list.add(responseMesssageFail);
//
//        Docket build = new Docket(DocumentationType.SWAGGER_2)
//                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.POST, list)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(predicate)
//                .apis(RequestHandlerSelectors.basePackage("com.zjut.smartClassroom.Controller"))
//                .paths(PathSelectors.any())
//                .build();
//        return build;
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 第一个方法设置访问路径前缀，第二个方法设置资源路径
//        registry.addResourceHandler("/dist/**").addResourceLocations("classpath:/templates/dist/");
//    }
//}

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}