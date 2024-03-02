package com.stocktracker.base.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

import com.stocktracker.base.entity.exception.ApplicationSetupException;

public final class ApiConfig {

    private final String host;
    private final String path;
    private final String query;
    private final String[] headers;

    public ApiConfig(String configFileName) {
        Properties props = loadConfiguration(configFileName);
        this.host = Objects.requireNonNull(props.getProperty("api.host"));
        this.path = Objects.requireNonNull(props.getProperty("api.path"));
        this.query = props.getProperty("api.query", "");
        String[] headerBundle = props.getProperty("api.headers").split(",");
        String[] userAgentHeader = {
            "User-Agent", Objects.requireNonNull(props.getProperty("api.header.useragent"))
        };
        this.headers = Stream.concat(Arrays.stream(headerBundle), Arrays.stream(userAgentHeader))
                .toArray(String[]::new);
    }

    private static Properties loadConfiguration(String fileName) {
        Properties properties = new Properties();
        try (InputStream input = ApiConfig.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new ApplicationSetupException("Unable to find file: " + fileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new ApplicationSetupException("Unable to setup config for " + fileName, e);
        }
        return properties;
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

    public String[] getHeaders() {
        return headers;
    }
}
