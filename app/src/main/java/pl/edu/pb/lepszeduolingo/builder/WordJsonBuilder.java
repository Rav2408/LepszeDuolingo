package pl.edu.pb.lepszeduolingo.builder;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;

public class WordJsonBuilder implements JSONDataBuilder{

    private static final String URL = "http://34.118.90.148:8090/api/";
    private Context context;
    JSONObject jsonObject;

    public WordJsonBuilder(Context context) {
        this.context = context;
    }

    @Override
    public JSONDataBuilder create() {
        jsonObject = new JSONObject();
        return this;
    }

    @Override
    public JSONDataBuilder put(String key, String value) {
        try {
            jsonObject.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }
    public JSONDataBuilder put(String key, int value) {
        try {
            jsonObject.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }
    public JSONDataBuilder put(String key, JSONObject value) {
        try {
            jsonObject.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public JSONDataBuilder put(String key, byte[] value) {
        try {
            jsonObject.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public JSONObject build() {
        return jsonObject;
    }

}
