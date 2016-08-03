package com.cjemison.mts.service;

import com.cjemison.mts.controller.model.StudentResponseVO;

import java.util.Optional;

import rx.Observable;

/**
 * Created by cjemison on 7/23/16.
 */
public interface GetService {

  Observable<Optional<StudentResponseVO>> get(final String id);
}
