package com.cjemison.mts.controller;

import com.cjemison.mts.controller.model.StudentVORequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by cjemison on 7/23/16.
 */
public interface ValidUserController {
    DeferredResult<ResponseEntity<?>> post(final StudentVORequest studentVORequest);
}
