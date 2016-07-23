package com.cjemison.mts.controller.impl;

import com.cjemison.mts.controller.PostStudentIdController;
import com.cjemison.mts.controller.model.StudentVORequest;
import com.cjemison.mts.controller.model.StudentVOResponse;
import com.cjemison.mts.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 * Created by cjemison on 7/22/16.
 */
@RestController
@RequestMapping(value = "/v1", consumes = MediaType.APPLICATION_JSON_VALUE)
public class PostStudentIdControllerImpl implements PostStudentIdController {
    private static final Logger logger = LoggerFactory.getLogger(PostStudentIdControllerImpl.class);

    @Autowired
    private StoreService storeService;

    @Override
    @RequestMapping(value = "/id",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<?>> post(@RequestBody
                                                  @Valid
                                                  final StudentVORequest studentVORequest) {
        Assert.notNull(studentVORequest);
        logger.debug("Payload: {}", studentVORequest);
        final DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();

        storeService.store(studentVORequest).subscribe(id -> {
            ResponseEntity<?> responseEntity =
                    new ResponseEntity<StudentVOResponse>(new StudentVOResponse(studentVORequest.getId(),
                            id.get()), HttpStatus.CREATED);
            result.setResult(responseEntity);
        });
        return result;
    }
}
