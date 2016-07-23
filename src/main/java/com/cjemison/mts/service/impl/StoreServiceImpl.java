package com.cjemison.mts.service.impl;

import com.cjemison.mts.controller.model.StudentVORequest;
import com.cjemison.mts.dao.StoreIdDAO;
import com.cjemison.mts.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import rx.Observable;
import rx.exceptions.Exceptions;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Optional;

/**
 * Created by cjemison on 7/22/16.
 */
@Service
public class StoreServiceImpl implements StoreService {
    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Autowired
    private StoreIdDAO storeIdDAO;

    @Value("${encKey}")
    private String encryptedKey;

    @Override
    public Observable<Optional<String>> store(final StudentVORequest vo) {
        logger.debug("StudentVORequest: {}", vo);
        return Observable.create((Observable.OnSubscribe<Optional<String>>) subscriber -> {
            Assert.notNull(vo);
            Assert.notNull(vo.getId());
            logger.debug("StudentVORequest: {}", vo);
            subscriber.onNext(Optional.of(vo.getId()));
            subscriber.onCompleted();
        }).flatMap(stringOptional -> {
            Optional<String> bycrpt = Optional.empty();
            if (stringOptional.isPresent()) {
                try {
                    Key aesKey = new SecretKeySpec(encryptedKey.getBytes(), "AES");
                    Cipher cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                    byte[] encrypted = cipher.doFinal(stringOptional.get().getBytes());
                    String value = Base64.getEncoder().encodeToString(encrypted);
                    bycrpt = Optional.of(value);
                    logger.trace("ID: {} Encrypted: {}", value);
                } catch (Exception e) {
                    logger.warn("### ERROR ###", e);
                    throw Exceptions.propagate(e);
                }
            }
            return Observable.just(bycrpt);
        }).flatMap(encyptKey -> {
            Optional<String> itemOutcome = Optional.empty();
            if (encyptKey.isPresent()) {
                return storeIdDAO.store(encyptKey.get());
            }
            return Observable.just(itemOutcome);
        }).flatMap(a -> {
            Optional<String> optional = Optional.empty();
            if (a.isPresent()) {
                optional = Optional.of(a.get());
            }
            return Observable.just(optional);
        });
    }
}
