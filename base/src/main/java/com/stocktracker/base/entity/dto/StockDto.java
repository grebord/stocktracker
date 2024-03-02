package com.stocktracker.base.entity.dto;

import com.stocktracker.base.entity.Security;

public final class StockDto {

    private final String symbol;
    private double priceUnderl;
    private double priceUnderlRatio;
    private double changeUnderl;
    private double priceCedear;
    private double priceCedearDollar;
    private double priceCedeus;

    public StockDto(String symbol) {
        this.symbol = symbol;
    }
    
    public StockDto(Security usStock, double ratio, Security arsCedear, double mepValue, Security usdCedear) {
        this.symbol = usStock.getSymbol();
        this.priceUnderl = usStock.getLastPrice();
        this.priceUnderlRatio = usStock.getLastPrice() / ratio;
        this.changeUnderl = usStock.getChangePercent();
        this.priceCedear = arsCedear.getLastPrice();
        this.priceCedearDollar = arsCedear.getLastPrice() / mepValue;
        this.priceCedeus = usdCedear.getLastPrice();
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPriceUnderl() {
        return priceUnderl;
    }

    public double getPriceUnderlRatio() {
        return priceUnderlRatio;
    }

    public double getChangeUnderl() {
        return changeUnderl;
    }

    public double getPriceCedear() {
        return priceCedear;
    }

    public double getPriceCedearDollar() {
        return priceCedearDollar;
    }

    public double getPriceCedeus() {
        return priceCedeus;
    }
}
