package com.sety.smartbillclient.api;

public class SeriesInfo {

    private String name;

    private long nextNumber;

    private SeriesType type;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public long getNextNumber() {
	return nextNumber;
    }

    public void setNextNumber(long nextNumber) {
	this.nextNumber = nextNumber;
    }

    public SeriesType getType() {
	return type;
    }

    public void setType(SeriesType type) {
	this.type = type;
    }

    @Override
    public String toString() {
	return "SeriesInfo [name=" + name + ", nextNumber=" + nextNumber + ", type=" + type + "]";
    }
    
    

}
