package com.xxl.job.core.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    private static HttpClient HTTP_CLIENT_INSTANCE = null;

    private static final CloseableHttpClient HTTP_CLIENT;

    public static final CloseableHttpAsyncClient HTTP_ASYNC_CLIENT;

    private static final RequestConfig REQUEST_CONFIG;

    static {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        // 总连接池数量
        connectionManager.setMaxTotal(150);
        // 可为每个域名设置单独的连接池数量
        //connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("xx.xx.xx.xx")), 80);
        // setConnectTimeout：设置建立连接的超时时间
        // setConnectionRequestTimeout：从连接池中拿连接的等待超时时间
        // setSocketTimeout：发出请求后等待对端应答的超时时间
        REQUEST_CONFIG = RequestConfig
                .custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000)
                .build();
        // 重试处理器，StandardHttpRequestRetryHandler
        HttpRequestRetryHandler retryHandler = new StandardHttpRequestRetryHandler();
        //同步http client
        HTTP_CLIENT = HttpClients.custom()
                .setMaxConnTotal(1000)
                .setMaxConnPerRoute(1000)
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(REQUEST_CONFIG)
                .setRetryHandler(retryHandler)
                .build();
        //异步http client
        HTTP_ASYNC_CLIENT = HttpAsyncClients
                .custom()
                .setMaxConnTotal(1000)
                .setMaxConnPerRoute(1000)
                .setDefaultRequestConfig(REQUEST_CONFIG)
                .build();
    }

    private static HttpClient getHttpClient(boolean enableProxy, String proxyHost, int proxyPort) {
        if (HTTP_CLIENT_INSTANCE == null) {

            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

            connectionManager.setMaxTotal(1000);
            connectionManager.setDefaultMaxPerRoute(100);

            // Create an HttpClient with the given custom dependencies and configuration.
            HTTP_CLIENT_INSTANCE = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .setProxy(enableProxy ? new HttpHost(proxyHost, proxyPort) : null)
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setExpectContinueEnabled(true)
                            .setConnectTimeout(6000)
                            .setConnectionRequestTimeout(6000)
                            .build())
                    .build();
        }
        return HTTP_CLIENT_INSTANCE;
    }

    public static String doGet(String uri) {
        return doGet(uri, null);
    }

    public static String doGet(String url, Map<String, String> requestParameterMap, Map<String, String> headerParameterMap, boolean httpProxyEnable, String httpProxyHost, int httpProxyPort) throws IOException, URISyntaxException {
        //
        HttpClient httpClient = getHttpClient(httpProxyEnable, httpProxyHost, httpProxyPort);

        URIBuilder uriBuilder = new URIBuilder(new URI(url));

        if (MapUtils.isNotEmpty(requestParameterMap)) {
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            requestParameterMap.forEach((key, value) -> {
                nameValuePairList.add(new BasicNameValuePair(key, value));
            });
            uriBuilder.addParameters(nameValuePairList);
        }

        final HttpGet httpRequest = new HttpGet(uriBuilder.build());

        httpRequest.setHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_16_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36");
        if (MapUtils.isNotEmpty(headerParameterMap)) {
            headerParameterMap.forEach(httpRequest::setHeader);
        }

        return httpClient.execute(httpRequest, response -> {
            HttpEntity entity = response.getEntity();
            String resultString = null;
            if (null != entity) {
                resultString = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            return resultString;
        });
    }

    public static String doGet(String uri, Map<String, String> params) {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            if (null != params && !params.isEmpty()) {
                List<NameValuePair> list = new ArrayList<>();
                for (Map.Entry<String, String> param : params.entrySet()) {
                    list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                uriBuilder.setParameters(list);
            }
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            response = HTTP_CLIENT.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity, "utf-8");
                }
            }
        } catch (Exception e) {
            System.out.printf("CloseableHttpClient-get-请求异常, %s \n", e.getMessage());
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String doGetPrice(String uri) {
        // 创建 URL 对象
        try {
            URL url = new URL(uri);
//            // 创建代理对象
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 7890));

            // 打开连接，并指定代理
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 禁用缓存
            connection.setUseCaches(false);
            // 设置请求方法
            connection.setRequestMethod("GET");

            // 设置请求头
            connection.setRequestProperty("accept", "application/json");

            // 发送请求并获取响应代码
            int responseCode = connection.getResponseCode();

            // 如果响应代码为 200，则读取响应内容
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String doPost(String uri, byte[] bytes) {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(uri);
            HttpEntity httpEntity = new ByteArrayEntity(bytes);
            httpPost.setEntity(httpEntity);
            response = HTTP_CLIENT.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity, "utf-8");
                }
            }
        } catch (Exception e) {
            System.out.printf("CloseableHttpClient-post-请求异常, %s", e.getMessage());
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String doPost(String uri, Map<String, String> getParams, boolean httpProxyEnable, String httpProxyHost, int httpProxyPort) {
        HttpClient httpClient = getHttpClient(httpProxyEnable, httpProxyHost, httpProxyPort);
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(uri);
            if (null != getParams && !getParams.isEmpty()) {
                List<NameValuePair> list = new ArrayList<>();
                for (Map.Entry<String, String> param : getParams.entrySet()) {
                    list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                HttpEntity httpEntity = new UrlEncodedFormEntity(list, "utf-8");
                httpPost.setEntity(httpEntity);
            }
            response = (CloseableHttpResponse) httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity, "utf-8");
                }
            }
        } catch (Exception e) {
            System.out.printf("CloseableHttpClient-post-请求异常, %s", e.getMessage());
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String doPost(String uri, String json) {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(uri);

            // 将 JSON 字符串转换为字节数组
            byte[] bytes = json.getBytes("UTF-8");
            HttpEntity httpEntity = new ByteArrayEntity(bytes);

            // 设置请求实体
            httpPost.setEntity(httpEntity);

            // 执行请求
            response = HTTP_CLIENT.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity, "utf-8");
                }
            }
        } catch (Exception e) {
            System.out.printf("CloseableHttpClient-post-请求异常, %s", e.getMessage());
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
