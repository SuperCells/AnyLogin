package com.supercells.lib;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * Created by solo on 16/7/5.
 * 反射获取资源ID
 */
public class ResUtil {
	private static final String TAG = "ResUtil";
	/**
	 * 获取资源
	 * 
	 * @param context
	 * @param packageName
	 * @param resourcesName
	 * @return
	 */
	public static int getResIdByName(Context context, String packageName, String resourcesName) {
		Resources resources = context.getResources();
		int id = resources.getIdentifier(resourcesName, packageName, context.getPackageName());
		if (id == 0) {
			Log.e(TAG, "资源文件读取不到！resourcesName:" + resourcesName);
		}
		return id;
	}

	/**
	 * 获取布局ID
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int layout(Context context, String resourcesName) {
		return getResIdByName(context, "layout", resourcesName);
	}
	/**
	 * 获取 color ID
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int color(Context context, String resourcesName) {
		return getResIdByName(context, "color", resourcesName);
	}
	/**
	 * 获取 array ID
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int array(Context context, String resourcesName) {
		return getResIdByName(context, "array", resourcesName);
	}
	/**
	 * 获取String资源ID
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int string(Context context, String resourcesName) {
		return getResIdByName(context, "string", resourcesName);
	}

	public static String stringValue(Context context, String resourcesName) {
		String res = "";
		try {
			res = context.getResources().getString(getResIdByName(context, "string", resourcesName));
		} catch (Exception e) {
			Log.e(TAG, "resourcesName:" + resourcesName);
		}
		return res;
	}

	/**
	 * 获取view id资源
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int view(Context context, String resourcesName) {
		return getResIdByName(context, "id", resourcesName);
	}

	/**
	 * 获取drawable资源
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int drawable(Context context, String resourcesName) {
		return getResIdByName(context, "drawable", resourcesName);
	}

	/**
	 * 获取drawable资源
	 * 
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int anim(Context context, String resourcesName) {
		return getResIdByName(context, "anim", resourcesName);
	}

	/**
	 * 获取dimen资源
	 *
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int dimen(Context context,String resourcesName){
		return getResIdByName(context, "dimen", resourcesName);
	}
	/**
	 * 获取style资源
	 *
	 * @param context
	 * @param resourcesName
	 * @return
	 */
	public static int style(Context context, String resourcesName) {
		return getResIdByName(context, "style", resourcesName);
	}
}
