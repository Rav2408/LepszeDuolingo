package pl.edu.pb.lepszeduolingo.db;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

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

    }

    public void pullData(){

        updateWords();
        updateCategories();
        updateTranslations();
        updateQuestions();
        updateDifficulties();
        updateCollections();
        updateLanguages();
        updateUnlockedWords();
    }

    public void printConsole(){
        Log.d("data", words.toString());
        Log.d("data", categories.toString());
        Log.d("data", translations.toString());
        Log.d("data", questions.toString());
        Log.d("data", difficulties.toString());
        Log.d("data", languages.toString());
    }


    //TODO DatabaseFacade
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
        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                unlockedWords=jsonArray;
            }
        }).getRequest(URL +"unlockedword");
    }



//    private void mapLanguages(JSONArray jsonArray){
//        Gson g = new Gson();
//        List<Language> languages = g.fromJson(String.valueOf(jsonArray), Language.class);
//        //TODO przejść po pętli żeby mapować pojedyńcze obiekty
//    }
}
