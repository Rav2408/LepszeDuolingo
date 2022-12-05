package pl.edu.pb.lepszeduolingo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LearnActivity extends AppCompatActivity {
    Button answerView_1, answerView_2, answerView_3, answerView_4;
    TextView questionViewText;
    CardView questionWrapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        answerView_1 = findViewById(R.id.answer_1);
        answerView_2 = findViewById(R.id.answer_2);
        answerView_3 = findViewById(R.id.answer_3);
        answerView_4 = findViewById(R.id.answer_4);
        questionViewText = findViewById(R.id.question_view);
        questionWrapper = findViewById(R.id.question_wrapper);
        setParams();
    }
    void setParams(){
        // TODO: pass correct params
        /*
        * if (jest to zdjecie){
        *   questionViewText.setVisibility(View.INVISIBLE);
        * }else{
        *   questionWrapper.setBackgroundColor(tutaj zdjecie);
        * }
        * */
        questionViewText.setText("Fajne pytanie tej");
        answerView_1.setText("tak");
        answerView_2.setText("nie");
        answerView_3.setText("tej");
        answerView_4.setText("wuchta wiary");
    }
}