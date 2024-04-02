package com.grab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grab.domain.DTO.MintTokenDTO;
import com.grab.domain.DTO.ResponseResult;
import com.grab.domain.MintToken;
import com.grab.mapper.MintTokenMapper;
import com.grab.service.MintTokenService;
import com.grab.util.RpcClientProxy;
import com.xxl.job.core.util.HttpClientUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.rpc.types.TokenResultObjects;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author robinson
 * @date 2024-03-26
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Service
public class MintTokenServiceImpl extends ServiceImpl<MintTokenMapper, MintToken> implements MintTokenService {

    @Resource
    MintTokenMapper mapper;

    @Override
    public ResponseResult getMintToken() {
        List<MintToken> mintTokens = new ArrayList<>();
        mintTokens.add(new MintToken(101, "MEW1gQWJ3nEXg2qgERiKu7FAFj79PHvQVREQUzScPP5", "MEW", 5));
        mintTokens.add(new MintToken(101, "GNJrBUuUnExsQYBAFy22Hw1qSmSJdWEgy8VawAn3uzUb", "SOLAR", 9));
        mintTokens.add(new MintToken(101, "WskzsKqEW3ZsmrhPAevfVZb6PuuLzWov9mJWZsfDePC", "PUNDU", 9));
        mintTokens.add(new MintToken(101, "CcTxKZHqU4E2Tek5gh1GZoPZPHncGFkZ36EVMsgDn6sK", "REX", 6));
        mintTokens.add(new MintToken(101, "22513u2QwiY6xaJn7nVFWGKy3aBdw6WfZsRPW2RRtCKj", "HuntBoden", 6));
        mintTokens.add(new MintToken(101, "JUPyiwrYJFskUPiHa7hkeR8VUtAeFoSYbKedZNsDvCN", "JUP", 6));
        mintTokens.add(new MintToken(101, "8DBQQaX18ZWVx7LGpNiRKQVzuHKzL98Y3ESQJVkZxwNr", "GRUMPY", 6));
        mintTokens.add(new MintToken(101, "T38Q2VC68NJhTiD2f1gHJDj5M5RiXayctBFZCsHSaE4", "CLOUD", 6));
        mintTokens.add(new MintToken(101, "7cb67ev3jvBKaAH1tnVM8FL8WfQ96sa2TYByEXajqx8N", "DAW", 6));
        mintTokens.add(new MintToken(101, "Es9vMFrzaCERmJfrF4H2FYD4KCoNkY11McCe8BenwNYB", "USDT", 6));
        mintTokens.add(new MintToken(101, "EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v", "USDC", 6));
        mintTokens.add(new MintToken(101, "WENWENvqqNya429ubCdR81ZmD69brwQaaBYY6p3LCpk", "WEN", 5));
        mintTokens.add(new MintToken(101, "7BgBvyjrZX1YKz4oh9mjb8ZScatkkwb8DzFx7LoiVkM3", "SLERF", 9));
        mintTokens.add(new MintToken(101, "CxrhHSqyW8YTDWc4csJMMgo7uBUJSXzNzrWhtw9ULdru", "never", 6));
        mintTokens.add(new MintToken(101, "GtDZKAqvMZMnti46ZewMiXCa4oXF4bZxwQPoKzXPFxZn", "nub", 9));
        mintTokens.add(new MintToken(101, "2uvch6aviS6xE3yhWjVZnFrDw7skUtf6ubc7xYJEPpwj", "Lola", 9));
        mintTokens.add(new MintToken(101, "3sPtjXtgoP7SEH22QFET6oZF4yszPByMJBEPf3y2zx6Y", "8PEPE", 9));
        mintTokens.add(new MintToken(101, "3fZjsRcjkjBXrwpTczseu3q93o6dKnadsTdcXQpf4E2Z", "NALA", 6));
        mintTokens.add(new MintToken(101, "C8cNX2D1y3jqKpMFkQhP1gGbfvTEdeckZXLBKSN5z5KF", "Mail", 6));
        mintTokens.add(new MintToken(101, "86dDPpq4LAr5UAJA2toUcfPNxDBi2xE7PVCXTUnJKwNR", "LUHU", 9));
        mintTokens.add(new MintToken(101, "5cvj5rEEocG5Wvh3oJuY6MoYj7gVZd8aoXSLZjDY6W4W", "SCUM", 9));
        mintTokens.add(new MintToken(101, "FF4dN8Qy8NNF88HRgMA3TkbRVZ8PTXWXZCZJb59X3Sbg", "CAN", 9));
        mintTokens.add(new MintToken(101, "4k3Dyjzvzp8eMZWUXbBCjEvwSkkk59S5iCNLY3QrkX6R", "RAY", 6));
        mintTokens.add(new MintToken(101, "921MoB1U7VprQfWw5D37a38LCBgB3nareT7rNffk66BG", "MONKEY", 4));
        mintTokens.add(new MintToken(101, "3psH1Mj1f7yUfaD5gh6Zj7epE8hhrMkMETgv5TshQA4o", "boden", 9));
        mintTokens.add(new MintToken(101, "7hWcHohzwtLddDUG81H2PkWq6KEkMtSDNkYXsso18Fy3", "CAT", 3));
        mintTokens.add(new MintToken(101, "dsKYrpdQQ1sQfCArMqGby7GCCK6va7Ei5Vdy3UixDSW", "PTSD", 9));
        mintTokens.add(new MintToken(101, "2xt4ZC4WUxEABSRzvY4ZhFfLj5F8sQnS5bXRC7KPtnfo", "CHRIST", 9));
        mintTokens.add(new MintToken(101, "Bf6xK9vFfKqUW6844zHQz9oq689nDZqizugxT5patYBy", "$BlackRock", 8));
        mintTokens.add(new MintToken(101, "5hmf8Jt9puwoqiFQTb3vr22732ZTKYRLRw9Vo7tN3rcz", "BABY", 6));
        mintTokens.add(new MintToken(101, "SHDWyBxihqiCj6YekG2GUr7wqKLeLAMK1gHZck9pL6y", "SHDW", 9));
        mintTokens.add(new MintToken(101, "27G8MtK7VtTcCHkpASjSDdkWWYfoqT6ggEuKidVJidD4", "JLP", 6));
        mintTokens.add(new MintToken(101, "A3eME5CetyZPBoWbRUwY3tSe25S6tb18ba9ZPbWk9eFJ", "PENG", 6));
        mintTokens.add(new MintToken(101, "3rcwsZ86w1npjDBmXBL3XSxxK6TcwSPzXFSuCx2kTFP4", "SHIBASSO", 9));
        mintTokens.add(new MintToken(101, "node3SHFNF7h6N9jbztfVcXrZcvAJdns1xAV8CbYFLG", "LIGMA", 6));
        mintTokens.add(new MintToken(101, "947tEoG318GUmyjVYhraNRvWpMX7fpBTDQFBoJvSkSG3", "CHAT", 9));
        mintTokens.add(new MintToken(101, "HhJpBhRRn4g56VsyLuT8DL5Bv31HkXqsrahTTUCZeZg4", "$MYRO", 9));
        mintTokens.add(new MintToken(101, "FU1q8vJpZNUrmqsciSjp8bAKKidGsLmouB8CBdf8TKQv", "tremp", 9));
        mintTokens.add(new MintToken(101, "DezXAZ8z7PnrnRJjz3wXBoRgixCa6xjnB7YaB1pPB263", "Bonk", 5));
        mintTokens.add(new MintToken(101, "93HkTBvMHt7V9H7k4NC4DTGNWM9EpByjAoE7wwoCQJCc", "TWTR", 9));
        mintTokens.add(new MintToken(101, "5LafQUrVco6o7KMz42eqVEJ9LW31StPyGjeeu5sKoMtA", "MUMU", 6));
        mintTokens.add(new MintToken(101, "Av6qVigkb7USQyPXJkUvAEm4f599WTRvd75PUWBA9eNm", "COST", 9));
        mintTokens.add(new MintToken(101, "J1toso1uCk3RLmjorhTtrVwY9HJ7X8V9yYac6Y7kGCPn", "JitoSOL", 9));
        mintTokens.add(new MintToken(101, "5MBBsoCVddAuF8XixvCcXNbHAw6WfpZ8WyTKMmczxxRN", "SHIB", 9));
        mintTokens.add(new MintToken(101, "BNT4uhSStq1beFADv3cq4wQAVfWB392PjAaxTBpNeWxu", "MEDIA", 6));
        mintTokens.add(new MintToken(101, "ukHH6c7mMyiWCf1b9pnWe25TSpkDDt3H5pQZgZ74J82", "BOME", 6));
        mintTokens.add(new MintToken(101, "AVLhahDcDQ4m4vHM4ug63oh7xc8Jtk49Dm5hoe9Sazqr", "BANANA", 9));
        mintTokens.add(new MintToken(101, "9dLuVbJMd4ZpTpFgmaFHAGSsFwVjtcnzFWaLAA1expHg", "HEEHEE", 6));
        mintTokens.add(new MintToken(101, "7GCihgDB8fe6KNjn2MYtkzZcRjQy3t9GHdC8uHYmW2hr", "POPCAT", 9));
        mintTokens.add(new MintToken(101, "8KSiBaJvpwqzYFU7u8qLmFqiEAHkARDRkAnetFjEznZX", "ALBERT", 6));
        mintTokens.add(new MintToken(101, "0x", "SOL", 9));
        mintTokens.add(new MintToken(101, "21rweMLGYeMNonHW7H3xa5py17X6ZFRcHirCp9inRBQA", "IQ50", 9));
        mintTokens.add(new MintToken(101, "6gnCPhXtLnUD76HjQuSYPENLSZdG8RvDB1pTLM5aLSJA", "BSKT", 5));
        int effectRaw = batchDeleteAndInsert(mintTokens);
        if (effectRaw > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error("generate error");
    }

    @Override
    public ResponseResult generateUsdPrice() {
        //
        List<MintToken> mintTokens = list();
        List<MintTokenDTO> mintTokenDTOS = new ArrayList<>();
        String urlPrice = "https://hermes.pyth.network/v2/updates/price/latest?";
        String urlPriceOne = null;
        for (MintToken mintToken : mintTokens) {
            if (StringUtils.isNotEmpty(mintToken.getHermesId())) {
                if (null == urlPriceOne) {
                    urlPriceOne = urlPrice + "ids%5B%5D=" + mintToken.getHermesId();
                } else {
                    urlPriceOne = urlPriceOne + "&ids%5B%5D=" + mintToken.getHermesId();
                }
            }
        }
        String responsePrice = HttpClientUtil.doGetPrice(urlPriceOne);
        if (StringUtils.isNotEmpty(responsePrice)) {
            JSONObject object = new JSONObject(responsePrice);
            String parsed = object.get("parsed").toString();
            JSONArray jsonArray = new JSONArray(parsed);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String hermesId = jsonObject.get("id").toString();
                //根据hermesId查询
                LambdaQueryWrapper<MintToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(MintToken::getHermesId, hermesId);
                MintToken mintToken = this.mapper.selectOne(lambdaQueryWrapper);
                String priceString = jsonObject.get("price").toString();
                JSONObject objectPrice = new JSONObject(priceString);
                String price = objectPrice.get("price").toString();
                String expo = objectPrice.get("expo").toString();
                int pow = Math.abs(Integer.parseInt(expo));
                BigDecimal priceBig = new BigDecimal(price).divide(BigDecimal.TEN.pow(pow));
                MintTokenDTO mintTokenDTO = new MintTokenDTO();
                mintTokenDTO.setId(mintToken.getId());
                mintTokenDTO.setPrice(priceBig);
                mintTokenDTOS.add(mintTokenDTO);
            }
        }

        int effectRow = 0;
        for (MintTokenDTO mintTokenDTO : mintTokenDTOS) {
            effectRow += this.mapper.updatePriceById(mintTokenDTO.getPrice(), mintTokenDTO.getId());
        }
        if (effectRow > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error("generate error!");
    }

    @Override
    public ResponseResult initHermesId() {
        List<MintToken> mintTokens = list();
        List<MintToken> mintTokenArrayList = new ArrayList<>();
        for (MintToken mintToken : mintTokens) {
            //根据符号查询id
            String url = "https://hermes.pyth.network/v2/price_feeds?query=" + mintToken.getSymbol() + "&asset_type=crypto";
            String response = HttpClientUtil.doGet(url);
            if (StringUtils.isNotEmpty(response)) {
                JSONArray jsonArray = new JSONArray(response);
                if (!jsonArray.isEmpty()) {
                    int i = 0;
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    while (!mintToken.getSymbol().equals(jsonObject.get("base"))) {
                        jsonObject = jsonArray.getJSONObject(++i);
                    }
                    String id = jsonObject.get("id").toString();
                    mintToken.setHermesId(id);
                    mintTokenArrayList.add(mintToken);
                }
            }
        }
        boolean updateBatchById = updateBatchById(mintTokenArrayList);
        if (updateBatchById) {
            return ResponseResult.success();
        }
        return ResponseResult.error("init hermes id error!");
    }

    public int batchDeleteAndInsert(List<MintToken> entities) {
        int effectRow = 0;
        try {
            //筛选唯一ID
            List<String> distinctIdList = entities.stream().map(MintToken::getId).distinct().collect(Collectors.toList());
            //删除库里的
            effectRow += this.mapper.deleteBatchIds(distinctIdList);
            //去除重复的
            List<MintToken> newList = new ArrayList<>();
            //防重复
            for (MintToken entity : entities) {
                boolean exists = newList.stream().anyMatch(i -> i.getId().equals(entity.getId()));
                if (!exists) {
                    newList.add(entity);
                }
            }
            effectRow += this.saveBatch(newList, DEFAULT_BATCH_SIZE) ? newList.size() : 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return effectRow;
    }

    public static void main(String[] args) {
        RpcClientProxy rpcClient = new RpcClientProxy(true,"https://go.getblock.io/9d3ef7d157f045a4bb24faaf12745f24");

        try {
            final PublicKey tokenKey = new PublicKey("7BgBvyjrZX1YKz4oh9mjb8ZScatkkwb8DzFx7LoiVkM3");
            final PublicKey accountKey = new PublicKey("A6qH8847vcxf2KZuAquUmF1umT29nqdxQ8PiyjZE19gJ");
            PublicKey tokenAccountsByOwner = rpcClient.getApi().getTokenAccountsByOwner(accountKey, tokenKey);
            TokenResultObjects.TokenAmountInfo tokenSupply = rpcClient.getApi().getTokenAccountBalance(tokenAccountsByOwner);
            System.out.println(tokenSupply.getUiAmountString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
