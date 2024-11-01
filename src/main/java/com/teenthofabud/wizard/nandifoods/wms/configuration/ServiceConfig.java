package com.teenthofabud.wizard.nandifoods.wms.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.reflections.Reflections;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.reflections.scanners.Scanners.ConstructorsSignature;


@Configuration
public class ServiceConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = JsonMapper.builder()
                                        .addModule(new Jdk8Module())
                                        .addModule(new JavaTimeModule())
                                        //.addModule(new JSR353Module())
                                        .serializationInclusion(JsonInclude.Include.NON_NULL)
                                        .serializationInclusion(JsonInclude.Include.NON_ABSENT)
                                        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                                        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                                        .build();
        return mapper;
    }

    public void cacheDTOTypes(ObjectMapper mapper) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections(
                "com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto",
                "com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.dto",
                "com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.dto");

        Set<Constructor> someConstructors =
                reflections.get(ConstructorsSignature.of(UOMDto.class).as(Constructor.class));
        Constructor constructorSignature = someConstructors.iterator().next();
        Object instance = constructorSignature.newInstance();

        JsonNode root = mapper.valueToTree(instance);
        Map<String, JsonNode> flattenedMap = new JsonFlattener(root).flatten();

    }

    private static final class JsonFlattener {

        private final Map<String, JsonNode> json = new LinkedHashMap<>(64);
        private final JsonNode root;

        JsonFlattener(JsonNode node) {
            this.root = Objects.requireNonNull(node);
        }

        public Map<String, JsonNode> flatten() {
            process(root, "");
            return json;
        }

        private void process(JsonNode node, String prefix) {
            if (node.isObject()) {
                ObjectNode object = (ObjectNode) node;
                object
                        .fields()
                        .forEachRemaining(
                                entry -> {
                                    process(entry.getValue(), prefix + "/" + entry.getKey());
                                });
            } else if (node.isArray()) {
                ArrayNode array = (ArrayNode) node;
                AtomicInteger counter = new AtomicInteger();
                array
                        .elements()
                        .forEachRemaining(
                                item -> {
                                    process(item, prefix + "/" + counter.getAndIncrement());
                                });
            } else {
                json.put(prefix, node);
            }
        }
    }

    /*@Bean("mocMessageResource")
    public ReloadableResourceBundleMessageSource messageSource() {
        MOCMessageResource messageSource = new MOCMessageResource();
        messageSource.setBasename("messages");
        return messageSource;
    }


    @Bean("mocLocaleResolver")
    public LocaleResolver localeResolver() {
        MOCLocaleResolver localeResolver = new MOCLocaleResolver();
        localeResolver.setDefaultLocale(MOCLocaleResolver.DEFAULT_LOCALE);
        return localeResolver;
    }*/

    @Bean
    public Validator validator(final AutowireCapableBeanFactory autowireCapableBeanFactory) {

        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                                                      .configure().constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
                                                      .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }

    @Bean
    public DateTimeFormatter hedEventTimestampFormatter() {
        return new DateTimeFormatterBuilder()
                .append(ISO_LOCAL_DATE_TIME) // use the existing formatter for date time
                .appendOffset("+HH:MM", "+00:00") // set 'noOffsetText' to desired '+00:00'
                .toFormatter();
    }

}
