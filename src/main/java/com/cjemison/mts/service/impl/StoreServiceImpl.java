package com.cjemison.mts.service.impl;

import com.cjemison.mts.controller.model.StudentRequestVO;
import com.cjemison.mts.controller.model.StudentResponseVO;
import com.cjemison.mts.dao.StoreDAO;
import com.cjemison.mts.service.StoreService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import rx.Observable;
import rx.exceptions.Exceptions;

/**
 * Created by cjemison on 7/22/16.
 */
@Service
public class StoreServiceImpl implements StoreService {
  private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

  @Autowired
  private StoreDAO storeDAO;

  @Value("${encKey}")
  private String encryptedKey;

  @Override
  public Observable<Optional<StudentResponseVO>> store(final StudentRequestVO vo) {
    logger.debug("StudentRequestVO: {}", vo);
    return Observable.just(vo)
          .flatMap(stringOptional -> getEncryptionString(stringOptional.getId()))
          .flatMap(encryptKey -> {
            Optional<StudentResponseVO> itemOutcome = Optional.empty();
            if (encryptKey.isPresent()) {
              return storeDAO.store(encryptKey.get());
            }
            return Observable.just(itemOutcome);
          });
  }

  @Override
  public Observable<List<StudentResponseVO>> store(final List<StudentRequestVO> list) {
    logger.debug("<List<StudentResponseVO>: {}", list);
    Assert.notNull(list);
    Observable.just(list)
          .flatMapIterable(ids -> ids)
          .flatMap(studentRequestVO -> getEncryptionString(studentRequestVO.getId())).flatMap
          (encryptKey -> {
            Optional<StudentResponseVO> itemOutcome = Optional.empty();
            if (encryptKey.isPresent()) {
              return storeDAO.store(encryptKey.get());
            }
            return Observable.just(itemOutcome);
          });

    return Observable.empty();
  }

  private Observable<Optional<String>> getEncryptionString(final String id) {
    Optional<String> bycrypt = Optional.empty();
    try {
      Key aesKey = new SecretKeySpec(encryptedKey.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, aesKey);
      byte[] encrypted = cipher.doFinal(id.getBytes());
      String value = Base64.getEncoder().encodeToString(encrypted);
      bycrypt = Optional.of(value);
      logger.trace("ID: {} Encrypted: {}", value);
    } catch (Exception e) {
      logger.warn("### ERROR ###", e);
      throw Exceptions.propagate(e);
    }
    return Observable.just(bycrypt);
  }
}
