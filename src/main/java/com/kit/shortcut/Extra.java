package com.kit.shortcut;

import com.google.gson.annotations.SerializedName;

public class Extra {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @SerializedName("name")
    String name;

    @SerializedName("value")
    Object value;



}