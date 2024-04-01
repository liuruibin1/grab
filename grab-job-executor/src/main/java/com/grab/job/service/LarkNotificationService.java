//package com.grab.job.service;
//
//import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//
//public class LarkNotificationService {
//    public static boolean larkNotification(ContractJobLog contractJobLog) {
//        String url = "https://open.larksuite.com/open-apis/bot/v2/hook/9b8ee129-df46-4b3e-9873-c04754012007";
//        String result = "失败";
//        if (contractJobLog.getResult()) {
//            result = "成功";
//        }
//        String jsonProgram = "{\n" +
//                "    \"msg_type\": \"post\",\n" +
//                "    \"content\": {\n" +
//                "        \"post\": {\n" +
//                "            \"en_us\": {\n" +
//                "                \"title\": \"" + contractJobLog.getFunctionName() + "\",\n" +
//                "                \"content\": [\n" +
//                "                    [\n" +
//                "                        {\n" +
//                "                            \"tag\": \"text\",\n" +
//                "                            \"text\": \"调度网络为: " + contractJobLog.getChainId() + "\\n" +
//                "调度合约为: " + contractJobLog.getContractName() + "\\n" +
//                "调度合约地址为: " + contractJobLog.getContractAddress() + "\\n" +
//                "调度是否成功: " + result + "\\n" +
//                "调度合约的函数为: " + contractJobLog.getFunctionName() + "\\n" +
//                "调度合约的参数为: " + contractJobLog.getParameter() + "\\n" +
//                "调度合约的hash为: " + contractJobLog.getHash() + "\\n" +
//                "调度合约的错误信息为: " + contractJobLog.getErrorMessage() + "\"\n" +
//                "                        }\n" +
//                "                    ]\n" +
//                "                ]\n" +
//                "            }\n" +
//                "        }\n" +
//                "    }\n" +
//                "}";
//
//        try {
//            // 发送 POST 请求并传递 JSON 参数
//            String response = HttpClientUtil.doPost(url, jsonProgram);
//
//            if (StringUtils.isEmpty(response)) {
//                return false;
//            }
//            // 打印响应
//            System.out.println("Response: " + response);
//            ObjectMapper objectMapper = new ObjectMapper();
//            ResponseLarkBO responseLarkBO = objectMapper.readValue(response, ResponseLarkBO.class);
//            if (ObjectUtils.isNotEmpty(responseLarkBO) && responseLarkBO.getMsg().equals("success")) {
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return false;
//    }
//
//    //测试调用
//    public static void main(String[] args) throws IOException {
//        ContractJobLog contractJobLog = new ContractJobLog();
//        contractJobLog.setResult(true);
//        contractJobLog.setParameter("11155111,0x64Ab107C899c440F4eb87DeaCeF5F47A91689798,setIncomeMakerAddress,0xf230CFdFfba9931cbe31E4855183a0357c1207A1");
//        contractJobLog.setHash("0x7962a713850195dfa051a22a0210f647d5863ecc34b494f2e38105d97bb0c8e0");
//        contractJobLog.setChainId(11155111);
//        contractJobLog.setFunctionName("setIncomeMakerAddress");
//        contractJobLog.setContractName("FurionSwapFactory");
//        contractJobLog.setContractAddress("0x64Ab107C899c440F4eb87DeaCeF5F47A91689798");
//        boolean result = larkNotification(contractJobLog);
//        System.out.println(result);
//    }
//}
