package pl.edu.pb.lepszeduolingo.builder;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonBuilder implements JSONDataBuilder{

    private static final String URL = "http://34.118.90.148:8090/api/";
    private Context context;
    JSONObject jsonObject;

    public JsonBuilder(Context context) {
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
