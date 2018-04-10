package com.future.zhh.ticket.presentation.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2017/11/19.
 */

@Singleton
public class JsonSerializerUtil {

    private final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer())
            .registerTypeAdapter(Date.class, new DateSerializer()).create();

    //日期时间戳
    public class DateSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime());
        }
    }

    //日期时间戳
    public class DateDeserializer implements JsonDeserializer<Date> {

        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Date(json.getAsJsonPrimitive().getAsLong());
        }
    }

    @Inject
    public JsonSerializerUtil() {}

    /**
     * Serialize an object to Json.
     */
    public String serialize(Object entity) {
        String jsonString = gson.toJson(entity, Object.class);
        return jsonString;
    }

    /**
     * Deserialize a json representation of an object.
     */
    public Object deserialize(String jsonString) {
        Object entity = gson.fromJson(jsonString, Object.class);
        return entity;
    }

    public <T> T deserialize(String jsonString, Class<T> clazz){
        T result = gson.fromJson(jsonString,clazz);
        return result;
    }

    public <T> List<T> deserializeList(String jsonString){
        Type type = new TypeToken<List<T>>(){}.getType();
        List<T> list = gson.fromJson(jsonString, type);
        return list;
    }

    public <T> List<T> deserializeList(String jsonString, Class<T[]> clazz) {
        T[] arr = gson.fromJson(jsonString, clazz);
        return Arrays.asList(arr);
    }

    public <T> List<List<T>> deserializeMizList(String jsonString){
        Type type = new TypeToken<List<List<T>>>(){}.getType();
        List<List<T>> list = gson.fromJson(jsonString,type);
        return list;
    }

    public <T> List<List<T>> deserializeMizList(String jsonString, Class<T[][]> clazz) {
        T[][] arr = gson.fromJson(jsonString, clazz);
        List<List<T>> src = new ArrayList<List<T>>();
        for(int i=0; i<arr.length; i++){
            src.add(Arrays.asList(arr[i]));
        }
        return src;
    }


//  public <T> List<List<T>> deserializeMizList(String jsonString, Class<T[][]> clazz){
//    T[][] arr = gson.fromJson(jsonString, clazz);
//
//    return Arrays.asList(Arrays.asList(arr));
//  }


//  public List<Object> deserializeList(String jsonString){
//      List<Object> list = new ArrayList<>();
//      JSONArray jsonArray = null;
//      try {
//        jsonArray = new JSONArray(jsonString);
//        for (int i = 0; i < jsonArray.length(); i++) {
//          list.add(deserialize(jsonArray.get(i).toString()));
//        }
//      } catch (JSONException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
//      return list;
//  }






}
