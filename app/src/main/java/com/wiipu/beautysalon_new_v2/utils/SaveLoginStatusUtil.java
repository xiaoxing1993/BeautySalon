package com.wiipu.beautysalon_new_v2.utils;

/**
 * Created by Ken~Jc on 2016/3/11.
 * Modified by xxx on 2016/3/14
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 保存用户状态的工具类
 *
 * @author wiipu
 *
 */
public class SaveLoginStatusUtil {

    private final static String NAME = "UserLoginStatus";

    /**
     * 存储用户信息
     *
     xxx 3.14
     */
    public static void save(Context context,  String nick_name, String sex, String phone_num,String position,String company_id,
                            String shop_id, String personal_id) {
        SharedPreferences preferences = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nick_name",nick_name);
        editor.putString("sex",sex);
        editor.putString("phone_num",phone_num);
        editor.putString("position",position);
        editor.putString("company_id",company_id);
        editor.putString("shop_id",shop_id);
        editor.putString("personal_id",personal_id);
        editor.commit();
    }

    /**

     */
    public static String loadNick_name(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return preferences.getString("nick_name", "nick");
    }

    public static String loadSex(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return preferences.getString("sex", "sex");
    }

    public static String loadPhone_num(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return preferences.getString("phone_num", "phone_num");
    }

    public static String loadPosition(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return preferences.getString("position", "position");
    }

    public static String loadCompany_id(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return preferences.getString("company_id", "company_id");
    }

    public static String loadShop_id(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return preferences.getString("shop_id", "shop_id");
    }

    public static String loadPersonal_id(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return preferences.getString("personal_id", "personal_id");
    }




}