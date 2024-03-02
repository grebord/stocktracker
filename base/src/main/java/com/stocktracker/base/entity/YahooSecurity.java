package com.stocktracker.base.entity;

public final class YahooSecurity {

    private String symbol;
    private String currency;
    private String quoteType;
    private double ask;
    private double regularMarketPreviousClose;
    private double bid;
    private double regularMarketPrice;
    private double regularMarketChangePercent;
    private int bidSize;
    private int askSize;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getRegularMarketPreviousClose() {
        return regularMarketPreviousClose;
    }

    public void setRegularMarketPreviousClose(double regularMarketPreviousClose) {
        this.regularMarketPreviousClose = regularMarketPreviousClose;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getRegularMarketPrice() {
        return regularMarketPrice;
    }

    public void setRegularMarketPrice(double regularMarketPrice) {
        this.regularMarketPrice = regularMarketPrice;
    }

    public double getRegularMarketChangePercent() {
        return regularMarketChangePercent;
    }

    public void setRegularMarketChangePercent(double regularMarketChangePercent) {
        this.regularMarketChangePercent = regularMarketChangePercent;
    }

    public int getBidSize() {
        return bidSize;
    }

    public void setBidSize(int bidSize) {
        this.bidSize = bidSize;
    }

    public int getAskSize() {
        return askSize;
    }

    public void setAskSize(int askSize) {
        this.askSize = askSize;
    }

    @Override
    public String toString() {
        return "YahooSecurity [symbol=" + symbol + ", currency=" + currency + ", quoteType=" + quoteType + ", ask="
                + ask + ", regularMarketPreviousClose=" + regularMarketPreviousClose + ", bid=" + bid
                + ", regularMarketPrice=" + regularMarketPrice + ", regularMarketChangePercent="
                + regularMarketChangePercent + ", bidSize=" + bidSize + ", askSize=" + askSize + "]";
    }
}
