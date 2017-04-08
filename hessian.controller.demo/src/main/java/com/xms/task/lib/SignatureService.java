package com.xms.task.lib;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

public class SignatureService {

    public static boolean verify(String json, String sign, String key) {

        if (sign == null || key == null) {
            return false;
        }
        String text = json + key;

        String mysign = DigestUtils.md5Hex(getContentBytes(text, "utf-8"));

        if (!mysign.equals(sign)) {
            return false;
        }
        return true;
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {

        try {
            if (charset == null || "".equals(charset)) {
                return content.getBytes("utf-8");
            } else {
                return content.getBytes(charset);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset, e);
        }
    }

    /**
     * 签名字符串
     *
     * @param json 需要签名的字符串
     * @param key  密钥
     * @return 签名结果
     */
    public static String sign(String json, String key) {
        json = json + key;
        return DigestUtils.md5Hex(getContentBytes(json, "utf-8"));
    }
}