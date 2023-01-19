package pl.edu.pb.lepszeduolingo.ui.word;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;

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
        // TODO: translation recycle view to show many translations
        DatabaseFacade databaseFacade = new DatabaseFacade(this);
        try {
            JSONObject unlockedWord = databaseFacade.getUnlockedWord(id);
            translations = databaseFacade.getTranslationsByWordId(unlockedWord.getInt("id"));

            WordView.setText(unlockedWord.getString("text"));
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
