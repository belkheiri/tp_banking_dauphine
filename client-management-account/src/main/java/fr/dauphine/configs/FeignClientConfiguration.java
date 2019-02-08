/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.configs;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 *
 * @author jihane and zouhair
 */
@Configuration
public class FeignClientConfiguration {

    
    @Bean
    public static Request.Options requestOptions(ConfigurableEnvironment env) {
        int ribbonReadTimeout = env.getProperty("ribbon.ReadTimeout", int.class, 900000);
        int ribbonConnectionTimeout = env.getProperty("ribbon.ConnectTimeout", int.class, 60000);

        return new Request.Options(ribbonConnectionTimeout, ribbonReadTimeout);
    }

    
}
