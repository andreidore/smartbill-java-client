package com.github.andreidore.smartbillclient;

import java.math.BigDecimal;

public class StockProduct {

    private String unit;

    private String name;

    private String code;

    private BigDecimal quantity;

    public StockProduct(String name, String code, String unit, BigDecimal quantity) {
	this.name = name;
	this.code = code;
	this.unit = unit;
	this.quantity = quantity;
    }

    public String getUnit() {
	return unit;
    }

    public String getName() {
	return name;
    }

    public String getCode() {
	return code;
    }

    public BigDecimal getQuantity() {
	return quantity;
    }

    @Override
    public String toString() {
	return "StockProduct [unit=" + unit + ", name=" + name + ", code=" + code + ", quantity=" + quantity + "]";
    }
    
    

}
