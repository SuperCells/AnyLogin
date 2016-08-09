package com.supercells.lib;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by solo on 16/7/9.
 * 存储帮助类
 */
public class DBUtil {
    public static void setString(Context ctx, String fileName, String key, String info) {
        Map map = new HashMap();
        map.put(key, info);
        save(ctx, fileName, map);
    }

    public static String getString(Context context, String fileName, String key) {
        SharedPreferences shared = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return shared.getString(key, "");
    }

    public static void setBoolean(Context ctx, String fileName, String key, boolean info) {
        Map map = new HashMap();
        map.put(key, Boolean.valueOf(info));
        save(ctx, fileName, map);
    }

    public static boolean getBoolean(Context context, String fileName, String key) {
        SharedPreferences shared = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return shared.getBoolean(key, false);
    }

    public static void setLong(Context ctx, String fileName, String key, long info) {
        Map map = new HashMap();
        map.put(key, Long.valueOf(info));
        save(ctx, fileName, map);
    }

    public static long getLong(Context context, String fileName, String key) {
        SharedPreferences shared = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return shared.getLong(key, 0L);
    }

    public static void setDouble(Context ctx, String fileName, String key, double info) {
        Map map = new HashMap();
        map.put(key, String.valueOf(info));
        save(ctx, fileName, map);
    }

    public static double getDouble(Context context, String fileName, String key) {
        SharedPreferences shared = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return Double.valueOf(shared.getString(key, "0")).doubleValue();
    }

    public static void setInteger(Context ctx, String fileName, String key, int info) {
        Map map = new HashMap();
        map.put(key, Integer.valueOf(info));
        save(ctx, fileName, map);
    }

    public static int getInteger(Context context, String fileName, String key) {
        SharedPreferences shared = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return shared.getInt(key, 0);
    }

    public static void setCharacter(Context ctx, String fileName, String key, char info) {
        Map map = new HashMap();
        map.put(key, String.valueOf(info));
        save(ctx, fileName, map);
    }

    public static char getCharacter(Context context, String fileName, String key) {
        SharedPreferences shared = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return shared.getString(key, "").charAt(0);
    }

    public static void setFloat(Context ctx, String fileName, String key, Float info) {
        Map map = new HashMap();
        map.put(key, info);
        save(ctx, fileName, map);
    }

    public static float getFloat(Context context, String fileName, String key) {
        SharedPreferences shared = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return shared.getFloat(key, 0.0F);
    }

    public static void remove(Context context,String fileName,String key){
        SharedPreferences.Editor editor = getEditor(context, fileName);
        editor.remove(key);
        editor.commit();
    }

    public static void saveExtras(Context ctx, String fileName, Map<String, ?> map) {
        save(ctx, fileName, map);
    }

    public static Map<String, ?> getExtras(Context ctx, String fileName) {
        SharedPreferences shared = ctx.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return shared.getAll();
    }

    public static SharedPreferences getSharedPreferences(Context context, String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context, String fileName) {
        return getSharedPreferences(context, fileName).edit();
    }

    private static void save(Context context, String fileName, Map<String, ?> map) {
        SharedPreferences.Editor editor = getEditor(context, fileName);
        for (Map.Entry entry : map.entrySet()) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if ((value instanceof Integer)) {
                editor.putInt(key, ((Integer) value).intValue());
            } else if ((value instanceof Long)) {
                editor.putLong(key, ((Long) value).longValue());
            } else if ((value instanceof Boolean)) {
                editor.putBoolean(key, ((Boolean) value).booleanValue());
            } else if ((value instanceof Float)) {
                editor.putFloat(key, ((Float) value).floatValue());
            } else {
                editor.putString(key, (String) value);
            }
        }

        editor.commit();
    }
}
