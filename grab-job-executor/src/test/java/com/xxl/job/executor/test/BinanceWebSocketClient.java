package com.xxl.job.executor.test;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;

public class BinanceWebSocketClient extends WebSocketClient {

    public BinanceWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to Binance Kline WebSocket");
        // 发送订阅消息
        String subscriptionMessage = "{\"method\":\"SUBSCRIBE\",\"params\":[\"btcusdt@kline_1m\"],\"id\":1}";
        send(subscriptionMessage);
    }

    @Override
    public void onMessage(String message) {
        // 处理接收到的消息，这里应该是K线数据的处理逻辑
        System.out.println("Received message: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed, code: " + code + ", reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("Error occurred: " + ex);
    }

    public static void main(String[] args) throws URISyntaxException {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        System.setProperty("https.proxyHost", "localhost");
        System.setProperty("https.proxyPort", "7890");
        String wsUrl = "wss://stream.binance.com/ws/btcusdt@kline_1m";
        BinanceWebSocketClient client = new BinanceWebSocketClient(new URI(wsUrl));
        client.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 7890)));
        client.connect();
    }
}

