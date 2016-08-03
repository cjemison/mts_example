package com.cjemison.mts.service.impl;

import com.cjemison.mts.controller.model.StudentResponseVO;
import com.cjemison.mts.dao.GetDAO;
import com.cjemison.mts.service.GetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.exceptions.Exceptions;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Optional;

/**
 * Created by cjemison on 7/23/16.
 */
@Service
public class GetServiceImpl implements GetService {
    private static final Logger logger = LoggerFactory.getLogger(GetServiceImpl.class);

    @Autowired
    private GetDAO getDAO;

    @Value("${encKey}")
    private String encryptedKey;

    @Override
    public Observable<Optional<StudentResponseVO>> get(final String id) {
        logger.debug("Id: {}", id);
        return Observable.just(Optional.of(id))
                .filter(Optional::isPresent)
                .map(stringOptional -> {
                    Optional<String> enc = Optional.empty();
                    try {
                        Key aesKey = new SecretKeySpec(encryptedKey.getBytes(), "AES");
                        Cipher cipher = Cipher.getInstance("AES");
                        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                        byte[] encrypted = cipher.doFinal(stringOptional.get().getBytes());
                        String value = Base64.getEncoder().encodeToString(encrypted);
                        enc = Optional.of(value);
                    } catch (Exception e) {
                        logger.warn("### ERROR ###", e);
                        throw Exceptions.propagate(e);
                    }
                    return enc;
                }).filter(Optional::isPresent)
                .flatMap(enc -> getDAO.get(enc.get()))
                .filter(Optional::isPresent)
                .flatMap(b -> {
                    StudentResponseVO response = new StudentResponseVO(id, b.get().getString("createdDate"));
                    return Observable.just(Optional.of(response));
                }).defaultIfEmpty(Optional.empty());
    }
}
