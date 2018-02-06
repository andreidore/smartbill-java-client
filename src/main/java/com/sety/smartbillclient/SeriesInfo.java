package com.sety.smartbillclient;

public class SeriesInfo {

    enum SeriesType {

	INVOICE("f"), PROFORMA("p");

	private String value;

	SeriesType(String value) {
	    this.value = value;
	}

	public String getValue() {
	    return value;
	}

	public static SeriesType fromString(String text) {
	    for (SeriesType b : SeriesType.values()) {
		if (b.value.equalsIgnoreCase(text)) {
		    return b;
		}
	    }
	    return null;
	}

    }

    private String name;

    private long nextNumber;

    private SeriesType type;

    public SeriesInfo(String name, long nextNumber, SeriesType type) {
	this.name = name;
	this.nextNumber = nextNumber;
	this.type = type;
    }

    public String getName() {
	return name;
    }

    public long getNextNumber() {
	return nextNumber;
    }

    public SeriesType getType() {
	return type;
    }

    @Override
    public String toString() {
	return "SeriesInfo [name=" + name + ", nextNumber=" + nextNumber + ", type=" + type + "]";
    }

}
