package pl.edu.pb.lepszeduolingo.rest;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IVolley {
    //void onResponse(String responde);
    default void onResponse(JSONArray jsonArray){

    };

    default void onResponse(JSONObject jsonObject){

    };

    default void onResponse(String string){

    };
}
