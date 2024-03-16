package com.paylony.topwise.sdk.emv.database.table;

public class EmvParam {
    private int id;
    private String key;
    private String value;

    public EmvParam() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
