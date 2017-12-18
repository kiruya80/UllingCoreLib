/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */

package com.ulling.lib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import android.content.Context;

import java.io.IOException;

/**
 *
 */
public class QcGsonBuilder {
  private static QcGsonBuilder gsonInstances = null;
  private static Gson GSON;
  private static final String UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

  public static void init(Context context) {
    gsonInstances = getInstance(context);
    QcLog.e("GsonBuilderHelper init complete !");
  }

  private QcGsonBuilder(Context context) {
    GsonBuilder gsonGsonBuilder = new GsonBuilder();
    gsonGsonBuilder.setDateFormat(UTC_DATE_FORMAT);
    //		gsonGsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
    gsonGsonBuilder.setPrettyPrinting();
    gsonGsonBuilder.registerTypeAdapter(long.class, new LongTypeAdapter());
    gsonGsonBuilder.registerTypeAdapter(Long.class, new LongTypeAdapter());
    gsonGsonBuilder.registerTypeAdapter(double.class, new DoubleTypeAdapter());
    gsonGsonBuilder.registerTypeAdapter(Double.class, new DoubleTypeAdapter());
    gsonGsonBuilder.registerTypeAdapter(float.class, new FloatTypeAdapter());
    gsonGsonBuilder.registerTypeAdapter(Float.class, new FloatTypeAdapter());
    GSON = gsonGsonBuilder.create();
  }

  public static QcGsonBuilder getInstance(Context mCtx_) {
    if (gsonInstances == null)
      gsonInstances = new QcGsonBuilder(mCtx_);
    return gsonInstances;
  }

  public Gson getGson() {
    return GSON;
  }

  public class LongTypeAdapter extends TypeAdapter<Long> {
    @Override
    public Long read(JsonReader reader) throws IOException {
      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return null;
      }
      String stringValue = reader.nextString();
      if ("".equals(stringValue)) {
        return null;
      }
      try {
        Long value = Long.valueOf(stringValue);
        return value;
      } catch (NumberFormatException e) {
        return null;
      }
    }

    @Override
    public void write(JsonWriter writer, Long value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }
      writer.value(value);
    }
  }

  public class FloatTypeAdapter extends TypeAdapter<Float> {
    @Override
    public Float read(JsonReader reader) throws IOException {
      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return null;
      }
      String stringValue = reader.nextString();
      if ("".equals(stringValue)) {
        return null;
      }
      try {
        Float value = Float.valueOf(stringValue);
        return value;
      } catch (NumberFormatException e) {
        return null;
      }
    }

    @Override
    public void write(JsonWriter writer, Float value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }
      writer.value(value);
    }
  }

  public class DoubleTypeAdapter extends TypeAdapter<Double> {
    @Override
    public Double read(JsonReader reader) throws IOException {
      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return null;
      }
      String stringValue = "";
      try {
        stringValue = reader.nextString();
        if (stringValue == null || "".equals(stringValue)) {
          return null;
        }
      } catch (Exception e) {
        return null;
      }
      try {
        Double value = Double.valueOf(stringValue);
        return value;
      } catch (NumberFormatException e) {
        return null;
      }
    }

    @Override
    public void write(JsonWriter writer, Double value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }
      writer.value(value);
    }
  }
}
