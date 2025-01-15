package com.teenthofabud.wizard.nandifoods.wms.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teenthofabud.wizard.nandifoods.wms.handler.OptionalUnitClassMeasuredValuesDtoCollectionComparator;
import com.teenthofabud.wizard.nandifoods.wms.handler.UnitClassMeasuredValuesDtoCollectionComparator;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.OptionalUnitClassMeasuredValuesDtoCollection;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassMeasuredValuesDtoCollection;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.hibernate.validator.HibernateValidator;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.ListCompareAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Configuration
public class ServiceConfiguration {

    @Value("${wms.settings.unit.approvalTimeFormat}}")
    private String unitApprovalTimeFormat;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = JsonMapper.builder()
                                        .addModule(new Jdk8Module().configureAbsentsAsNulls(false))
                                        .addModule(new JavaTimeModule())
                                        //.addModule(new JSR353Module())
                                        .serializationInclusion(JsonInclude.Include.NON_NULL)
                                        //.serializationInclusion(JsonInclude.Include.NON_ABSENT)
                                        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                                        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                                        .defaultDateFormat(new SimpleDateFormat(unitApprovalTimeFormat))
                                        .build();
        return mapper;
    }

    @Bean
    public BeanUtilsBean beanUtilsBean() {
        BeanUtilsBean beanUtilsBean = BeanUtilsBean2.getInstance();
        return beanUtilsBean;
    }

    @Bean
    public Javers javers() {
        JavaType optionalListJavaType = TypeFactory.defaultInstance().constructParametricType(Optional.class, List.class);
        return JaversBuilder
                .javers()
                .registerValue(optionalListJavaType.getRawClass())
                .registerValue(OptionalUnitClassMeasuredValuesDtoCollection.class, new OptionalUnitClassMeasuredValuesDtoCollectionComparator())
                .registerValue(UnitClassMeasuredValuesDtoCollection.class, new UnitClassMeasuredValuesDtoCollectionComparator())
                .build();
    }

    @Bean
    public Validator validator(final AutowireCapableBeanFactory autowireCapableBeanFactory) {

        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                                                      .configure().constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
                                                      .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator;
    }

}
