package com.studyhole.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.apache.commons.io.IOUtils;


@Service
@AllArgsConstructor
@NoArgsConstructor
@org.springframework.context.annotation.Lazy
public class RSAKeyLoaderService {

    @Value("${jwt.public.key}")
    private Resource publicKeyResource;

    @Value("${jwt.private.key}")
    private Resource privateKeyResource;

    public RSAPublicKey publicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = loadKeyBytes(publicKeyResource);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey)keyFactory.generatePublic(keySpec);
    }
    
    public RSAPrivateKey privateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateKeyBytes = loadKeyBytes(privateKeyResource);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
    }
    
    private byte[] loadKeyBytes(Resource keyResource) throws IOException {
        try (InputStream inputStream = keyResource.getInputStream()) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return bytes;
        }
    }
}
