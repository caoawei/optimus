package com.optimus.redis.util;

import com.optimus.utils.EncryptUtil;

public class HashAlgorithmUtils {

    public static byte[] md5(String key) {
        return EncryptUtil.nativeMd5(key);
    }

    /**
     * 也是将16个字节按序分4组,然后取各组最后一个字节,转换成long
     * @param key
     * @return
     */
    public static long keyHash(String key) {
        byte[] digest = md5(key);
        long code = (long) (digest[15] & 0xff) << 24;
        code |= (long) (digest[11] & 0xff) << 16;
        code |= (long) (digest[7] & 0xff) << 8;
        code |= (long) (digest[3] & 0xff);
        return code;
    }

}
