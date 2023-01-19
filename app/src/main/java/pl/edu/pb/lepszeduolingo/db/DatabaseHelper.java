package pl.edu.pb.lepszeduolingo.db;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import pl.edu.pb.lepszeduolingo.builder.Creator;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;

class DatabaseHelper {
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
    JSONArray scores;
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
        creator = new Creator(context);
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
//    public void updateStartUnlockedWords(){
//            VolleyRequest.getInstance(context, new IVolley() {
//                @Override
//                public void onResponse(JSONArray jsonArray) {
//                    unlockedWords=jsonArray;
//                }
//            }).getRequest(URL +"unlockedword");
//    }
    public void updateScores(){
        VolleyRequest.getInstance(context, new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                scores=jsonArray;
            }
        }).getRequest(URL +"score");
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
    public JSONObject getDifficultyById(int difficultyID){
        for(int i = 0; i < difficulties.length(); i++){
            try {
                if(difficulties.getJSONObject(i).getInt("id") == difficultyID){
                    return difficulties.getJSONObject(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public int getDifficultyIdByName(String difficultyName){
        for(int i = 0; i < difficulties.length(); i++){
            try {
                if(Objects.equals(difficulties.getJSONObject(i).getString("level"), difficultyName)){
                    return difficulties.getJSONObject(i).getInt("id");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
    public JSONObject getScoreByDifficultyId(int difficultyId){
        for(int i = 0; i < scores.length(); i++){
            try {
                if(scores.getJSONObject(i).getJSONObject("difficulty").getInt("id") == difficultyId
                    && scores.getJSONObject(i).getJSONObject("duolingoUser").getInt("id") == user.getInt("id")){
                    return scores.getJSONObject(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public void setBestScore(int difficultyId, int newScore){
        JSONObject score = getScoreByDifficultyId(difficultyId);
        try {
            if(score != null && newScore > score.getInt("bestScore")){
                updateBestScoreById(newScore, score.getInt("id"), difficultyId);
            }else{
                scores.put(creator.createScore(newScore, difficultyId, user.getInt("id")));
                VolleyRequest.getInstance(context, new IVolley() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        scores=jsonArray;
                    }
                }).postRequest(URL + "score", creator.createScore(newScore, difficultyId, user.getInt("id")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void updateBestScoreById(int newScore, int scoreId, int difficultyId){
        try {
            // get old data
            JSONArray tempArray = scores;
            JSONObject tempScore = tempArray.getJSONObject(scoreId);
            // set new data
            tempScore.put("bestScore", newScore);
            tempArray.put(scoreId, tempScore);
            // update score
            scores = tempArray;
            VolleyRequest.getInstance(context, new IVolley() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    scores=jsonArray;
                }
            }).postRequest(URL + "score", creator.updateScore(newScore, difficultyId, user.getInt("id"), scoreId));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
