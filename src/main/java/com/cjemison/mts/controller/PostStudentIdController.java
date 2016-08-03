package com.cjemison.mts.controller;

import com.cjemison.mts.controller.model.StudentVORequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by cjemison on 7/22/16.
 */
public interface PostStudentIdController {
  DeferredResult<ResponseEntity<?>> post(final StudentVORequest studentVORequest);

  //DeferredResult<ResponseEntity<?>> postList(final List<StudentVORequest> studentVORequestList);
}
