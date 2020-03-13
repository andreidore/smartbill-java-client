package com.github.smartbillclient4j;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;

public class StockResponse extends Response {


    private String number;

    private String series;

    private String url;

    @JsonAlias("list")
    private List<Stock> stocks;


    public StockResponse() {
    }

    public List<Stock> getStocks() {
        return this.stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }



}
