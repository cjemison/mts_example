package com.cjemison.mts.service;

import com.cjemison.mts.controller.model.StudentVORequest;
import rx.Observable;

import java.util.Optional;

/**
 * Created by cjemison on 7/22/16.
 */
public interface StoreService {
    Observable<Optional<String>> store(final StudentVORequest studentVORequest);
}
