package com.teenthofabud.wizard.nandifoods.wms.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@EnableWebMvc
@Configuration
@EnableSpringDataWebSupport(
        pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
)
public class WebConfiguration implements WebMvcConfigurer {

    @Value("#{'${wms.cors.allowedOrigins}'.split(',')}")
    private List<String> allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
       CorsRegistration corsRegistration = registry.addMapping("/**");
       String[] allowedMethods = Arrays.stream(HttpMethod.values())
               .map(HttpMethod::name)
               .toArray(String[]::new);
       corsRegistration.allowedMethods(allowedMethods);
       corsRegistration.allowedOrigins(allowedOrigins.toArray(String[]::new));
    }

    @Bean
    public RouterFunction<ServerResponse> homeRouter() {
        ClassPathResource index = new ClassPathResource("static/index.html");
        return route().resource(path("/"), index).build();
    }

}
