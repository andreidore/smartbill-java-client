package com.github.andreidore.smartbillclient;

import java.util.List;

public class Stock {

    private List<StockProduct> products;

    private Warehouse warehouse;

    public Stock(Warehouse warehouse, List<StockProduct> products) {

	this.warehouse = warehouse;

	this.products = products;

    }

    public List<StockProduct> getProducts() {
	return products;
    }

    public Warehouse getWarehouse() {
	return warehouse;
    }

    
    
    
}
