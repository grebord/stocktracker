package com.stocktracker.base;

import org.tinylog.Logger;
import org.tinylog.configuration.Configuration;

import com.stocktracker.base.config.AppConfig;
import com.stocktracker.base.config.AppMessages;
import com.stocktracker.base.service.SecurityService;

public final class App {

    public static void main(String[] args) {
        Configuration.set("writer.level", "debug");
        init(true);
        Logger.info(AppMessages.testingBase());
        SecurityService.getStockData("GOOGL:58");
        stop();
    }

    public static void init(boolean apiActive) {
        AppConfig.apiActive = apiActive;
        AppMessages.initialize();
        Logger.info(AppMessages.greeting());
        new Thread(SecurityService::init).start();
    }

    public static void stop() {
        SecurityService.stop();
        Logger.info(AppMessages.goodbye());
    }
}
