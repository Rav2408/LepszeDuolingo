package pl.edu.pb.lepszeduolingo.builder;

import android.content.Context;

import org.json.JSONObject;

public class Creator {

    Context context;

    public Creator(Context context) {
        this.context = context;
    }

    public JSONObject createWord(){
       return null;
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
