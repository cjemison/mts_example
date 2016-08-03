package com.cjemison.mts.dao.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.cjemison.mts.controller.model.StudentResponseVO;
import com.cjemison.mts.dao.GetDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Optional;

import rx.Observable;
import rx.exceptions.Exceptions;

/**
 * Created by cjemison on 7/23/16.
 */
@Repository
public class GetDAOImpl implements GetDAO {
  private static final Logger logger = LoggerFactory.getLogger(StoreDAOImpl.class);
  private final String TABLE_NAME = "student_ids";
  private final String[] ATTRIBUES = {"id", "createdDate"};

  @Autowired
  private AmazonDynamoDBClient amazonDynamoDBClient;


  @Override
  public Observable<Optional<StudentResponseVO>> get(final String id) {
    return Observable.just(Optional.ofNullable(id)).flatMap(stringOptional -> {
      Optional<StudentResponseVO> itemOptional = Optional.empty();
      if (stringOptional.isPresent() && !StringUtils.isEmpty(stringOptional.get())) {
        try {
          DynamoDB dynamoDB = new DynamoDB(amazonDynamoDBClient);
          Table table = dynamoDB.getTable(TABLE_NAME);
          GetItemSpec getItemSpec = new GetItemSpec()
                .withPrimaryKey("id", stringOptional.get());
          Item item = table.getItem(getItemSpec);
          if (item != null) {
            StudentResponseVO studentResponseVO = new StudentResponseVO(
                  item.getString("id"),
                  item.getString("createdDate"));
            itemOptional = Optional.ofNullable(studentResponseVO);
          }
        } catch (Exception e) {
          logger.warn("### ERROR ###", e);
          throw Exceptions.propagate(e);
        }
      }
      return Observable.just(itemOptional).defaultIfEmpty(Optional.empty());
    }).defaultIfEmpty(Optional.empty());
  }
}
