package com.stocktracker.base.util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.stocktracker.base.entity.exception.ApplicationSetupException;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class HttpUtil {

    private HttpUtil() { }

    public static SSLContext getDisabledCertificateValidationContext() {
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(
                null,
                new TrustManager[] { new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                    }
                } },
                null
            );
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new ApplicationSetupException("Failed to build SSLContext", e);
        }
        return sslContext;
    }
}
