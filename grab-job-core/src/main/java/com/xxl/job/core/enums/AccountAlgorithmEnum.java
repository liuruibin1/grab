package com.xxl.job.core.enums;

import java.io.Serializable;

public enum AccountAlgorithmEnum implements Serializable {

    ECDSA("ECDSA"),

    BTC_ECDSA("BTC_ECDSA"),

    TRON_ECDSA("TRON_ECDSA");

    private String value;

    AccountAlgorithmEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
