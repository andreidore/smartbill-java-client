package com.github.smartbillclient4j;

import java.math.BigDecimal;

public class Tax {

    private String name;

    private BigDecimal percentage;

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
