package pl.edu.pb.lepszeduolingo.db;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFacade {
    DatabaseHelper databaseHelper;

    public DatabaseFacade(Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    public List<String> getTranslationsByWordId(int wordId){
        JSONArray allTranlations = databaseHelper.getTranslations();
        List<String> translations = new ArrayList<>();

        for(int i=0;i<allTranlations.length();i++){
            try {
                if(allTranlations.getJSONObject(i).getJSONObject("word").getInt("id") == wordId){
                    translations.add(allTranlations.getJSONObject(i).getString("translationText"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return translations;
    }


    public JSONArray getWords() {
        return databaseHelper.getWords();
    }

    public JSONObject getWord(int id) throws JSONException {
        return getWords().getJSONObject(id);
    }

    public JSONArray getCategories() {
        return databaseHelper.getCategories();
    }
    public List<JSONObject> getCategoriesByDifficultyId(int id) {
        JSONArray allCategories = getCategories();
        List<JSONObject> categories = new ArrayList<>();

        for(int i=0;i<allCategories.length();i++){
            try {
                if(allCategories.getJSONObject(i).getJSONObject("difficulty").getInt("id") == id){
                    categories.add(allCategories.getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }


    public JSONObject getCollectionByCategoryId(int categoryId){
        JSONArray allCollections = getCollections();
        for(int i=0;i<allCollections.length();i++) {
            try {
                if(allCollections.getJSONObject(i).getJSONObject("category").getInt("id") == categoryId){
                    return allCollections.getJSONObject(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public List<JSONObject> getQuestionsByCategoryId(int categoryId){
        JSONArray allQuestions = getQuestions();
        List<JSONObject> questions = new ArrayList<>();
        for(int i=0;i<allQuestions.length();i++) {
            try {
                if(allQuestions.getJSONObject(i).getJSONObject("collection").getJSONObject("category").getInt("id") == categoryId){
                     questions.add(allQuestions.getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return questions;
    }



    public JSONArray getTranslations() {
        return databaseHelper.getTranslations();
    }

    public JSONArray getQuestions() {
        return databaseHelper.getQuestions();
    }

    public JSONArray getDifficulties() {
        return databaseHelper.getDifficulties();
    }

    public JSONArray getLanguages(){ return databaseHelper.getLanguages(); }

    public JSONArray getCollections() {
        return databaseHelper.getCollections();
    }

    public void setUser(JSONObject user){
        databaseHelper.setUser(user);
    }

}
