package com.github.smartbillclient4j;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonAlias;

public class Product {

    @JsonAlias("productName")
    private String name;

    @JsonAlias("productCode")
    private String code;

    @JsonAlias("measuringUnit")
    private String unit;

    private BigDecimal quantity;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "{" + " name='" + getName() + "'" + ", code='" + getCode() + "'" + ", unit='"
                + getUnit() + "'" + ", quantity='" + getQuantity() + "'" + "}";
    }



}
