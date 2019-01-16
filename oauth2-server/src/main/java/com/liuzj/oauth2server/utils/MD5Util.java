package com.liuzj.oauth2server.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * 字符串MD5编码
     *
     * @param input 输入字符串
     * @return String 编码后的字符串
     * @see Md5Util
     */
    public static String encodeMD5(String input) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            md5.update(input.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : md5.digest()) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
