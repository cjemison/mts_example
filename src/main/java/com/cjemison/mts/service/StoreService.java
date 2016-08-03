package com.cjemison.mts.service;

import com.cjemison.mts.controller.model.StudentRequestVO;
import com.cjemison.mts.controller.model.StudentResponseVO;

import java.util.List;
import java.util.Optional;

import rx.Observable;

/**
 * Created by cjemison on 7/22/16.
 */
public interface StoreService {
  Observable<Optional<StudentResponseVO>> store(final StudentRequestVO studentRequestVO);

  Observable<List<StudentResponseVO>> store(final List<StudentRequestVO> list);
}
