package com.stocktracker.javafx.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public final class BondPeekModel {
    
    private final DoubleProperty priceAl30 = new SimpleDoubleProperty(0.0);
    private final DoubleProperty priceAl30d = new SimpleDoubleProperty(0.0);
    private final DoubleProperty changeAl30d = new SimpleDoubleProperty(0.0);
    private final DoubleProperty changeGd30d = new SimpleDoubleProperty(0.0);
    private final DoubleBinding mepValue = Bindings //
            .when(priceAl30d.greaterThan(0.0))
            .then(priceAl30.divide(priceAl30d))
            .otherwise(0.0);

    public DoubleProperty getPriceAl30Property() {
        return priceAl30;
    }

    public DoubleProperty getPriceAl30dProperty() {
        return priceAl30d;
    }

    public DoubleProperty getChangeAl30dProperty() {
        return changeAl30d;
    }

    public DoubleProperty getChangeGd30dProperty() {
        return changeGd30d;
    }

    public DoubleBinding getMepValueBinding() {
        return mepValue;
    }
}
