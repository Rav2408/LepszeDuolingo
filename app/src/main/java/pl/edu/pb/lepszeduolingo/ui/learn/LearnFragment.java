package pl.edu.pb.lepszeduolingo.ui.learn;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.Objects;

public class LearnFragment extends Fragment {
    Button answerView1;
    Button answerView2;
    Button answerView3;
    Button answerView4;
    Toast answerMessage;
    boolean isCorrect;
    void handleAnswer(int chosenAnswer){
        handleButtons(false);
        isCorrect = Objects.equals(chosenAnswer, 1);
        // set back message
        if(isCorrect){
            showMessage("Correct answer");
        }else{
            showMessage("Wrong answer");
        }
    }
    void showMessage(String message){
        int messageDelay = 400;
        int switchDelay = 600;
        answerMessage = Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG);
        answerMessage.show();
        // make toast disappear faster
        Handler handler = new Handler();
        handler.postDelayed(() -> answerMessage.cancel(), messageDelay);
        handler.postDelayed(() -> {
            addIsCorrect(isCorrect);
            handleButtons(true);
        }, switchDelay);
    }
    void getAnswer(){
        answerView1.setOnClickListener(v -> handleAnswer(0));
        answerView2.setOnClickListener(v -> handleAnswer(1));
        answerView3.setOnClickListener(v -> handleAnswer(2));
        answerView4.setOnClickListener(v -> handleAnswer(3));
    }
    void addIsCorrect(boolean isCorrect){
        AnswerListener answerListener = (AnswerListener) getActivity();
        assert answerListener != null;
        answerListener.addIsCorrect(isCorrect);
    }
    void handleButtons(boolean flag){
        answerView1.setEnabled(flag);
        answerView2.setEnabled(flag);
        answerView3.setEnabled(flag);
        answerView4.setEnabled(flag);
    }
}
