package com.stocktracker.base.entity;

import java.util.Currency;
import java.util.Objects;

public final class Security {

    private String symbol;
    private SecurityType type;
    private Currency currency;
    private double lastPrice;
    private double askPrice;
    private double bidPrice;
    private double previousClosePrice;
    private double changePercent;
    private int askSize;
    private int bidSize;
    private boolean empty = false;

    public Security() {
        this.empty = true;
    }

    public Security(BymaSecurity bymaSecurity) {
        this.symbol = Objects.requireNonNull(bymaSecurity.getSymbol()) + ".BA";
        this.type = "GO".equals(bymaSecurity.getSecurityType()) ? SecurityType.BOND : SecurityType.STOCK;
        this.currency = Currency.getInstance(bymaSecurity.getDenominationCcy());
        this.lastPrice = bymaSecurity.getTrade();
        this.askPrice = bymaSecurity.getOfferPrice();
        this.bidPrice = bymaSecurity.getBidPrice();
        this.previousClosePrice = bymaSecurity.getPreviousClosingPrice();
        this.changePercent = bymaSecurity.getPreviousClosingPrice() == 0 ? 0 :
                (bymaSecurity.getTrade() / bymaSecurity.getPreviousClosingPrice() - 1) * 100;
        this.askSize = bymaSecurity.getQuantityOffer();
        this.bidSize = bymaSecurity.getQuantityBid();
    }

    public Security(YahooSecurity yahooSecurity) {
        this.symbol = Objects.requireNonNull(yahooSecurity.getSymbol());
        this.type = SecurityType.STOCK;
        this.currency = Currency.getInstance(yahooSecurity.getCurrency());
        this.lastPrice = yahooSecurity.getRegularMarketPrice();
        this.askPrice = yahooSecurity.getAsk();
        this.bidPrice = yahooSecurity.getBid();
        this.previousClosePrice = yahooSecurity.getRegularMarketPreviousClose();
        this.changePercent = yahooSecurity.getRegularMarketChangePercent();
        this.askSize = yahooSecurity.getAskSize();
        this.bidSize = yahooSecurity.getBidSize();
    }

    public String getSymbol() {
        return symbol;
    }

    public SecurityType getType() {
        return type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public double getPreviousClosePrice() {
        return previousClosePrice;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public int getAskSize() {
        return askSize;
    }

    public int getBidSize() {
        return bidSize;
    }

    public boolean isEmpty() {
        return empty;
    }

    @Override
    public String toString() {
        return "Security [symbol=" + symbol + ", type=" + type + ", currency=" + currency + ", lastPrice=" + lastPrice
                + ", askPrice=" + askPrice + ", bidPrice=" + bidPrice + ", previousClosePrice=" + previousClosePrice
                + ", changePercent=" + changePercent + ", askSize=" + askSize + ", bidSize=" + bidSize + "]";
    }
}
