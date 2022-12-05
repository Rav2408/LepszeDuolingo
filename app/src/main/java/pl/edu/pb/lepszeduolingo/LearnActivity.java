package pl.edu.pb.lepszeduolingo;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class LearnActivity extends AppCompatActivity {
    Button answerView_1, answerView_2, answerView_3, answerView_4;
    TextView questionViewText;
    CardView questionWrapper;
    MutableLiveData<Integer> answer = new MutableLiveData<>();
    // test data
    Integer correctAnswer = 0;
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
        // default
        answer.setValue(-1);
        getAnswer();
        // listen for answer
        answer.observe(this, new Observer<Integer>(){
            @Override
            public void onChanged(Integer chosenAnswer) {
                if(Objects.equals(chosenAnswer, correctAnswer)){
                    Log.d(TAG, String.format("Good answer: %2d", chosenAnswer));
                }else{
                    Log.d(TAG, String.format("Wrong Answer: %2d", chosenAnswer));
                }
            }
        });
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
    void getAnswer(){
        answerView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setValue(0);
            }
        });
        answerView_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setValue(1);
            }
        });
        answerView_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setValue(2);
            }
        });
        answerView_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setValue(3);
            }
        });
    }
}