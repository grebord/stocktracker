package com.stocktracker.javafx.config;

import java.util.ResourceBundle;

public final class AppFxMessages {

    private static final String RES_MESSAGES = "messages-fx";
    private static String usageHelp;

    private AppFxMessages() { }

    public static synchronized void initialize() {
        ResourceBundle messages = ResourceBundle.getBundle(RES_MESSAGES);
        usageHelp = messages.getString("app.usagehelp");
    }

    public static String usageHelp() {
        return usageHelp;
    }
}
