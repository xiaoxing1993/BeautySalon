package com.wiipu.beautysalon_new_v2.network.beans;

import android.content.Context;
import com.android.volley.VolleyError;

/**
 * 请求响应失败的接口
 */
public interface ErrorHook {

	/**
	 * 响应失败的接口
	 * @param context 应用程序上下文
	 * @param error 失败的原因
	 */
	public void deal(Context context, VolleyError error);
		//TODO 在该处填写错误回调处理
}
