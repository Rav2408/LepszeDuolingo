package pl.edu.pb.lepszeduolingo;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

public class LearnActivity extends AppCompatActivity {
    Button answerView_1, answerView_2, answerView_3, answerView_4;
    TextView questionViewText;
    CardView questionWrapper;
    MutableLiveData<Integer> answer = new MutableLiveData<>();
    Toast answerMessage;
    // test data
    Random rand = new Random();
    Integer correctAnswer = rand.nextInt(4);
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
        // set parameters for db
        setParams();
        // listen inputs
        getAnswer();
        // show answer and redirect
        handleAnswer();
    }
    // TODO: add logic when every word was unlocked
    // perhaps show every word without deleting them?
    void handleAnswer(){
        answer.observe(this, chosenAnswer -> {
            if(Objects.equals(chosenAnswer, correctAnswer)){
                // test
                // Log.d(TAG, String.format("Good answer: %2d", chosenAnswer));
                // remove that question from list and db
                // givenList.removeIf(givenList -> givenList.getId().equals(Id));
                showMessage("Correct answer");
                recreate();
            }else{
                // test
                // Log.d(TAG, String.format("Wrong Answer: %2d", chosenAnswer));
                // question stays
                showMessage("Wrong answer");
                recreate();
            }
        });
    }
    void showMessage(String message){
        int delay = 400;
        answerMessage = Toast.makeText(this, message,
                Toast.LENGTH_LONG);
        answerMessage.show();
        // make toast disappear faster
        Handler handler = new Handler();
        handler.postDelayed(() -> answerMessage.cancel(), delay);
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
        /*
        // random value from locked words
            Random rand = new Random();
            int Id = givenList.get(rand.nextInt(givenList.size()));
        */
        questionViewText.setText("Fajne pytanie tej");
        answerView_1.setText("tak");
        answerView_2.setText("nie");
        answerView_3.setText("tej");
        answerView_4.setText("wuchta wiary");
    }
    void getAnswer(){
        answerView_1.setOnClickListener(v -> answer.setValue(0));
        answerView_2.setOnClickListener(v -> answer.setValue(1));
        answerView_3.setOnClickListener(v -> answer.setValue(2));
        answerView_4.setOnClickListener(v -> answer.setValue(3));
    }
}