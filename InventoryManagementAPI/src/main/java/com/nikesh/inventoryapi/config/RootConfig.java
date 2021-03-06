package com.nikesh.inventoryapi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @author Nikesh Maharjan
 */
@Configuration
@ComponentScan(basePackages = "com.nikesh.inventoryapi",
        excludeFilters = {
            @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
        })
public class RootConfig {

}
