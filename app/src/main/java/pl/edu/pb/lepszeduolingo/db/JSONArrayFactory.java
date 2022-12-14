package pl.edu.pb.lepszeduolingo.db;

import android.content.Context;

import org.json.JSONArray;

public class JSONArrayFactory {

    public JSONArray pullJSONArrayData(Context context, String name){
        if(name.equals("word")) return new WordsJSONArray(context);
        else return null;
    }
}
