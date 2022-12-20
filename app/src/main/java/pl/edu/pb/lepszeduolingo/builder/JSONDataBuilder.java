package pl.edu.pb.lepszeduolingo.builder;

import org.json.JSONArray;
import org.json.JSONObject;

public interface JSONDataBuilder {
    JSONObject jsonObject = null;

    JSONDataBuilder create();
    JSONDataBuilder put(String key, String value);
    JSONDataBuilder put(String key, int value);
    JSONDataBuilder put(String key, JSONObject value);
    JSONDataBuilder put(String key, byte[] value);
    JSONObject build();

}
