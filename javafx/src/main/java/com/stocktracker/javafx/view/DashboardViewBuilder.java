package com.stocktracker.javafx.view;

import com.stocktracker.javafx.model.DashboardModel;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public final class DashboardViewBuilder implements Builder<Parent> {

    private static final String DASHBOARD_STYLECLASS = "dashboard";
    private static final String BONDBAR_STYLECLASS = "bond-bar";
    private static final String STOCKPANE_STYLECLASS = "stock-pane";

    private final DashboardModel model;
    
    public DashboardViewBuilder(DashboardModel model) {
        this.model = model;
    }

    @Override
    public Parent build() {
        Node stockPane = createStockPane();
        VBox.setVgrow(stockPane, Priority.ALWAYS);
        VBox result = new VBox(createBondBar(), new Separator(), stockPane);
        result.getStyleClass().add(DASHBOARD_STYLECLASS);
        return result;
    }
    
    private Node createBondBar() {
        HBox bondBar = new HBox();
        Bindings.bindContent(bondBar.getChildren(), model.getBondNodeProperty());
        bondBar.getStyleClass().add(BONDBAR_STYLECLASS);
        return bondBar;
    }

    private Node createStockPane() {
        TilePane tilePane = new TilePane();
        Bindings.bindContent(tilePane.getChildren(), model.getStockNodeProperty());
        ScrollPane scrollPane = new ScrollPane(tilePane);
        scrollPane.getStyleClass().add(STOCKPANE_STYLECLASS);
        return scrollPane;
    }
}
