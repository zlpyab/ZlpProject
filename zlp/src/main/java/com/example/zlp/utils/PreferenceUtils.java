package com.example.zlp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author U
 *	SharePreference:String,int,boolean
 */
public class PreferenceUtils {
	
	private static SharedPreferences getSp(Context context) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp;
	}
	
	/**
	 * @param context
	 * @param key
	 * @param value
	 *  存放boolean值
	 */
	public static void putBoolean(Context context, String key, boolean value){
		SharedPreferences sp=getSp(context);
		sp.edit().putBoolean(key, value).commit();
	}
	
	
	/**
	 * @param context
	 * @param key
	 * @param defValue
	 *  获取boolean的值
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue){
		SharedPreferences sp=getSp(context);
		return sp.getBoolean(key, defValue);
	}
	/**
	 * @param context
	 * @param key
	 *  获取boolean的值
	 */
	public static boolean getBoolean(Context context, String key){
		return getBoolean(context,key,false);
	}
	
	// String
	/**
	 * @param context
	 * @param key
	 * @param value
	 *  存放String值
	 */
	public static void putString(Context context, String key, String value){
		SharedPreferences sp=getSp(context);
		sp.edit().putString(key, value).commit();
	}
	
	
	/**
	 * @param context
	 * @param key
	 * @param defValue
	 *  获取String的值
	 */
	public static String getString(Context context, String key, String defValue){
		SharedPreferences sp=getSp(context);
		return sp.getString(key, defValue);
	}
	/**
	 * @param context
	 * @param key
	 *  获取String的值
	 */
	public static String getString(Context context, String key){
		return getString(context,key,"");
	}
	// int
	/**
	 * @param context
	 * @param key
	 * @param value
	 *  存放int值
	 */
	public static void putInt(Context context, String key, int value){
		SharedPreferences sp=getSp(context);
		sp.edit().putInt(key, value).commit();
	}
	
	
	/**
	 * @param context
	 * @param key
	 * @param defValue
	 *  获取String的值
	 */
	public static int getInt(Context context, String key, int defValue){
		SharedPreferences sp=getSp(context);
		return sp.getInt(key, defValue);
	}
	/**
	 * @param context
	 * @param key
	 *  获取String的值
	 */
	public static int getInt(Context context, String key){
		return getInt(context,key,-1);
	}

	/**
	 *
	 * @param context
	 * @param key
	 * @param value
	 * 存放long值
	 */
	public static void putLong(Context context, String key, long value){
		SharedPreferences sp = getSp(context);
		sp.edit().putLong(key,value).commit();
	}

	/**
	 *
	 * @param context
	 * @param key
	 * @param defValue
	 * @return 获取long值
	 */
	public static long getLong(Context context, String key, long defValue){
		SharedPreferences sp=getSp(context);
		return sp.getLong(key,defValue);
	}
	
	/**
	 * @param context
	 *  清除
	 */
	public static void clear(Context context){
		SharedPreferences sp=getSp(context);
		sp.edit().clear().commit();
	}

	public static void remove(Context context, String key) {
		SharedPreferences sp=getSp(context);
		sp.edit().remove(key);
	}
	
}
