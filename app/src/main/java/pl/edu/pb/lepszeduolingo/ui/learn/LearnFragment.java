package pl.edu.pb.lepszeduolingo.ui.learn;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;

public class LearnFragment extends Fragment {
    Button answerView1;
    Button answerView2;
    Button answerView3;
    Button answerView4;
    Toast answerMessage;
    boolean isCorrect;
    DatabaseFacade databaseFacade = new DatabaseFacade(this.getContext());

    void handleAnswer(int chosenAnswer, String answer){
        handleButtons(false);
        int wordId = ((LearnActivity)getActivity()).getWordId();

        isCorrect = ((LearnActivity)getActivity()).orderChange.getCorrectAnswer().equals(answer);
        if(isCorrect){
            showMessage("Correct answer");
            if(!databaseFacade.isWordUnlocked(wordId)){
                databaseFacade.unlockWord(wordId);
            }
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
            nextQuestion();
            handleButtons(true);
        }, switchDelay);
    }
    void getAnswer(){
        answerView1.setOnClickListener(v -> handleAnswer(0,answerView1.getText().toString()));
        answerView2.setOnClickListener(v -> handleAnswer(1,answerView2.getText().toString()));
        answerView3.setOnClickListener(v -> handleAnswer(2,answerView3.getText().toString()));
        answerView4.setOnClickListener(v -> handleAnswer(3,answerView4.getText().toString()));
    }
    void nextQuestion(){
        AnswerListener answerListener = (AnswerListener) getActivity();
        assert answerListener != null;
        answerListener.nextQuestion();
    }
    void handleButtons(boolean flag){
        answerView1.setEnabled(flag);
        answerView2.setEnabled(flag);
        answerView3.setEnabled(flag);
        answerView4.setEnabled(flag);
    }
}
