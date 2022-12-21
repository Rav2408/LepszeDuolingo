package pl.edu.pb.lepszeduolingo.db;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;

import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;

public class DatabaseHelper {       //TODO wzorzec fabryka (factory method) do tworzenia kolejnych JSSONArray
    private static final String URL = "http://34.118.90.148:8090/api/";

    Context context;
    JSONArray words;
    JSONArray categories;
    JSONArray translations;
    JSONArray questions;
    JSONArray difficulties;
    String currentSalt;
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

        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                words=jsonArray;
            }
        }).getRequest(URL +"word");

        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                categories=jsonArray;
            }
        }).getRequest(URL +"category");

        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                translations=jsonArray;
            }
        }).getRequest(URL +"translation");

        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                questions=jsonArray;
            }
        }).getRequest(URL +"question");

        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                difficulties=jsonArray;
            }
        }).getRequest(URL +"difficulty");
    }

    public void printConsole(){
        Log.d("data", words.toString());
        Log.d("data", categories.toString());
        Log.d("data", translations.toString());
        Log.d("data", questions.toString());
        Log.d("data", difficulties.toString());
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

//    public String findUserSaltByEmail(String email){
//
//        VolleyRequest.getInstance(context, new IVolley() {
//            @Override
//            public void onResponse(String salt) {
//                currentSalt = salt;
//            }
//        }).getRequest(URL +"duolingo/salt?email" + email);
//    }

}
