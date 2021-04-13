package com.restTry.restService.config;

import com.restTry.restService.challenge.SearchSvcInterface;
import com.restTry.restService.challenge.SearchSvcImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
@Profile("default")
public class SearchConfiguration {
    @Bean
    @Scope
    public SearchSvcInterface candSearch() {
        return new SearchSvcImpl();
    }
}

