package cn.lemongo97.wol.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

public class GsonUtil {
    private static final Gson gson = new GsonBuilder().create();

    private GsonUtil() {
    }

    public static <T extends Serializable> String toJson(T obj) {
        return gson.toJson(obj);
    }

    public static <T extends Serializable> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T extends Serializable> T fromJson(String json, TypeToken<T> typeToken) {
        return gson.fromJson(json, typeToken.getType());
    }
}
