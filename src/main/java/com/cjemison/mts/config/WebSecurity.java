package com.cjemison.mts.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration
      .WebSecurityConfigurerAdapter;

/**
 * Created by cjemison on 8/3/16.
 */

@EnableWebSecurity
@Configuration
@Order(1)
public class WebSecurity extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().anyRequest().fullyAuthenticated().and().
          httpBasic().and().
          csrf().disable();
  }
}