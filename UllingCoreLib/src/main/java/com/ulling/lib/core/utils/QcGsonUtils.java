package com.ulling.lib.core.utils;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 최길호 on 2018-06-05.
 */

public class QcGsonUtils {

    private static QcGsonUtils SINGLE_U = null;
    private final Gson GSON;

    public static synchronized QcGsonUtils getInstance(Context context) {
//        if (getInstance(context) == null) {
//            return null;
//        }
        if (SINGLE_U == null) {
            SINGLE_U = new QcGsonUtils(context);
        }
        return SINGLE_U;
    }

    public QcGsonUtils(Context context) {
        GSON = QcGsonBuilder.getInstance(context).getGson();
    }

    /**
     * json string -> class 파일로 가져오기
     *
     * @param gsonStr
     * @param classType
     * @param <T>
     * @return
     */
    public <T> T getStringToJson(String gsonStr, Class<T> classType) {
        try {
            return GSON.fromJson(gsonStr, classType);
        } catch (JsonParseException e) {
            QcLog.e("getStringToJson === " + gsonStr + " ===== " + e.toString());
            return null;
        }
    }

    /**
     * json string -> class 파일로 가져오기 / list
     *
     * @param gsonStr
     * @param classType
     * @param <T>
     * @return
     */
    public <T> ArrayList<T> getStringToJsonList(String gsonStr, Class<T> classType) {
        return GSON.fromJson(gsonStr, new ListOfJson<T>(classType));
    }

    private static class ListOfJson<T> implements ParameterizedType {

        private Class<?> wrapped;

        public ListOfJson(Class<T> wrapper) {
            this.wrapped = wrapper;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{wrapped};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

    /**
     * 객체 or 리스트등 -> json string 변환
     *
     * @param object
     * @param <T>
     * @return
     */
    public <T> String getJsonToString(Object object) {
        String json = GSON.toJson(object);
        return json;
    }

    public <T> String getJsonToString(Object object, Class<T> classType) {
        String json = GSON.toJson(object, classType);
        return json;
    }

    /**
     * 리스트 -> JsonArray으로 변환
     *
     * @param objectList
     * @param <T>
     * @return
     */
    public <T> JsonArray getJsonToJsonArray(ArrayList<T> objectList) {
        JsonArray jsonArray = GSON.toJsonTree(objectList).getAsJsonArray();
        return jsonArray;
    }
}