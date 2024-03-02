package com.stocktracker.base.config;

import java.util.ResourceBundle;

public final class AppMessages {

    private static final String RES_MESSAGES = "messages";
    private static String greeting;
    private static String goodbye;
    private static String testingBase;

    private AppMessages() { }

    public static synchronized void initialize() {
        ResourceBundle messages = ResourceBundle.getBundle(RES_MESSAGES);
        greeting = messages.getString("app.greeting");
        goodbye = messages.getString("app.goodbye");
        testingBase = messages.getString("app.testingbase");
    }

    public static String greeting() {
        return greeting;
    }

    public static String goodbye() {
        return goodbye;
    }

    public static String testingBase() {
        return testingBase;
    }
}
