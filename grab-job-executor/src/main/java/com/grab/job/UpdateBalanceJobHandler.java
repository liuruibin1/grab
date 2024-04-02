package com.grab.job;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.grab.domain.MintToken;
import com.grab.service.MintTokenService;
import com.grab.service.impl.MintTokenServiceImpl;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.util.HttpClientUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateBalanceJobHandler extends BaseJob {

    @Autowired
    MintTokenServiceImpl mintTokenServiceImpl;

    /**
     * 定时更新价格
     */
    @XxlJob("UpdateBalanceJobHandler:handle")
    public void handle() {
        List<MintToken> mintTokens = mintTokenServiceImpl.list();
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
        mintTokenServiceImpl.updateBatchById(mintTokenArrayList);
    }

}
