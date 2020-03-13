package com.github.smartbillclient4j;

public class SeriesInfo {

    private String name;

    private long nextNumber;

    private DocumentType type;

    public SeriesInfo(String name, long nextNumber, DocumentType type) {
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

    public DocumentType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SeriesInfo [name=" + name + ", nextNumber=" + nextNumber + ", type=" + type + "]";
    }

}
