package com.stocktracker.javafx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stocktracker.javafx.model.BondPeekModel;

@ExtendWith(MockitoExtension.class)
class BondPeekModelTest {

    @Test
    void testInit() {
        BondPeekModel bondPeekModel = new BondPeekModel();
        Assertions.assertEquals(0.0, bondPeekModel.getMepValueBinding().doubleValue());
    }

    @Test
    void testLoad() {
        BondPeekModel bondPeekModel = new BondPeekModel();
        bondPeekModel.getPriceAl30Property().setValue(7.0);
        bondPeekModel.getPriceAl30dProperty().setValue(2.0);
        Assertions.assertEquals(3.5, bondPeekModel.getMepValueBinding().doubleValue());
    }
}
