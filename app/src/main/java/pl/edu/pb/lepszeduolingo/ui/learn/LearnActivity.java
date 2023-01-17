package pl.edu.pb.lepszeduolingo.ui.learn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;

public class LearnActivity extends AppCompatActivity implements AnswerListener{
    Fragment textFragment = new LearnTextFragment();
    Fragment imageFragment = new LearnImageFragment();
    Random rand = new Random();
    List<JSONObject> questions;
    int questionNumber;
    boolean fragmentFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        // db
        DatabaseFacade databaseFacade = new DatabaseFacade(this);
        questions = databaseFacade.getQuestionsByCategoryId(getIntent().getExtras().getInt("categoryId"));
        // set parameters for db
        setParams();
    }
    // set parameters for db
    void setParams(){
            // choose answer
            questionNumber = rand.nextInt(questions.size());
            JSONObject question = questions.get(questionNumber);
//            //przejść po questions i wybrać/wylosować niepoprawne odpowiedzi różne od porpawnej
//            Answers answers = new ConcreteAnswers(poprawna, niepoprawna1, niepo2, niepo3);
//            Answers decoratedAnswers = new AnsersLetterChange(answers);
//
//            List<String> lista = decoratedAnswers.getAnswers();
//            String correct = decoratedAnswers.getCorrectAnswer();

            // rand if text or image
            Fragment fragmentOut = fragmentFlag ? textFragment : imageFragment;
            // pass data
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Bundle data = new Bundle();
            data.putString("Question", question.toString());

            fragmentOut.setArguments(data);
            fragmentTransaction.replace(R.id.flLearn, fragmentOut).commit();
    }
    // get flag from fragment
    @Override
    public void addIsCorrect(boolean isCorrect) {
        // for testing
        fragmentFlag = !fragmentFlag;
        setParams();
    }
}
