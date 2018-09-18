package com.hydra.core.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

import com.hydra.core.common.CONSMSG;

/**
 * MD5加解密工具
 * <p>
 * Created on 2017年12月20日 下午2:54:25
 * </p>
 * 
 * @author Hydra wangshuang@lakala.com
 * @since 2017年12月20日
 */
public final class MD5 {
    /**
     * <p>
     * Title: sign
     * </p>
     * <p>
     * Description: 签名
     * </p>
     * 
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return String
     */
    public static String sign(String text, String key, String input_charset) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * <p>
     * Title: sign
     * </p>
     * <p>
     * Description: 签名，编码格式默认为UTF-8
     * </p>
     * 
     * @param text 需要签名的字符串
     * @param key 密钥
     * @return String
     */
    public static String sign(String text, String key) {
        return sign(text, key, CONSMSG.CHARSET_UTF8);
    }

    /**
     * <p>
     * Title: verify
     * </p>
     * <p>
     * Description: 验签
     * </p>
     * 
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param sign 签名结果
     * @param input_charset 编码格式
     * @return boolean
     */
    public static boolean verify(String text, String key, String sign, String input_charset) {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>
     * Title: verify
     * </p>
     * <p>
     * Description: 验签，编码格式默认为UTF-8
     * </p>
     * 
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param sign 签名结果
     * @return boolean
     */
    public static boolean verify(String text, String key, String sign) {
        return verify(text, key, sign, CONSMSG.CHARSET_UTF8);
    }

    private static byte[] getContentBytes(String content, String charset) {
        if (StringUtil.isEmpty(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    private MD5() {
        throw new UnsupportedOperationException();
    }
}
