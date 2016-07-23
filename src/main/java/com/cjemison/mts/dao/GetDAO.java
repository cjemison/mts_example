package com.cjemison.mts.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import rx.Observable;

import java.util.Optional;

/**
 * Created by cjemison on 7/23/16.
 */
public interface GetDAO {
    Observable<Optional<Item>> get(final String id);
}
