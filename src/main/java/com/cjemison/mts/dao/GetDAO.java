package com.cjemison.mts.dao;

import com.cjemison.mts.controller.model.StudentResponseVO;

import java.util.Optional;

import rx.Observable;

/**
 * Created by cjemison on 7/23/16.
 */
public interface GetDAO {
  Observable<Optional<StudentResponseVO>> get(final String id);
}
