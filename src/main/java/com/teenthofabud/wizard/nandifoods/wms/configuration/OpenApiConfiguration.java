package com.teenthofabud.wizard.nandifoods.wms.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
/**
 * https://www.baeldung.com/spring-rest-openapi-documentation </br>
 * http://localhost:8080/swagger-ui.html
 */
@Profile("!test")
public class OpenApiConfiguration {

     //private static final String SERVICE_DESCRIPTION = "Inventory Management";

  @Value("${spring.application.version}")
  private String version;

  @Value("${spring.application.name}")
  private String name;

  @Value("${spring.application.description}")
  private String description;

  @Bean
  public OpenAPI springOpenAPI() {

    return new OpenAPI()
          .info(
            new Info()
              .title(name)
              .contact(contactBuilder())
              .description(description)
      .version(version));
  }

  private Contact contactBuilder() {
    Contact contact = new Contact();
    contact.setEmail("anirban.das@t-online.de");
    contact.setName("Anirban Das");
    return contact;
  }

  @Bean
  public GroupedOpenApi groupedOpenApi() {
    String[] paths = {"/**"};
    String[] packagedToMatch = {
            "com.teenthofabud.wizard.nandifoods.wms.settings.unit",
            "com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom"
    };
    return GroupedOpenApi
      .builder()
      .group("WMS API")
      .pathsToMatch(paths)
      .packagesToScan(packagedToMatch)
      .addOpenApiCustomizer(openApi ->  {
        Info info = openApi.getInfo();
        info.setDescription(description);
      }).build();
  }

}