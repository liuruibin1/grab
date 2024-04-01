package com.grab.job;


import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

@Component
public class ContractJobHandler extends BaseJob {


    @XxlJob("ContractJobHandler:handle")
    public void handle() {
        //参数
        String param = XxlJobHelper.getJobParam();
        assert param != null;
        //最多可以输入两个函数方法
        String[] split = param.split(",");

    }

}
