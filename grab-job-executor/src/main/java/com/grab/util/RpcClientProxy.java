package com.grab.util;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import okhttp3.*;
import org.p2p.solanaj.rpc.Cluster;
import org.p2p.solanaj.rpc.RpcApi;
import org.p2p.solanaj.rpc.RpcClient;
import org.p2p.solanaj.rpc.RpcException;
import org.p2p.solanaj.rpc.types.RpcRequest;
import org.p2p.solanaj.rpc.types.RpcResponse;
import org.springframework.beans.factory.annotation.Value;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

public class RpcClientProxy extends RpcClient {

    @Value("${app.http.proxy.host}")
    public String proxyHost = "localhost";

    @Value("${app.http.proxy.port}")
    public int proxyPort= 7890;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient httpClient;

    private RpcApi rpcApi;

    public RpcClientProxy(boolean httpProxyEnable, Cluster endpoint) {
        super(endpoint.getEndpoint());
        initializeHttpClient(httpProxyEnable);
    }

    public RpcClientProxy(boolean httpProxyEnable,String endpoint) {
        super(endpoint);
        initializeHttpClient(httpProxyEnable);
    }

    private void initializeHttpClient(boolean httpProxyEnable) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (httpProxyEnable) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            clientBuilder.proxy(proxy);
        }
        httpClient = clientBuilder.build();
        rpcApi = getApi();
    }

    public <T> T call(String method, List<Object> params, Class<T> clazz) throws RpcException {
        RpcRequest rpcRequest = new RpcRequest(method, params);

        JsonAdapter<RpcRequest> rpcRequestJsonAdapter = new Moshi.Builder().build().adapter(RpcRequest.class);
        JsonAdapter<RpcResponse<T>> resultAdapter = new Moshi.Builder().build()
                .adapter(Types.newParameterizedType(RpcResponse.class, Type.class.cast(clazz)));

        Request request = new Request.Builder().url(getEndpoint())
                .post(RequestBody.create(rpcRequestJsonAdapter.toJson(rpcRequest), JSON)).build();

        try {
            Response response = httpClient.newCall(request).execute();
            final String result = response.body().string();
            // System.out.println("Response = " + result);
            RpcResponse<T> rpcResult = resultAdapter.fromJson(result);

            if (rpcResult.getError() != null) {
                throw new RpcException(rpcResult.getError().getMessage());
            }

            return (T) rpcResult.getResult();
        } catch (SSLHandshakeException e) {
            this.httpClient = new OkHttpClient.Builder().build();
            throw new RpcException(e.getMessage());
        } catch (IOException e) {
            throw new RpcException(e.getMessage());
        }
    }
}
