package com.arley.cms.console.util;

import java.security.KeyPair;
import java.util.Collection;
import java.util.List;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/9/14 8:56
 */
public class CommonUtils {

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = RSAUtils.generateKey();
        String publicKey = RSAUtils.getPublicKey(keyPair);
        String privateKey = RSAUtils.getPrivateKey(keyPair);
        System.out.println("生成的公钥：" + publicKey);
        System.out.println("生成的私钥：" + privateKey);

    }

    /**
     * 判断集合非空
     * @param collection
     * @return
     */
    public static boolean isNotEmptyCollection(Collection collection) {
        return null != collection && collection.size() > 0;
    }

    public static boolean isEmptyOfList(List list) {
        return isEmptyOfListBySize(list, 0);
    }

    public static boolean isEmptyOfListBySize(List list, int size) {
        return null == list || list.size() <= size;
    }

}
