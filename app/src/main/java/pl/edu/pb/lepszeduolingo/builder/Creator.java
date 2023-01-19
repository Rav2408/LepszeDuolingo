package pl.edu.pb.lepszeduolingo.builder;

import android.content.Context;

import org.json.JSONObject;

import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;

public class Creator {

    Context context;

    public Creator(Context context) {
        this.context = context;
    }

    public JSONObject createWord(String word, int languageId, int difficultyId, String imagePath){
       return new JsonBuilder(context).create()
               .put("text", word)
               .put("language",
                       new JsonBuilder(context)
                               .create()
                               .put("id", languageId)
                               .build()
               )
               .put("difficulty",
                       new JsonBuilder(context)
                               .create()
                               .put("id",  difficultyId)
                               .build()
               )
               .put("imagePath", imagePath)
               .build();
    }

    public JSONObject createUser(String name, String email, String role, String salt, String hash){
        return new JsonBuilder(context).create()
                .put("name", name)
                .put("email", email)
                .put("role", role)
                .put("salt", salt)
                .put("hash", hash)
                .build();
    }

    public JSONObject createCategory(String name, int difficultyId){
        return new JsonBuilder(context).create()
                .put("name", name)
                .put("difficulties",
                        new JsonBuilder(context)
                                .create()
                                .put("id",difficultyId)
                                .build()
                )
                .build();
    }

    public JSONObject createScore(int newScore, int difficultyId, int userId){
        return new JsonBuilder(context).create()
                .put("bestScore", newScore)
                .put("difficulty",
                        new JsonBuilder(context)
                                .create()
                                .put("id", difficultyId)
                                .build()
                )
                .put("duolingoUser",
                        new JsonBuilder(context)
                                .create()
                                .put("id", userId)
                                .build()
                )
                .build();
    }
    public JSONObject updateScore(int newScore, int difficultyId, int userId, int scoreId){
        return new JsonBuilder(context).create()
                .put("id", scoreId)
                .put("bestScore", newScore)
                .put("difficulty",
                        new JsonBuilder(context)
                                .create()
                                .put("id", difficultyId)
                                .build()
                )
                .put("duolingoUser",
                        new JsonBuilder(context)
                                .create()
                                .put("id", userId)
                                .build()
                )
                .build();
    }

    public JSONObject createUnlockedWord(int wordId, int userId){
        return new JsonBuilder(context).create()
                .put("word",
                        new JsonBuilder(context)
                                .create()
                                .put("id", wordId)
                                .build()
                )
                .put("duolingoUser",
                        new JsonBuilder(context)
                                .create()
                                .put("id",  userId)
                                .build()
                )
                .build();
    }
    public JSONObject createUnlockedWordWithText(JSONObject word, JSONObject user){
        return new JsonBuilder(context).create()
                .put("word", word)
                .put("duolingoUser", user)
                .build();
    }
}
