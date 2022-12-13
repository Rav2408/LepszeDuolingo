package pl.edu.pb.lepszeduolingo.ui.word;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.db.DatabaseHelper;

public class WordActivity extends AppCompatActivity {
    TextView WordView, WordTranslationView;
    List<String> translations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        WordView = findViewById(R.id.word_text);
        WordTranslationView = findViewById(R.id.word_translation);



        // get parameter
        Bundle bundle = getIntent().getExtras();
        int wordId = -1;
        if(bundle != null){
            wordId = bundle.getInt("id");
        }
        setParams(wordId);
    }
    void setParams(int id){
        // TODO: translation recycle view
        DatabaseFacade databaseFacade = new DatabaseFacade(this);
        try {
            JSONObject word = databaseFacade.getWord(id);
            translations = databaseFacade.getTranslationsByWordId(word.getInt("id"));

            WordView.setText(word.getString("text"));
            if(translations.isEmpty()){
                WordTranslationView.setText("Brak tłumaczenia!");
            }else{
                WordTranslationView.setText(translations.get(0));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

}