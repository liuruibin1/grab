package com.xxl.job.core.enums;

import java.io.Serializable;

public enum ChainInfoEnum implements Serializable {

    ETHEREUM("1878af79741334c4b7a2d7adc97d19ea", "ETH", 18, "Ethereum", true, ChainTypeEnum.ETHEREUM, AccountAlgorithmEnum.ECDSA, 1),

    SEPOLIA("521b300b3b8e3a268a78b6e1880b6911", "ETH", 18, "Sepolia", false, ChainTypeEnum.ETHEREUM, AccountAlgorithmEnum.ECDSA, 11155111),

    BSC("b6f6b4efd9393d1fa207d325ff1bbd60", "BNB", 18, "BSC", true, ChainTypeEnum.BSC, AccountAlgorithmEnum.ECDSA, 56),

    BTC("1c762234363a3edc90a2930d330db099", "BTC", 8, "BTC", true, ChainTypeEnum.BTC, AccountAlgorithmEnum.BTC_ECDSA, 8086),

    ARBITRUM_GOERLI("3f4a0473465334beaecab07046131c8c", "ArbitrumGoerli", 18, "ArbitrumGoerli", false, ChainTypeEnum.ETHEREUM, AccountAlgorithmEnum.ECDSA, 421613),

    ARBITRUM("78e75f8146493f88930ddd72d2e447a5", "Arbitrum", 18, "Arbitrum", false, ChainTypeEnum.ETHEREUM, AccountAlgorithmEnum.ECDSA, 42161);

    private String name;

    private boolean isProduction;

    private String chainInfoId;

    private String nativeTokenSymbol;

    private Integer nativeTokenDecimals;

    private ChainTypeEnum chainType;

    private AccountAlgorithmEnum accountAlgorithm;

    private Integer chainId;

    ChainInfoEnum(String chainInfoId, String nativeTokenSymbol, Integer nativeTokenDecimals, String name, boolean isProduction, ChainTypeEnum chainType, AccountAlgorithmEnum accountAlgorithm, Integer chainId) {
        this.chainInfoId = chainInfoId;
        this.nativeTokenSymbol = nativeTokenSymbol;
        this.nativeTokenDecimals = nativeTokenDecimals;
        this.name = name;
        this.isProduction = isProduction;
        this.chainType = chainType;
        this.accountAlgorithm = accountAlgorithm;
        this.chainId = chainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isProduction() {
        return isProduction;
    }

    public void setProduction(boolean production) {
        isProduction = production;
    }

    public String getChainInfoId() {
        return chainInfoId;
    }

    public void setChainInfoId(String chainInfoId) {
        this.chainInfoId = chainInfoId;
    }

    public String getNativeTokenSymbol() {
        return nativeTokenSymbol;
    }

    public void setNativeTokenSymbol(String nativeTokenSymbol) {
        this.nativeTokenSymbol = nativeTokenSymbol;
    }

    public Integer getNativeTokenDecimals() {
        return nativeTokenDecimals;
    }

    public void setNativeTokenDecimals(Integer nativeTokenDecimals) {
        this.nativeTokenDecimals = nativeTokenDecimals;
    }

    public ChainTypeEnum getChainType() {
        return chainType;
    }

    public void setChainType(ChainTypeEnum chainType) {
        this.chainType = chainType;
    }

    public AccountAlgorithmEnum getAccountAlgorithm() {
        return accountAlgorithm;
    }

    public void setAccountAlgorithm(AccountAlgorithmEnum accountAlgorithm) {
        this.accountAlgorithm = accountAlgorithm;
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public static ChainInfoEnum getByChainName(String name) {
        for (ChainInfoEnum enumerate : ChainInfoEnum.values()) {
            if (enumerate.getName().equals(name)) {
                return enumerate;
            }
        }
        return null;
    }

    public static ChainInfoEnum getByChainInfoId(String chainInfoId) {
        for (ChainInfoEnum enumerate : ChainInfoEnum.values()) {
            if (enumerate.getChainInfoId().equals(chainInfoId)) {
                return enumerate;
            }
        }
        return null;
    }

    public static ChainInfoEnum getByChainId(Integer chainId) {
        for (ChainInfoEnum enumerate : ChainInfoEnum.values()) {
            if (enumerate.getChainId().equals(chainId)) {
                return enumerate;
            }
        }
        return null;
    }
}