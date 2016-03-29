package com.wiipu.beautysalon_new_v2.network.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 网络请求常量类
 */
public class Constants {

	/**
	 * 设备的sn码
	 * */
	private static String sn = "";
	/**
	 * 网络请求的url
	 */
	public static String URL = "http://112.124.3.197:8016/bimuInterface/app_bound.php";
	public static String APPKEY = "888";
	public static final int RTIMES = 1;
	public static String SECRET = "753159842564855248546518489789";

	/**
	 * 获取设备的sn码
	 * */
	public static String getSN(Context context) {
		if (sn.equals("")) {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			sn = tm.getSimSerialNumber()==null ? "" : tm.getSimSerialNumber();
		}
		return sn;
	}
}
