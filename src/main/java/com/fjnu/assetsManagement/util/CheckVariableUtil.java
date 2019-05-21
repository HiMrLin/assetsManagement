/**
 * @author CaoZhengxi
 * @date 2018年5月22日
 */
package com.fjnu.assetsManagement.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: CheckVariableUtil
 * @author lwn
 * @date
 *
 */
public class CheckVariableUtil {
    /**
     * 检查分页信息非法
     *
     * @param pageNum  起始页
     * @param pageSize 页面大小
     * @return 如果分页信息非法返回true
     */
    public static boolean pageParamIsIllegal(Integer pageNum, Integer pageSize) {

        return pageNum == null || pageSize == null || pageNum <= 0 || pageSize < 0;
    }

    /**
     * 检测空字符串变量
     *
     * @return 空字符传返回true 非空返回false
     */
    private static boolean stringVariableIsEmpty(String string) {
        if (string == null || "".equals(string.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 检测Integer值为null或小于0
     *
     * @return 如果为null或小于0返回true
     */
    public static boolean integerParamLessZero(Integer value) {
        return null == value || (value.intValue() < 0);
    }

    /**
     * 检测字符串是Integer格式的
     *
     * @return: 如果字符串 -不是- Integer格式的返回true
     */
    public static boolean variableIsInteger(String value) {
        if (value == null) {
            return true;
        }
        String pattern = "^[1-9]\\d*|0$";
        return !Pattern.matches(pattern, value);
    }


    /**
     * 检测身份证号是非法的
     *
     * @param iDCard
     * @return非法返回true
     */
    public static boolean iDCardIsIllegal(String iDCard) {
        if (iDCard == null || "".equals(iDCard.trim()) || iDCard.trim().length() != 18 || !iDCard.matches("\\d{17}[\\d|X]")) {
            return true;
        }
        return false;
    }

    /**
     * 字符串转Double
     *
     * @param str
     * @param defaultNum 默认值
     * @return
     */
    public static Double parseDouble(String str, double defaultNum) {
        double num = defaultNum;
        if (!stringVariableIsEmpty(str)) {
            try {
                num = Double.parseDouble(str.trim());
            } catch (Exception e) {
                num = defaultNum;
            }
        }
        return num;
    }

    /**
     * 字符串转Integer
     *
     * @param str
     * @param defaultNum 默认值
     * @return
     */
    public static Integer parseInt(String str, int defaultNum) {
        int num = defaultNum;
        if (!stringVariableIsEmpty(str)) {
            try {
                num = Integer.parseInt(str.trim());
            } catch (Exception e) {
                num = defaultNum;
            }
        }
        return num;
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }


    /**
     * 邮箱验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isEmai(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); // 验证邮箱
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 字符串转Long
     *
     * @param str
     * @param defaultNum 默认值
     * @return
     */
    public static Long parseLong(String str, Long defaultNum) {
        Long num = defaultNum;
        if (!stringVariableIsEmpty(str)) {
            try {
                num = Long.parseLong(str.trim());
            } catch (Exception e) {
                num = defaultNum;
            }
        }
        return num;
    }

    /*
        检验sex参数是否为0，1；
     */
    public static boolean isSexConstants(Integer sex) {
        if (sex == null) {
            return false;
        }
        int MAN = 1;
        int WOMAN = 0;
        Set<Integer> sexConstant = new HashSet<>();
        sexConstant.add(MAN);
        sexConstant.add(WOMAN);
        if (!sexConstant.contains(sex)) {
            return false;
        }
        return true;
    }


}
