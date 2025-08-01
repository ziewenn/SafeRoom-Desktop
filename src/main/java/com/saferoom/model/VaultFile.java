package com.saferoom.model;

public class VaultFile {
    private final String name;
    private final String size;
    private final String date;

    public VaultFile(String name, String size, String date) {
        this.name = name;
        this.size = size;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getDate() {
        return date;
    }
}
