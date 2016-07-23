package com.cjemison.mts.service;

import com.cjemison.mts.controller.model.StudentVOResponse;
import rx.Observable;

import java.util.Optional;

/**
 * Created by cjemison on 7/23/16.
 */
public interface GetService {

    Observable<Optional<StudentVOResponse>> get(final String id);
}
