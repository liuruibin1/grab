package com.xxl.job.core.util;

import java.util.Arrays;
import java.util.List;

public class UUIDUtil {

    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID() {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID() {
        return UUID.fastUUID().toString(true);
    }

    public static String generateBySeed(String seed) {
        byte[] bytes = seed.getBytes();
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
        return uuid.toString().replaceAll("-", "");
    }

    public static String generateLowerCaseBySeedArray(String... seedArray) {
        List<String> seedList = Arrays.asList(seedArray);
        String seedStr = String.join("", seedList);
        return generateBySeed(seedStr.toLowerCase());
    }

    public static String generateUpperCase8BySeedArray(String... seedArray) {
        List<String> seedList = Arrays.asList(seedArray);
        String seedStr = String.join("", seedList);
        return generateBySeed(seedStr).toUpperCase().substring(0, 8);
    }

    public static void main(String[] args) {
       /* String id = "";
        //09526025de7f0803b70d9dbffe58be5a15a2e16b5574eb37229f70da71487511
        id = UUIDUtil.generateLowerCaseBySeedArray("Sepolia", "0xD6d0769D9E8BdFeE378DDb0C12c7515850DB341b");
        System.out.println("WETH = " + id);*/

        System.out.println(simpleUUID());
    }

}
