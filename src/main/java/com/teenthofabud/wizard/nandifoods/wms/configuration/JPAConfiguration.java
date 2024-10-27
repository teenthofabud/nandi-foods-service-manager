package com.teenthofabud.wizard.nandifoods.wms.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "com.teenthofabud.wizard.nandifoods.wms.settings.unit"
} )
@EnableJpaAuditing(auditorAwareRef = "auditListener")
public class JPAConfiguration {

    /**
     * Entities must be annotated with
     * "@EntityListeners(AuditingEntityListener.class)"
     */
    /*@Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAware<String>() {

            *//**
             * To fill the @CreatedBy annotated field, select the current spring security
             * user from the API call or return the static string "Tooluser"
             *//*
            public Optional<String> getCurrentAuditor() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication == null || !authentication.isAuthenticated()) {
                    return Optional.of(new String("DEFAULT"));
                }
                return Optional.of(authentication.getName());
            }
        };
    }*/

}