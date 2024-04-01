package com.xxl.job.executor.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {
    public static void main(String[] args) {
        try {
            System.setProperty("http.proxyHost", "127.0.0.1");
            System.setProperty("http.proxyPort", "7890");
            System.setProperty("https.proxyHost", "127.0.0.1");
            System.setProperty("https.proxyPort", "7890");
            // CoinMarketCap API密钥
            String apiKey = "724b59d6-6835-45ab-b7f1-70a861c640a1";
            // 货币对和时间间隔
            String symbol = "BTCUSD";
            String interval = "1h";
            // 构建API请求URL
            String apiUrl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/ohlcv/historical";
            apiUrl += "?symbol=" + symbol + "&interval=" + interval;
            // 创建HTTP连接
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // 设置请求头部
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("X-CMC_PRO_API_KEY", apiKey);
            // 发送请求并获取响应
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // 打印响应
            System.out.println(response.toString());
            // 解析响应并提取K线图数据
            // TODO: 解析JSON数据
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
