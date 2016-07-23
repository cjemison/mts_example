package com.cjemison.mts.controller.impl;

import com.cjemison.mts.controller.ValidUserController;
import com.cjemison.mts.controller.model.StudentVORequest;
import com.cjemison.mts.service.GetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;

/**
 * Created by cjemison on 7/23/16.
 */
@RestController
@RequestMapping(value = "/v1", consumes = MediaType.APPLICATION_JSON_VALUE)
public class ValidUserControllerImpl implements ValidUserController {
    private static final Logger logger = LoggerFactory.getLogger(PostStudentIdControllerImpl.class);

    @Autowired
    private GetService getService;

    @Override
    @RequestMapping(value = "/valid",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<?>> post(@RequestBody
                                                  @Valid
                                                  final StudentVORequest studentVORequest) {
        Assert.notNull(studentVORequest);
        logger.debug("Payload: {}", studentVORequest);
        final DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();

        getService.get(studentVORequest.getId()).subscribe(a -> {
            ResponseEntity<?> responseEntity;
            if (a.isPresent()) {
                responseEntity = ResponseEntity.ok(a.get());
            } else {
                responseEntity = ResponseEntity.notFound().build();
            }
            result.setResult(responseEntity);
        });
        return result;
    }
}
