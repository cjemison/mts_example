package com.cjemison.mts.controller;

import com.cjemison.mts.controller.model.StudentRequestVO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

/**
 * Created by cjemison on 7/22/16.
 */
public interface StudentController {

  DeferredResult<ResponseEntity<?>> post(final StudentRequestVO studentRequestVO);

  DeferredResult<ResponseEntity<?>> postList(final List<StudentRequestVO> list);
}
