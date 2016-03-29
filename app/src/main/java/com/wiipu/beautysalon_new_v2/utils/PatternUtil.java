package com.wiipu.beautysalon_new_v2.utils;

/**
 * Created by Ken~Jc on 2016/3/8.
 * 检查手机号的工具
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检测格式是否正确
 *
 */
public class PatternUtil {
    /**
     * @param s
     * @return
     * 判断手机号码是否合法
     */
    public static boolean CheckPhoneNum(String s) {
        s = s.trim();
        Pattern pattern = Pattern.compile("1[3-9]{1}[0-9]{9}");
        Matcher match = pattern.matcher(s);
        return match.matches();
    }
}
