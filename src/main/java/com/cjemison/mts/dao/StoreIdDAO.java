package com.cjemison.mts.dao;

import rx.Observable;

import java.util.Optional;

/**
 * Created by cjemison on 7/23/16.
 */
public interface StoreIdDAO {
    Observable<Optional<String>> store(final String id);
}
