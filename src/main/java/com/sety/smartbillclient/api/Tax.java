package com.sety.smartbillclient.api;

import java.math.BigDecimal;

public class Tax {

    private String name;

    private BigDecimal percentage;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
	return "Tax [name=" + name + ", percentage=" + percentage + "]";
    }
    
    

    

}
