package com.cjemison.mts.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by cjemison on 7/22/16.
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter implements InitializingBean {

  @Value("${aws.accessKey}")
  private String accessKey;

  @Value("${aws.password}")
  private String secretKey;

  @Bean
  public AmazonDynamoDBClient amazonDynamoDBClient() throws Exception {
    AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
    return new AmazonDynamoDBClient(credentials);
  }

  @Bean
  public DateTimeFormatter dateTimeFormatter() {
    return DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
  }

  @Override
  public void afterPropertiesSet() throws Exception {

  }
}