package com.github.andreidore.smartbillclient;

import java.math.BigDecimal;

public class Tax {

    private String name;

    private BigDecimal percentage;

    public Tax(String name, BigDecimal percentage) {
	this.name = name;
	this.percentage = percentage;
    }

    public String getName() {
	return name;
    }

    public BigDecimal getPercentage() {
	return percentage;
    }

    @Override
    public String toString() {
	return "Tax [name=" + name + ", percentage=" + percentage + "]";
    }

}
