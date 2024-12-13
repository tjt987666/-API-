package com.xiongda.xiongclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class SignUtils {
    public static String getSign(String body, String secretKey){
        System.out.println("getSign:"+body+secretKey);

        // 使用hutool中的加密算法SHA256
        Digester md5 = new Digester(DigestAlgorithm.SHA256);

        String secretKey1 = body+"."+ secretKey;

        return md5.digestHex(secretKey1);
    }


}
