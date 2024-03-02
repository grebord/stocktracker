package com.stocktracker.javafx.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class StockTileModel {

    private final StringProperty symbol = new SimpleStringProperty("...");
    private final DoubleProperty priceUnderl = new SimpleDoubleProperty(0.0);
    private final DoubleProperty changeUnderl = new SimpleDoubleProperty(0.0);
    private final DoubleProperty priceUnderlRatio = new SimpleDoubleProperty(0.0);
    private final DoubleProperty priceCedearDollar = new SimpleDoubleProperty(0.0);
    private final DoubleProperty priceCedeus = new SimpleDoubleProperty(0.0);
    private final DoubleBinding markupCedearDollar = Bindings //
            .when(Bindings.and(priceUnderlRatio.greaterThan(0.0), priceCedearDollar.greaterThan(0.0)))
            .then(priceCedearDollar.divide(priceUnderlRatio).subtract(1.0).multiply(100.0))
            .otherwise(0.0);
    private final DoubleBinding markupCedeusUnderl = Bindings //
            .when(Bindings.and(priceUnderlRatio.greaterThan(0.0), priceCedeus.greaterThan(0.0)))
            .then(priceCedeus.divide(priceUnderlRatio).subtract(1.0).multiply(100.0))
            .otherwise(0.0);
    private final DoubleBinding markupCedeusCedear = Bindings //
            .when(Bindings.and(priceCedearDollar.greaterThan(0.0), priceCedeus.greaterThan(0.0)))
            .then(priceCedeus.divide(priceCedearDollar).subtract(1.0).multiply(100.0))
            .otherwise(0.0);

    public StringProperty getSymbol() {
        return symbol;
    }

    public DoubleProperty getPriceUnderl() {
        return priceUnderl;
    }

    public DoubleProperty getChangeUnderl() {
        return changeUnderl;
    }

    public DoubleProperty getPriceUnderlRatio() {
        return priceUnderlRatio;
    }

    public DoubleProperty getPriceCedearDollar() {
        return priceCedearDollar;
    }

    public DoubleProperty getPriceCedeus() {
        return priceCedeus;
    }

    public DoubleBinding getMarkupCedearDollar() {
        return markupCedearDollar;
    }

    public DoubleBinding getMarkupCedeusUnderl() {
        return markupCedeusUnderl;
    }

    public DoubleBinding getMarkupCedeusCedear() {
        return markupCedeusCedear;
    }
}
