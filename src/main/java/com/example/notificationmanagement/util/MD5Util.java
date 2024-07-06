package com.example.notificationmanagement.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {    //密码编译
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());  //计算输入字符串的 MD5 哈希值
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));  //格式化为两位的十六进制字符串
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
