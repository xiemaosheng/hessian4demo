package com.xms.task.lib;

import com.alibaba.fastjson.JSONObject;
import com.xms.task.code.Code;
import com.xms.task.exception.ServiceCheckException;
import org.springframework.util.StringUtils;

public class Asserts {

    private Asserts() {
    }

    /**
     * 断言 布尔值正常
     *
     * @param expression
     * @param message
     */
    public static void check(boolean expression, String message) throws ServiceCheckException {
        if (!expression) {
            throw new ServiceCheckException(Code.ARGUMENT_INVALID, message);
        }
    }

    /**
     * 断言 为空
     *
     * @param str
     * @param message
     */
    public static void notEmpty(Object str, String message) {

        if (StringUtils.isEmpty(str)) {
            throw new ServiceCheckException(Code.ARGUMENT_REQUIRED, message);
        }
    }

    /**
     * 断言 正则表达式匹配
     *
     * @param str
     * @param message
     */
    public static void matches(String str, String regex, String message) {
        if (!str.matches(regex)) {
            throw new ServiceCheckException(Code.ARGUMENT_INVALID, message);
        }
    }

    /**
     * 断言 身份证号码
     *
     * @param str
     * @param message
     */
    public static void cardNo(String str, String message) {
        if (!str.matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$")) {
            throw new ServiceCheckException(Code.ARGUMENT_CARDNO, message);
        }
    }

    /**
     * 断言 电话号码
     *
     * @param str
     * @param message
     */
    public static void mobile(String str, String message) {
        if (!str.matches("^1[345789]\\d{9}$")) {
            throw new ServiceCheckException(Code.ARGUMENT_MOBILE, message);
        }
    }

    /**
     * 断言 电子邮箱
     *
     * @param str
     * @param message
     */
    public static void email(String str, String message) {
        if (!str.matches("^\\s*([a-zA-Z0-9_\\.\\-\\+])+@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,20})\\s*$")) {
            throw new ServiceCheckException(Code.ARGUMENT_EMAIL, message);
        }
    }

    /**
     * 断言 Json字符串
     *
     * @param json
     * @param message
     */
    public static void json(String json, String message) {

        boolean state = false;
        try {
            JSONObject.parse(json);
            state = true;
        } catch (Exception e) {
            state = false;
        }

        if (!state) {
            throw new ServiceCheckException(Code.ARGUMENT_JSON, message);
        }
    }

    /**
     * 断言 字符串长度范围
     *
     * @param value
     * @param min
     * @param max
     * @param message
     */
    public static void rangeLength(String value, int min, int max, String message) {
        if (value.length() < min) {
            throw new ServiceCheckException(Code.ARGUMENT_SHORT, message);
        }

        if (value.length() > max) {
            throw new ServiceCheckException(Code.ARGUMENT_LONG, message);
        }

    }

    /**
     * 最大长度验证
     *
     * @param value
     * @param length
     * @param message
     */
    public static void maxLength(String value, int length, String message) {
        if (value.length() > length) {
            throw new ServiceCheckException(Code.ARGUMENT_LONG, message);
        }
    }

    /**
     * 断言 URL
     *
     * @param str
     * @return boolean
     */
    public static void url(String str, String message) {
        if (!str.matches("^(https|http|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;{}]*")) {
            throw new ServiceCheckException(Code.ARGUMENT_URL, message);
        }
    }

    /**
     * 断言正整字
     *
     * @param str
     * @return boolean
     */
    public static void number(String str, String message) {
        if (!str.matches("^[0-9]*$")) {
            throw new ServiceCheckException(Code.ARGUMENT_NUMBER, message);
        }
    }

    /**
     * 断言浮点数
     *
     * @param str
     * @return boolean
     */
    public static void floatNumber(String str, String message) {
        if (!str.matches("^\\d+(\\.\\d+)?$")) {
            throw new ServiceCheckException(Code.ARGUMENT_FLOAT, message);
        }
    }
}
