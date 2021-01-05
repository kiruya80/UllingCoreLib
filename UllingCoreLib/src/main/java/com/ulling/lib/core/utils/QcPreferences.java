/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */
package com.ulling.lib.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.LongSerializationPolicy;
import com.ulling.lib.core.base.QcBaseApplication;
import com.ulling.lib.core.common.QcDefine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : KILHO
 * @프로그램 ㄴ 프리퍼런스 헬퍼 클래스
 * @변경이력
 */
public class QcPreferences {
    private static QcPreferences SINGLE_U = null;
    /**
     * preference
     */
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    public static final boolean PREFER_LOG_FLAG = QcDefine.DEBUG_FLAG;
    private static Gson GSON;
    private String APP_NAME;

    public static synchronized QcPreferences getInstance() {
        if (QcBaseApplication.getInstance() == null) {
            QcLog.i("QcPreferences init failed !");
            return null;
        }
        if (SINGLE_U == null) {
            SINGLE_U = new QcPreferences();
        }
        return SINGLE_U;
    }

    private QcPreferences() {
        APP_NAME = QcBaseApplication.getInstance().getPackageName();
        prefs = QcBaseApplication.getInstance().getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        GsonBuilder gsonGsonBuilder = new GsonBuilder();
        gsonGsonBuilder.setDateFormat(QcDefine.UTC_DATE_FORMAT);
        gsonGsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        gsonGsonBuilder.setPrettyPrinting();
        GSON = gsonGsonBuilder.create();
        QcLog.i("QcPreferences init Success !!" + APP_NAME);
    }

    public String getAPP_NAME() {
        return APP_NAME;
    }

    public void put(String key, String value) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return;
        }
        if (value == null) {
            editor.putString(key, "");
        } else {
            editor.putString(key, value);
        }
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, int value) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return;
        }
        editor.putInt(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, boolean value) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return;
        }
        editor.putBoolean(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, long value) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return;
        }
        editor.putLong(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, float value) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return;
        }
        editor.putFloat(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, Object object) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return;
        }
        if (object == null) {
            editor.putString(key, "");
        } else {
            editor.putString(key, GSON.toJson(object));
        }
        editor.commit();
        if (PREFER_LOG_FLAG && object != null)
            QcLog.e("key :" + key + " , value :" + object.toString());
    }

    //    public void put(String key, ArrayList<String> valueList) {
//        valueList = new ArrayList<String>();
//        Set<String> value = new HashSet<String>(valueList);
//        editor.putStringSet(key, value);
//        editor.commit();
//        if (PREFER_LOG_FLAG)
//            QcLog.e("key :" + key + " , value :" + value);
//    }

    public <T> void putList(String key, List<T> valueList) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return;
        }
        if (valueList == null) {
            editor.putString(key, "");
        } else {
            editor.putString(key, GSON.toJson(valueList));
        }
        editor.commit();
        if (PREFER_LOG_FLAG && valueList != null)
            QcLog.e("key :" + key + " , value :" + valueList);
    }

    public String get(String key, String defValue) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return null;
        }
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getString(key, defValue));
        return prefs.getString(key, defValue);
    }

    public int get(String key, int defValue) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return 0;
        }
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getInt(key, defValue));
        return prefs.getInt(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return false;
        }
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getBoolean(key, defValue));
        return prefs.getBoolean(key, defValue);
    }

    public long get(String key, long defValue) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return 0;
        }
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getLong(key, defValue));
        return prefs.getLong(key, defValue);
    }

    public float get(String key, float defValue) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return 0;
        }
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getFloat(key, defValue));
        return prefs.getFloat(key, defValue);
    }

    public <T> T get(String key, Class<T> cls) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return null;
        }
        String gson = null;
        try {
            gson = prefs.getString(key, "");
        } catch (Exception e) {
        }
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, cls);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public <T> List<T> getList(String key, final Class<T> cls) {
        if (key == null || key.isEmpty()) {
            QcLog.e("Key is empty or null");
            return null;
        }

        List<T> list = new ArrayList<T>();
        String gson = prefs.getString(key, "");

        if (gson.isEmpty()) {
            return list;
        } else {
//            Type type = new TypeToken<List<T>>() {
//            }.getType();
//            return GSON.fromJson(gson, type);

            try {
                JsonArray arry = new JsonParser().parse(gson).getAsJsonArray();
                for (JsonElement jsonElement : arry) {
                    list.add(GSON.fromJson(jsonElement, cls));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

    }

    public QcPreferences remove(String key) {
        editor.remove(key);
        editor.commit();
        return this;
    }
}
