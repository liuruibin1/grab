package com.grab.domain.enumer;

import java.util.Random;

public enum RpcEndpoint {
    ENDPOINT_1("https://go.getblock.io/924b353b6d024e75a0b85473dbe1b52d"),
    ENDPOINT_2("https://go.getblock.io/029f86aa02074202938719a3bf3f5ebe"),
    ENDPOINT_3("https://go.getblock.io/4d3b68a79d154bd487689e193f89bb07"),
    ENDPOINT_4("https://go.getblock.io/9d3ef7d157f045a4bb24faaf12745f24"),
    ENDPOINT_5("https://go.getblock.io/d991bc1a24834b77b1e834693a134958"),
    ENDPOINT_6("https://go.getblock.io/abb221f1d5a944dfb7337c821e5ea92b");

    private final String url;

    RpcEndpoint(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static RpcEndpoint getRandomEndpoint() {
        RpcEndpoint[] endpoints = RpcEndpoint.values();
        int randomIndex = new Random().nextInt(endpoints.length);
        return endpoints[randomIndex];
    }
}
