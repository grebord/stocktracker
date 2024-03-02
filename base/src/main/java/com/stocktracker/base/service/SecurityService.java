package com.stocktracker.base.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.tinylog.Logger;

import com.stocktracker.base.config.AppConfig;
import com.stocktracker.base.entity.Security;
import com.stocktracker.base.entity.dto.BondDto;
import com.stocktracker.base.entity.dto.StockDto;
import com.stocktracker.base.entity.exception.ApplicationSetupException;
import com.stocktracker.base.entity.exception.DataProviderException;

public final class SecurityService {

    private static final int RATE_LIMIT_DELAY_MS = 1500;

    private static final BymaService bymaService = new BymaService();
    private static final YahooService yahooService = new YahooService();
    private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private static BondDto bondData = null;

    private SecurityService() { }

    public static synchronized void stop() {
        executorService.shutdown();
    }

    public static synchronized void init() {
        if (!AppConfig.apiActive) return;
        Logger.info("Fetching BOND data..");
        try {
            Security al30 = bymaService.getData("AL30");
            Security al30d = fetchBymaDataWithRateLimit("AL30D");
            Security gd30d = fetchBymaDataWithRateLimit("GD30D");
            bondData = new BondDto(al30, al30d, gd30d);
            Logger.info("DONE fetching BOND data");
            SecurityService.class.notifyAll();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new ApplicationSetupException("Could not initialize bondData!", e);
        }
    }

    public static synchronized BondDto getBondData() {
        if (!AppConfig.apiActive) return new BondDto();
        while (bondData == null) {
            Logger.info("Waiting for bondData in BOND data request..");
            try {
                SecurityService.class.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        return bondData;
    }
    
    public static synchronized StockDto getStockData(final String symbol) {
        if (!AppConfig.apiActive) return new StockDto(symbol);

        Logger.info("Getting STOCK data for: {}", symbol);
        while (bondData == null) {
            Logger.info("Waiting for bondData in STOCK data request for: {}", symbol);
            try {
                SecurityService.class.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        
        String[] composite = symbol.split(":");
        String symbolName = composite[0];
        double symbolRatio = Double.parseDouble(composite[1]);

        Security baseStock;
        try {
            baseStock = yahooService.getData(symbolName);
        } catch (DataProviderException e) {
            Logger.error(e, "ERROR fetching Yahoo data for symbol " + symbol);
            baseStock = new Security();
        }

        Security cedearArs;
        try {
            cedearArs = bymaService.getData(symbolName);
        } catch (DataProviderException e) {
            Logger.error(e, "ERROR fetching BYMA data for symbol " + symbol);
            cedearArs = new Security();
        }

        Security cedearUsd;
        try {
            cedearUsd = fetchBymaDataWithRateLimit(symbolName + "D");
        } catch (ExecutionException e) {
            Logger.error(e, "ERROR fetching BYMA USD data for symbol " + symbol);
            cedearUsd = new Security();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        if (baseStock.isEmpty() && cedearArs.isEmpty() && cedearUsd.isEmpty()) {
            Logger.warn("Could not retrieve any data for symbol {}", symbol);
            return new StockDto("ERR:" + symbolName);
        }

        Logger.info("DONE getting STOCK data for: {}", symbol);
        double mepValue = bondData.getPriceAl30() / bondData.getPriceAl30d();
        return new StockDto(baseStock, symbolRatio, cedearArs, mepValue, cedearUsd);
    }

    private static Security fetchBymaDataWithRateLimit(String symbol) throws InterruptedException, ExecutionException {
        ScheduledFuture<Security> result = executorService.schedule(
            () -> bymaService.getData(symbol),
            RATE_LIMIT_DELAY_MS, TimeUnit.MILLISECONDS);
        return result.get();
    }
}
