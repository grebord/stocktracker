package com.stocktracker.base.entity;

public final class BymaSecurity {

    private String symbol;
    private String denominationCcy;
    private String securityType;
    private double offerPrice;
    private double previousClosingPrice;
    private double bidPrice;
    private double trade;
    private int quantityBid;
    private int quantityOffer;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDenominationCcy() {
        return denominationCcy;
    }

    public void setDenominationCcy(String denominationCcy) {
        this.denominationCcy = denominationCcy;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(double offerPrice) {
        this.offerPrice = offerPrice;
    }

    public double getPreviousClosingPrice() {
        return previousClosingPrice;
    }

    public void setPreviousClosingPrice(double previousClosingPrice) {
        this.previousClosingPrice = previousClosingPrice;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public double getTrade() {
        return trade;
    }

    public void setTrade(double trade) {
        this.trade = trade;
    }

    public int getQuantityBid() {
        return quantityBid;
    }

    public void setQuantityBid(int quantityBid) {
        this.quantityBid = quantityBid;
    }

    public int getQuantityOffer() {
        return quantityOffer;
    }

    public void setQuantityOffer(int quantityOffer) {
        this.quantityOffer = quantityOffer;
    }

    @Override
    public String toString() {
        return "BymaSecurity [symbol=" + symbol + ", denominationCcy=" + denominationCcy + ", securityType="
                + securityType + ", offerPrice=" + offerPrice + ", previousClosingPrice=" + previousClosingPrice
                + ", bidPrice=" + bidPrice + ", trade=" + trade + ", quantityBid=" + quantityBid + ", quantityOffer="
                + quantityOffer + "]";
    }
}
