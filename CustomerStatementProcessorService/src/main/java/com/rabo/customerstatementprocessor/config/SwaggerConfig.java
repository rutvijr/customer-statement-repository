package com.rabo.customerstatementprocessor.config;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This class contains the Swagger documentation for this service.
 * 
 * @author rutvij.ravii@cardinalhealth.com
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    private static final String API_NAME                  = "CustomerStatementProcessorService Service API";
    private static final String PACKAGE_SCAN              = "com.rabo.customerstatementprocessor.controller";

    private static final String HTTP_MESSAGE_TYPE_ERROR   = "Error";
    private static final String HTTP_MESSAGE_TYPE_WARNING = "Warning";
    private static final String HTTP_MESSAGE_TYPE_SUCCESS = "Success";

    /**
     * It returns the API info of the service.
     *
     * @return the API info
     */
    public ApiInfo apiInfo()
    {
        return new ApiInfoBuilder().title("CustomerStatementProcessorService")
                                   .description("This spec is for accessing the customer statement processor service")
                                   .contact(new Contact("", "", "rutvijr@gmail.com"))
                                   .build();
    }

    /**
     * It returns the Docket that represents the Swagger documentation of this
     * service.
     *
     * @return the Docket
     */
    @Bean
    public Docket docketApi()
    {
        return new Docket(DocumentationType.SWAGGER_2).groupName(API_NAME)
                                                      .select()
                                                      .apis(RequestHandlerSelectors.basePackage(PACKAGE_SCAN))
                                                      .paths(PathSelectors.any())
                                                      .build()
                                                      .globalResponseMessage(POST, postResponseMessages())
                                                      .apiInfo(apiInfo());
    }

    private List<ResponseMessage> postResponseMessages()
    {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(HttpStatus.OK.value())
                                                         .message(HttpStatus.OK.name())
                                                         .responseModel(new ModelRef(HTTP_MESSAGE_TYPE_SUCCESS))
                                                         .build());
        responseMessages.add(new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
                                                         .message(HttpStatus.BAD_REQUEST.name())
                                                         .responseModel(new ModelRef(HTTP_MESSAGE_TYPE_WARNING))
                                                         .build());
        responseMessages.add(new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                         .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                                                         .responseModel(new ModelRef(HTTP_MESSAGE_TYPE_ERROR))
                                                         .build());
        return responseMessages;
    }
}
