package com.cjemison.mts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders
      .AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers
      .GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by cjemison on 8/3/16.
 */
@Configuration
@Order(2)
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

  @Value("${restUser}")
  private String restUser;

  @Value("${restPass}")
  private String restPass;

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService());
  }

  @Bean
  UserDetailsService userDetailsService() {
    return username -> {
      if (username.equals(restUser)) {
        return new User(username, restPass, true, true, true, true,
              AuthorityUtils.createAuthorityList("USER"));
      }
      throw new UsernameNotFoundException("could not find the user '"
            + username + "'");
    };
  }
}
