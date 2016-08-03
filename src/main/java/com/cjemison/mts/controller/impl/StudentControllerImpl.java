package com.cjemison.mts.controller.impl;

import com.cjemison.mts.controller.StudentController;
import com.cjemison.mts.controller.model.StudentRequestVO;
import com.cjemison.mts.controller.model.StudentResponseVO;
import com.cjemison.mts.service.GetService;
import com.cjemison.mts.service.StoreService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RequestMapping(value = "/v1", consumes = MediaType.APPLICATION_JSON_VALUE)
public class StudentControllerImpl implements StudentController {
  private static final Logger logger = LoggerFactory.getLogger(StudentControllerImpl.class);

  @Autowired
  private GetService getService;

  @Autowired
  private StoreService storeService;

  @Override
  @RequestMapping(value = "/student",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE)
  public DeferredResult<ResponseEntity<?>> post(@RequestBody
                                                @Valid
                                                final StudentRequestVO studentRequestVO) {
    Assert.notNull(studentRequestVO);
    logger.debug("Payload: {}", studentRequestVO);
    final DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();

    storeService.store(studentRequestVO).subscribe(id -> {
      result.setResult(new ResponseEntity<StudentResponseVO>(id.get(), HttpStatus.CREATED));
    });
    return result;
  }

  @Override
  @RequestMapping(value = "/search",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE)
  public DeferredResult<ResponseEntity<?>> search(@RequestBody
                                                  @Valid
                                                  final StudentRequestVO studentRequestVO) {
    Assert.notNull(studentRequestVO);
    logger.debug("Payload: {}", studentRequestVO);
    final DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();
    getService.get(studentRequestVO.getId()).subscribe(studentResponseVO -> {
      if (studentResponseVO.isPresent()) {
        result.setResult(new ResponseEntity<StudentResponseVO>(studentResponseVO.get(),
              HttpStatus.CREATED));
      } else {
        result.setErrorResult(ResponseEntity.notFound().build());
      }
    });
    return result;
  }
}
