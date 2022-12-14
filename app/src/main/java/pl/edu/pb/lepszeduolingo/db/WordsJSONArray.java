package pl.edu.pb.lepszeduolingo.db;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;

public class WordsJSONArray extends JSONArray {

    JSONArray wordsJsonArray;

    public WordsJSONArray(Context context) {
        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    wordsJsonArray = new JSONArray(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).getRequest("http://34.118.90.148:8090/api/word");
    }

}
