package com.github.andreidore.smartbillclient;

public class Warehouse {

    public enum Type {

	EN_GROS, EN_DETAIL;

    }

    private String name;
    private Type type;

    public Warehouse(String name, Type type) {
	this.name = name;
	this.type = type;
    }

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
