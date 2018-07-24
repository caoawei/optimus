package com.optimus.utils;

import com.optimus.common.exception.BizException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * 加密工具类
 * @author caoawei
 * Created on 2017/12/5.
 */
public class EncryptUtil {

    private static String PASSWORD = md5(EncryptUtil.class.getName()).substring(0,16);

    public static String md5(String text){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text == null ? "".getBytes() : text.getBytes("utf-8"));
            byte[] b = md.digest();
            return encodeHex(b);
        } catch (Exception e) {
            throw new BizException("MD5加密失败.",e);
        }
    }

    public static String md5OfUpper(String text){
        return md5(text).toUpperCase();
    }

    /**
     * AES 加密数据
     * 注意:此方法要求被加密数据以及加密密码长度必要是16的倍数.
     * @param text 被加密数据
     * @param password 加密密码
     * @return
     */
    public static String aes(String text,String password){
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = text == null ? "".getBytes() : text.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(password.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return parseByte2HexStr(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    private static String encodeHex(byte[] data){
        StringBuffer rs = new StringBuffer("");
        for (int offset = 0; offset < data.length; offset++) {
            int i = data[offset];
            if (i < 0){
                i += 256;
            }
            if (i < 16){
                rs.append("0");
            }
            rs.append(Integer.toHexString(i));
        }

        return rs.toString();
    }
}
