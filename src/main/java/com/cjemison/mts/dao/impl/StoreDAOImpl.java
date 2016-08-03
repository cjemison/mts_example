package com.cjemison.mts.dao.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.cjemison.mts.controller.model.StudentResponseVO;
import com.cjemison.mts.dao.StoreDAO;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import rx.Observable;
import rx.exceptions.Exceptions;

/**
 * Created by cjemison on 7/23/16.
 */
@Repository
public class StoreDAOImpl implements StoreDAO {
  private static final Logger logger = LoggerFactory.getLogger(StoreDAOImpl.class);
  private final String TABLE_NAME = "student_ids";

  @Autowired
  private AmazonDynamoDBClient amazonDynamoDBClient;

  @Autowired
  private DateTimeFormatter dateTimeFormatter;

  @Override
  public Observable<Optional<StudentResponseVO>> store(final String id) {
    return Observable.create(subscriber -> {
      subscriber.onNext(Optional.ofNullable(id));
      subscriber.onCompleted();
    }).flatMap(stringOptional -> {
      Optional<StudentResponseVO> optional = Optional.empty();
      try {
        String createdDate = dateTimeFormatter.print(dateTime());
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDBClient);
        Table table = dynamoDB.getTable(TABLE_NAME);
        PutItemSpec putItemSpec = new PutItemSpec();
        putItemSpec.withItem(new Item()
              .withPrimaryKey("id", id)
              .withString("createdDate", createdDate));
        table.putItem(putItemSpec);
        optional = Optional.of(new StudentResponseVO(id, createdDate));
      } catch (Exception e) {
        logger.warn("### ERROR ###", e);
        throw Exceptions.propagate(e);
      }
      return Observable.just(optional);
    }).defaultIfEmpty(Optional.empty());
  }

  public DateTime dateTime() {
    return new DateTime(DateTimeZone.UTC);
  }
}
