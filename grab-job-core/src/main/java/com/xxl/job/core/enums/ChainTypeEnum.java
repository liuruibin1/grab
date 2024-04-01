package com.xxl.job.core.enums;

import java.io.Serializable;

public enum ChainTypeEnum implements Serializable {

    BTC("BTC"),

    ETHEREUM("ETHEREUM"),

    TRON("TRON"),

    BSC("BSC");

    private String name;

    ChainTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
