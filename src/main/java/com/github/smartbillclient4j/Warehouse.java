package com.github.smartbillclient4j;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Warehouse {

    public enum Type {
        @JsonProperty("en gros")
        EN_GROS, @JsonProperty("en detail")
        EN_DETAIL;

    }

    @JsonAlias("warehouseName")
    private String name;

    @JsonAlias("warehouseType")
    private Type type;


    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Warehouse [name=" + name + ", type=" + type + "]";
    }

}
