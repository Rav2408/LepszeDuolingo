package pl.edu.pb.lepszeduolingo.db;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pb.lepszeduolingo.builder.Creator;
import pl.edu.pb.lepszeduolingo.builder.JsonBuilder;
import pl.edu.pb.lepszeduolingo.models.Language;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;

class DatabaseHelper {       //TODO wzorzec fabryka (factory method) do tworzenia kolejnych JSSONArray
    private static final String URL = "http://34.118.90.148:8090/api/";

    Context context;
    JSONArray words;
    JSONArray categories;
    JSONArray translations;
    JSONArray questions;
    JSONArray difficulties;
    JSONArray languages;
    JSONArray collections;
    JSONArray unlockedWords;
    JSONObject user;
    Creator creator;

    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getInstance(Context context){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    private DatabaseHelper(Context context) {
        this.context = context;
        pullData();
        creator = new Creator(context);
    }

    public void pullData(){

        updateWords();
        updateCategories();
        updateTranslations();
        updateQuestions();
        updateDifficulties();
        updateCollections();
        updateLanguages();
        //updateUnlockedWords();
        updateStartUnlockedWords();
    }

    public JSONArray getWords() {
        return words;
    }

    public JSONArray getCategories() {
        return categories;
    }

    public JSONArray getTranslations() {
        return translations;
    }

    public JSONArray getQuestions() {
        return questions;
    }

    public JSONArray getDifficulties() {
        return difficulties;
    }

    public JSONArray getLanguages() {
        return languages;
    }

    public JSONArray getCollections() {
        return collections;
    }

    public JSONArray getUnlockedWords() {
        return unlockedWords;
    }

    public void setUser(JSONObject user){
        this.user = user;
    }

    public void updateWords() {
        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                words=jsonArray;
            }
        }).getRequest(URL +"word");
    }

    public void updateCategories(){
        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                categories=jsonArray;
            }
        }).getRequest(URL +"category");
    }

    public void updateTranslations(){
        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                translations=jsonArray;
            }
        }).getRequest(URL +"translation");
    }
    public void updateQuestions(){
        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                questions=jsonArray;
            }
        }).getRequest(URL +"question");
    }
    public void updateDifficulties(){
        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                difficulties=jsonArray;
            }
        }).getRequest(URL +"difficulty");
    }
    public void updateCollections(){
        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                collections=jsonArray;
            }
        }).getRequest(URL +"collection");
    }
    public void updateLanguages(){
        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                languages=jsonArray;
            }
        }).getRequest(URL +"language");
    }
    public void updateUnlockedWords(){
        try {
            VolleyRequest.getInstance(context, new IVolley() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    unlockedWords=jsonArray;
                }
            }).getRequest(URL +"unlockedword/user/" + user.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void updateStartUnlockedWords(){
            VolleyRequest.getInstance(context, new IVolley() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    unlockedWords=jsonArray;
                }
            }).getRequest(URL +"unlockedword/");
    }

    public void unlockWord(int wordId) {
        try{
            unlockedWords.put(creator.createUnlockedWordWithText(getWordById(wordId), user));
            VolleyRequest.getInstance(context, new IVolley() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    unlockedWords=jsonArray;
                }
            }).postRequest(URL + "unlockedword", creator.createUnlockedWord(wordId, user.getInt("id")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public JSONObject getWordById(int wordId){
        for(int i = 0; i < words.length(); i++){
            try {
                if(words.getJSONObject(i).getInt("id") == wordId){
                    return words.getJSONObject(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

//    private void mapLanguages(JSONArray jsonArray){
//        Gson g = new Gson();
//        List<Language> languages = g.fromJson(String.valueOf(jsonArray), Language.class);
//        //TODO przejść po pętli żeby mapować pojedyńcze obiekty
//    }
}
