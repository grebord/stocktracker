package com.stocktracker.base.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.tinylog.Logger;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.stocktracker.base.config.ApiConfig;
import com.stocktracker.base.entity.BymaSecurity;
import com.stocktracker.base.entity.Security;
import com.stocktracker.base.entity.exception.DataProviderException;
import com.stocktracker.base.entity.util.HttpResponseCode;
import com.stocktracker.base.util.HttpUtil;

public final class BymaService {

    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(20);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(60);
    private static final String REQUEST_SCHEME = "https";
    private static final int MAX_RETRIES = 9;
    private static final int RETRY_DELAY_MS = 2500;

    private final ApiConfig apiConfig;
    private final HttpClient httpClient;
    
    public BymaService() {
        apiConfig = new ApiConfig("byma.properties");
        httpClient = HttpClient.newBuilder()
                .sslContext(HttpUtil.getDisabledCertificateValidationContext())
                .connectTimeout(CONNECT_TIMEOUT)
                .build();
    }

    @SuppressWarnings("unused") // For dependency injection
    private BymaService(ApiConfig apiConfig, HttpClient httpClient) {
        this.apiConfig = apiConfig;
        this.httpClient = httpClient;
    }

    public synchronized Security getData(final String symbol) throws DataProviderException {
        HttpRequest request = setupRequest(symbol);
        HttpResponse<String> response = sendRequest(request);
        return getResponseObject(response);   
    }

    private HttpRequest setupRequest(String symbol) {
        URI uri;
        try {
            uri = new URI(
                REQUEST_SCHEME,
                apiConfig.getHost(),
                apiConfig.getPath(),
                null
            );
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }

        JSONObject bodyObject = new JSONObject();
        bodyObject.put("symbol", symbol);
        bodyObject.put("settlementType", "3");
        bodyObject.put("Content-Type", "application/json");
        String body = bodyObject.toJSONString();

        HttpRequest request = HttpRequest.newBuilder(uri)
                .timeout(REQUEST_TIMEOUT)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .headers(apiConfig.getHeaders())
                .build();
        Logger.debug("REQUEST: {}\n{}", request, body);
        return request;
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws DataProviderException {
        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int retries = 0;
            while (response.statusCode() == HttpResponseCode.SERVICE_UNAVAILABLE.getCode() && retries++ < MAX_RETRIES) {
                Thread.sleep(RETRY_DELAY_MS);
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            }
        } catch (IOException e) {
            throw new DataProviderException(
                "ERROR fetching data from BYMA API through request: " + request.toString(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DataProviderException("INTERRUPTED while fetching data from BYMA API", e);
        }
        Logger.debug("RESPONSE: {}\nBody: {}", response, response.body());

        if (response.statusCode() != HttpResponseCode.OK.getCode()) {
            throw new DataProviderException(
                "ERROR in BYMA response, status code: " + response.statusCode() + ", body: " + response.body());
        }
        return response;
    }

    private Security getResponseObject(HttpResponse<String> response) throws DataProviderException {
        try {
            JSONObject responseObject = JSON.parseObject(response.body());
            BymaSecurity[] bymaSecurity = responseObject.getJSONArray("data")
                    .toArray(BymaSecurity.class);
            return new Security(bymaSecurity[0]);
        } catch (NullPointerException | IndexOutOfBoundsException | IllegalArgumentException e) {
            throw new DataProviderException(
                "ERROR creating Security from BYMA response body: " + response.body(), e);
        }
    }
}
