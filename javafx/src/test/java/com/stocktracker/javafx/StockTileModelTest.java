package com.stocktracker.javafx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stocktracker.javafx.model.StockTileModel;

@ExtendWith(MockitoExtension.class)
class StockTileModelTest {

    @Test
    void testInit() {
        StockTileModel stockTileModel = new StockTileModel();
        Assertions.assertEquals(0.0, stockTileModel.getMarkupCedearDollar().doubleValue());
        Assertions.assertEquals(0.0, stockTileModel.getMarkupCedeusUnderl().doubleValue());
        Assertions.assertEquals(0.0, stockTileModel.getMarkupCedeusCedear().doubleValue());
    }

    @Test
    void testLoadOne() {
        StockTileModel stockTileModel = new StockTileModel();
        stockTileModel.getPriceUnderlRatio().setValue(1.0);
        Assertions.assertEquals(0.0, stockTileModel.getMarkupCedearDollar().doubleValue());
    }

    @Test
    void testLoadBoth() {
        StockTileModel stockTileModel = new StockTileModel();
        stockTileModel.getPriceUnderlRatio().setValue(2.0);
        stockTileModel.getPriceCedeus().setValue(7.0);
        Assertions.assertEquals(250.0, stockTileModel.getMarkupCedeusUnderl().doubleValue());
    }
}
