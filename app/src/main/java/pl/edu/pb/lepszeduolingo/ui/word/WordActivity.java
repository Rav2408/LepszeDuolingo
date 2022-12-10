package pl.edu.pb.lepszeduolingo.ui.word;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.ui.difficulty1.Diff_RecyclerViewAdapter;

public class WordActivity extends AppCompatActivity {
    TextView WordView, WordTranslationView;

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
        // TODO: set params here
        WordView.setText("Abomination");
        WordTranslationView.setText("Abominacja");
    }

}