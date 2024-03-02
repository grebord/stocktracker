package com.stocktracker.javafx.controller;

import com.stocktracker.base.entity.dto.StockDto;
import com.stocktracker.base.service.SecurityService;
import com.stocktracker.javafx.model.StockTileModel;
import com.stocktracker.javafx.view.StockTileViewBuilder;

import javafx.concurrent.Task;
import javafx.scene.Node;

final class StockTileController {
    
    private final StockTileModel model;
    private final StockTileViewBuilder viewBuilder;

    StockTileController() {
        model = new StockTileModel();
        viewBuilder = new StockTileViewBuilder(model);
    }

    Node createView() {
        return viewBuilder.build();
    }

    void loadSymbol(final String symbol) {
        final Task<StockDto> loadTask = new Task<StockDto>() {
            @Override
            protected StockDto call() {
                return SecurityService.getStockData(symbol);
            }
            @Override
            protected void succeeded() {
                StockDto securityDto = this.getValue();
                model.getSymbol().setValue(securityDto.getSymbol());
                model.getPriceUnderl().setValue(securityDto.getPriceUnderl());
                model.getChangeUnderl().setValue(securityDto.getChangeUnderl());
                model.getPriceUnderlRatio().setValue(securityDto.getPriceUnderlRatio());
                model.getPriceCedearDollar().setValue(securityDto.getPriceCedearDollar());
                model.getPriceCedeus().setValue(securityDto.getPriceCedeus());
            }
        };
        new Thread(loadTask).start();
    }
}
