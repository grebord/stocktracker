package com.stocktracker.javafx.controller;

import com.stocktracker.base.entity.dto.BondDto;
import com.stocktracker.base.service.SecurityService;
import com.stocktracker.javafx.model.BondPeekModel;
import com.stocktracker.javafx.view.BondPeekViewBuilder;

import javafx.concurrent.Task;
import javafx.scene.Node;

final class BondPeekController {
    
    private final BondPeekModel model;
    private final BondPeekViewBuilder viewBuilder;

    BondPeekController() {
        model = new BondPeekModel();
        viewBuilder = new BondPeekViewBuilder(model);
    }

    Node createView() {
        return viewBuilder.build();
    }

    void loadBonds() {
        final Task<BondDto> loadTask = new Task<BondDto>() {
            @Override
            protected BondDto call() {
                return SecurityService.getBondData();
            }
            @Override
            protected void succeeded() {
                BondDto bondDto = this.getValue();
                model.getPriceAl30Property().setValue(bondDto.getPriceAl30());
                model.getPriceAl30dProperty().setValue(bondDto.getPriceAl30d());
                model.getChangeAl30dProperty().setValue(bondDto.getChangeAl30d());
                model.getChangeGd30dProperty().setValue(bondDto.getChangeGd30d());
            }
        };
        new Thread(loadTask).start();
    }
}
