package com.stocktracker.javafx.controller;

import java.util.List;

import com.stocktracker.javafx.model.DashboardModel;
import com.stocktracker.javafx.view.DashboardViewBuilder;

import javafx.scene.Parent;

public final class DashboardController {
    
    private final DashboardModel model;
    private final DashboardViewBuilder viewBuilder;
    
    public DashboardController() {
        model = new DashboardModel();
        viewBuilder = new DashboardViewBuilder(model);
    }

    public Parent createView() {
        return viewBuilder.build();
    }

    public void createAndLoadBonds() {
        BondPeekController bondPeekController = new BondPeekController();
        model.getBondNodeProperty().add(bondPeekController.createView());
        bondPeekController.loadBonds();
    }

    public void createAndLoadStock(final List<String> symbols) {
        for (String symbol : symbols) {
            StockTileController stockTileController = new StockTileController();
            model.getStockNodeProperty().add(stockTileController.createView());
            stockTileController.loadSymbol(symbol);
        }
    }
}
