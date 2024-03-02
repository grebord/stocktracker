package com.stocktracker.javafx;

import java.util.List;
import java.util.regex.Pattern;

import org.tinylog.Logger;

import com.stocktracker.javafx.config.AppFxMessages;
import com.stocktracker.javafx.controller.DashboardController;
import com.stocktracker.base.App;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class AppFx extends Application {

    private static final String STAGE_TITLE = "Stock Tracker";
    private static final String BASE_SCENE_CSS = "/javafx-base.css";
    private static final int STAGE_MIN_WIDTH = 800;
    private static final int STAGE_MIN_HEIGHT = 480;
    private static final Pattern argPattern = Pattern.compile("^[A-Z]{2,6}:(?!0{1,4})\\d{1,4}$");

    @Override
    public void init() {
        AppFxMessages.initialize();
        validateArgs(this.getParameters().getUnnamed());
        boolean useApi = "true".equals(this.getParameters().getNamed().get("useApi"));
        App.init(useApi);
    }

    @Override
    public void stop() {
        App.stop();
    }
    
    @Override
    public void start(Stage stage) {
        stage.setTitle(STAGE_TITLE);
        stage.setMinWidth(STAGE_MIN_WIDTH);
        stage.setMinHeight(STAGE_MIN_HEIGHT);
        DashboardController mainController = new DashboardController();
        stage.setScene(createScene(mainController.createView()));
        stage.show();
        mainController.createAndLoadStock(this.getParameters().getUnnamed());
        mainController.createAndLoadBonds();
    }

    private static Scene createScene(Parent root) {
        Scene scene = new Scene(root);
        String style = AppFx.class.getResource(BASE_SCENE_CSS).toExternalForm();
        scene.getStylesheets().add(style);
        return scene;
    }

    private static void validateArgs(List<String> args) {
        Logger.debug("args: {}", String.join(" ", args));
        args.forEach(arg -> arg = arg.trim().toUpperCase());
        if (args.isEmpty() || args.stream().anyMatch(arg -> !argPattern.matcher(arg).matches())) {
            Logger.warn(AppFxMessages.usageHelp());
            Platform.exit();
        }
    }
}
