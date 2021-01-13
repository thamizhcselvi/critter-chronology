package com.udacity.jdnd.course3.critter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CritterConfig {

        @Bean
        @ConfigurationProperties("spring.datasource")
        public DataSource getDatasource() {
            DataSourceBuilder dsb = DataSourceBuilder.create();
            dsb.username("user1");
            dsb.password(securePasswordService());
            dsb.url("jdbc:mysql://localhost:3306/critter");
            return dsb.build();
        }

        private String securePasswordService() {
            return "admin808";
        }
}
