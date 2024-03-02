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
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.stocktracker.base.config.ApiConfig;
import com.stocktracker.base.entity.Security;
import com.stocktracker.base.entity.YahooSecurity;
import com.stocktracker.base.entity.exception.DataProviderException;
import com.stocktracker.base.entity.util.HttpResponseCode;

public final class YahooService {

    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(20);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(60);
    private static final String REQUEST_SCHEME = "https";
    
    private final ApiConfig apiConfig;
    private final HttpClient httpClient;
    
    public YahooService() {
        apiConfig = new ApiConfig("yahoo.properties");
        httpClient = HttpClient.newBuilder().connectTimeout(CONNECT_TIMEOUT).build();
    }

    @SuppressWarnings("unused") // For dependency injection
    private YahooService(ApiConfig apiConfig, HttpClient httpClient) {
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
                String.format(apiConfig.getPath(), symbol),
                apiConfig.getQuery(),
                null
            );
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }

        HttpRequest request = HttpRequest.newBuilder(uri)
                .timeout(REQUEST_TIMEOUT)        
                .GET()
                .headers(apiConfig.getHeaders())
                .build();
        Logger.debug("REQUEST: {}", request);
        return request;
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws DataProviderException {
        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new DataProviderException(
                "ERROR fetching data from Yahoo API through request: " + request.toString(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DataProviderException("INTERRUPTED while fetching data from Yahoo API", e);
        }
        Logger.debug("Response: {}\nBody: {}", response, response.body());

        if (response.statusCode() != HttpResponseCode.OK.getCode()) {
            throw new DataProviderException(
                "ERROR in Yahoo response, status code: " + response.statusCode() + ", body: " + response.body());
        }
        return response;
    }

    private Security getResponseObject(HttpResponse<String> response) throws DataProviderException {
        try {
            JSONObject responseObject = JSON.parseObject(response.body());
            JSONArray results = responseObject.getJSONObject("optionChain")
                    .getJSONArray("result");
            YahooSecurity security = results.getJSONObject(0)
                    .getJSONObject("quote")
                    .toJavaObject(YahooSecurity.class);
            return new Security(security);
        } catch (NullPointerException | IndexOutOfBoundsException | IllegalArgumentException e) {
            throw new DataProviderException(
                "ERROR creating Security from Yahoo response body: " + response.body(), e);
        }
    }
}
