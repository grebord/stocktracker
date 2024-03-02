package com.stocktracker.base;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stocktracker.base.config.ApiConfig;
import com.stocktracker.base.entity.Security;
import com.stocktracker.base.service.YahooService;

@ExtendWith(MockitoExtension.class)
class YahooServiceTest {

    @Spy
    ApiConfig apiConfig = new ApiConfig("yahoo-test.properties");
    
    @Mock
    HttpClient httpClient;

    @InjectMocks
    YahooService yahooService;

    @Test
    void testTheWholeThingsHappyPath() throws Exception {
        // Arrange
        Path filePath = Path.of(getClass().getResource("/yahooResponseExample.json").getPath());
        String responseStr = new String(Files.readAllBytes(filePath));
        @SuppressWarnings("unchecked") // Mockito ensures type safety at runtime
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);

        Mockito.when(mockResponse.body()).thenReturn(responseStr);
        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(httpClient.send(ArgumentMatchers.any(), ArgumentMatchers.eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockResponse);
        
        // Act
        Security security = yahooService.getData("QQQ");

        // Assert
        Mockito.verify(httpClient).send(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verify(apiConfig).getHeaders();
        
        Assertions.assertEquals("QQQ", security.getSymbol());
        Assertions.assertEquals(10, security.getAskSize());
        Assertions.assertEquals(421.88, security.getLastPrice());
    }
}
