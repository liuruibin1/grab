package com.grab.util;

import com.grab.domain.MintToken;
import com.grab.domain.enumer.RpcEndpoint;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.rpc.Cluster;
import org.p2p.solanaj.rpc.RpcException;
import org.p2p.solanaj.rpc.types.TokenResultObjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolClientUtil {

    // 设置最大重试次数
    private static final int MAX_RETRIES = 3;

    public static PublicKey getTokenAccountsByOwner(PublicKey owner, PublicKey tokenMint) {
        RpcEndpoint randomEndpoint = RpcEndpoint.getRandomEndpoint();
        RpcClientProxy client = new RpcClientProxy(randomEndpoint.getUrl());
        List<Object> params = new ArrayList<>();
        params.add(owner.toBase58());

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("mint", tokenMint.toBase58());
        params.add(parameterMap);
        params.add(Map.of("encoding", "jsonParsed"));
        PublicKey tokenAccountKey = null;
        try {
            Map<String, Object> rawResult = client.call("getTokenAccountsByOwner", params, Map.class);
            if (null != rawResult) {
                if (null != rawResult.get("value") && ((List) rawResult.get("value")).size() > 0) {
                    String base58 = (String) ((Map) ((List) rawResult.get("value")).get(0)).get("pubkey");
                    tokenAccountKey = new PublicKey(base58);
                }
            }

        } catch (Exception ex) {
            return null;
        }

        return tokenAccountKey;
    }

    public static BigDecimal getTokenBalance(PublicKey accountPublicKey, MintToken mintToken, RpcClientProxy client) {
        try {
            PublicKey tokenKey = SolClientUtil.getTokenAccountsByOwner(accountPublicKey, new PublicKey(mintToken.getAddress()));
            if (tokenKey != null) {
                TokenResultObjects.TokenAmountInfo tokenAmountInfo = client.getApi().getTokenAccountBalance(tokenKey);
                String amount = tokenAmountInfo.getUiAmountString();
                return new BigDecimal(amount);
            }
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }

    public static long getBalanceWithRetries(RpcClientProxy client, PublicKey accountPublicKey) throws InterruptedException, RpcException {
        int retryCount = 0;
        while (retryCount < MAX_RETRIES) {
            try {
                return client.getApi().getBalance(accountPublicKey);
            } catch (Exception e) {
                // 如果发生异常，则进行重试
                retryCount++;
                if (retryCount >= MAX_RETRIES) {
                    // 如果达到最大重试次数仍然失败，则抛出异常或返回错误结果
                    throw e;
                } else {
                    // 等待一段时间后进行重试
                    Thread.sleep(1000); // 1秒后重试
                }
            }
        }
        return 0;
    }

    public static BigDecimal getTokenBalanceWithRetries(RpcClientProxy client, PublicKey accountPublicKey, MintToken mintToken) throws InterruptedException {
        int retryCount = 0;
        while (retryCount < MAX_RETRIES) {
            try {
                return SolClientUtil.getTokenBalance(accountPublicKey, mintToken, client);
            } catch (Exception e) {
                // 如果发生异常，则进行重试
                retryCount++;
                if (retryCount >= MAX_RETRIES) {
                    // 如果达到最大重试次数仍然失败，则抛出异常或返回错误结果
                    throw e;
                } else {
                    // 等待一段时间后进行重试
                    Thread.sleep(1000); // 1秒后重试
                }
            }
        }
        return BigDecimal.ZERO;
    }

    public static void main(String[] args) {
        RpcClientProxy client = new RpcClientProxy(Cluster.MAINNET);
        PublicKey accountPublicKey = new PublicKey("J1S9H3QjnRtBbbuD4HjPV6RpRhwuk4zKbxsnCHuTgh9w");
        try {
            long balance = client.getApi().getBalance(accountPublicKey);
            BigDecimal solBalance = BigDecimal.valueOf(balance)
                    .divide(BigDecimal.TEN.pow(9));
            PublicKey tokenKey = SolClientUtil.getTokenAccountsByOwner(accountPublicKey, new PublicKey("TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA"));
            if (null != tokenKey) {
                TokenResultObjects.TokenAmountInfo tokenAmountInfo = client.getApi().getTokenAccountBalance(tokenKey);
                String amount = tokenAmountInfo.getUiAmountString();
                BigDecimal balanceTokens = BigDecimal.valueOf(Long.parseLong(amount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
