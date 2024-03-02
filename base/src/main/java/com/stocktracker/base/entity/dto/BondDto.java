package com.stocktracker.base.entity.dto;

import com.stocktracker.base.entity.Security;

public final class BondDto {

    private double priceAl30;
    private double priceAl30d;
    private double changeAl30d;
    private double changeGd30d;

    public BondDto() { }
    
    public BondDto(Security al30, Security al30d, Security gd30d) {
        if (al30.getLastPrice() <= 0.0) throw new IllegalArgumentException("non-positive AL30 price");
        if (al30d.getLastPrice() <= 0.0) throw new IllegalArgumentException("non-positive AL30D price");
        this.priceAl30 = al30.getLastPrice();
        this.priceAl30d = al30d.getLastPrice();
        this.changeAl30d = al30d.getChangePercent();
        this.changeGd30d = gd30d.getChangePercent();
    }

    public double getPriceAl30() {
        return priceAl30;
    }

    public double getPriceAl30d() {
        return priceAl30d;
    }

    public double getChangeAl30d() {
        return changeAl30d;
    }

    public double getChangeGd30d() {
        return changeGd30d;
    }
}
