package com.ulling.lib;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.reflect.TypeToken;
import com.ulling.lib.common.UllingDefine;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : KILHO
 * @ProjectName : BBuzzArt_Phase_2.0
 * @FileName : SharedPreferencesHelper.java
 * @FilePath : net.bbuzzart.android.util
 * @Date : 2015. 1. 12.
 * @프로그램 ㄴ 프리퍼런스 헬퍼 클래스
 * @변경이력
 */
public class SharedPreferencesHelper {
    /**
     * Log - class name
     */
    private static String TAG = "SharedPreferencesHelper";
    private static SharedPreferencesHelper prefsInstances = null;
    /**
     * preference
     */
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    private static Gson GSON;
//	Type typeOfObject = new TypeToken<Object>(){}.getType();

    public static synchronized void init(Context context) {
        prefsInstances = getComplexPreferences(context);
        UtilLog.eLog(TAG, "ComplexPreferences init complete !");
    }

    private SharedPreferencesHelper(Context context) {
        prefs = context.getSharedPreferences(UllingDefine.SP_APP_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        GsonBuilder gsonGsonBuilder = new GsonBuilder();
        gsonGsonBuilder.setDateFormat(UllingDefine.UTC_DATE_FORMAT);
        gsonGsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        gsonGsonBuilder.setPrettyPrinting();
        GSON = gsonGsonBuilder.create();
    }

    public static SharedPreferencesHelper getComplexPreferences(Context context) {
        if (prefsInstances == null) {
            prefsInstances = new SharedPreferencesHelper(context);
        }
        return prefsInstances;
    }

    public void put(String key, String value) {
        editor.putString(key, value);
        editor.commit();
        if (UtilLog.PREFER_LOG_FLAG)
            UtilLog.eLog(TAG, "key :" + key + " , value :" + value);
    }

    public void putSet(String key, ArrayList<String> valueList) {
        valueList = new ArrayList<String>();
        Set<String> value = new HashSet<String>(valueList);
        editor.putStringSet(key, value);
        editor.commit();
        if (UtilLog.PREFER_LOG_FLAG)
            UtilLog.eLog(TAG, "key :" + key + " , value :" + value);
    }

    public void put(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
        if (UtilLog.PREFER_LOG_FLAG)
            UtilLog.eLog(TAG, "key :" + key + " , value :" + value);
    }

    public void put(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
        if (UtilLog.PREFER_LOG_FLAG)
            UtilLog.eLog(TAG, "key :" + key + " , value :" + value);
    }

    public void put(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
        if (UtilLog.PREFER_LOG_FLAG)
            UtilLog.eLog(TAG, "key :" + key + " , value :" + value);
    }

    public void put(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
        if (UtilLog.PREFER_LOG_FLAG)
            UtilLog.eLog(TAG, "key :" + key + " , value :" + value);
    }

    public String get(String key, String defValue) {
        if (UtilLog.PREFER_LOG_FLAG)
            UtilLog.eLog(TAG, "key :" + key + " , value :" + prefs.getString(key, defValue));
        return prefs.getString(key, defValue);
    }

    public int get(String key, int defValue) {
        if (UtilLog.PREFER_LOG_FLAG)
            UtilLog.eLog(TAG, "key :" + key + " , value :" + prefs.getInt(key, defValue));
        return prefs.getInt(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        if (UtilLog.PREFER_LOG_FLAG)
            UtilLog.eLog(TAG, "key :" + key + " , value :" + prefs.getBoolean(key, defValue));
        return prefs.getBoolean(key, defValue);
    }

    public long getLong(String key, long defValue) {
        if (UtilLog.PREFER_LOG_FLAG)
            UtilLog.eLog(TAG, "key :" + key + " , value :" + prefs.getLong(key, defValue));
        return prefs.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        if (UtilLog.PREFER_LOG_FLAG)
            UtilLog.eLog(TAG, "key :" + key + " , value :" + prefs.getFloat(key, defValue));
        return prefs.getFloat(key, defValue);
    }

    public Object putObject(String key, Object object) {
        if (object == null) {
            UtilLog.eLog(TAG, "Object is null");
            return null;
        }
        if (key.equals("") || key == null) {
            UtilLog.eLog(TAG, "Key is empty or null");
            return null;
        }
        if (object.equals("")) {
            editor.putString(key, "");
        } else {
            editor.putString(key, GSON.toJson(object));
        }
        return editor.commit();
    }

    public <T> T getObject(String key, Class<T> a) {
        String gson = null;
        try {
            gson = prefs.getString(key, null);
        } catch (Exception e) {
        }
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public <T> List<Object> getObjectSet(String key, Class<T> a) {
        List<Object> gsonList = new ArrayList<Object>();
        String gson = null;
        try {
            gson = prefs.getString(key, null);
        } catch (Exception e) {
        }
        if (gson == null) {
            return null;
        }
        Type type = new TypeToken<List<Object>>() {
        }.getType();
        gsonList = GSON.fromJson(gson, type);
        return gsonList;
    }

    public SharedPreferencesHelper remove(String key) {
        editor.remove(key);
        editor.commit();
        return this;
    }
}
