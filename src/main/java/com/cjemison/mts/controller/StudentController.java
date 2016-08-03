package com.cjemison.mts.controller;

import com.cjemison.mts.controller.model.StudentRequestVO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by cjemison on 7/22/16.
 */
public interface StudentController {

  DeferredResult<ResponseEntity<?>> post(final StudentRequestVO studentRequestVO);

  DeferredResult<ResponseEntity<?>> search(final StudentRequestVO studentRequestVO);
}
