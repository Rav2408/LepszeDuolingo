package pl.edu.pb.lepszeduolingo.ui.learn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.decorator.Answers;
import pl.edu.pb.lepszeduolingo.decorator.AnswersLetterChange;
import pl.edu.pb.lepszeduolingo.decorator.AnswersOrderChange;
import pl.edu.pb.lepszeduolingo.decorator.ConcreteAnswers;
import pl.edu.pb.lepszeduolingo.utils.RandomUtil;

public class LearnActivity extends AppCompatActivity implements AnswerListener{
    Answers orderChange;
    Answers letterChange;
    Fragment textFragment = new LearnTextFragment();
    Fragment imageFragment = new LearnImageFragment();
    List<JSONObject> questions;
    List<String> answersStrings;
    String correctAnswer;
    int questionNumber;
    boolean fragmentFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        DatabaseFacade databaseFacade = new DatabaseFacade(this);
        questions = databaseFacade.getQuestionsByCategoryId(getIntent().getExtras().getInt("categoryId"));

        try {
            setParams();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public int getWordId(){
        try {
            return Integer.parseInt(questions.get(questionNumber).getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private JSONObject prepareQuestion() throws JSONException {
        List<Integer> randomUniqueNumbers = RandomUtil.drawRandomNumbers(questions.size());
        questionNumber = randomUniqueNumbers.get(0);
        JSONObject question = questions.get(questionNumber);

        String incorrect1;
        String incorrect2;
        String incorrect3;

        if(fragmentFlag){
            correctAnswer = question.getJSONObject("translation").getString("translationText");
            incorrect1 = questions.get(randomUniqueNumbers.get(1)).getJSONObject("translation").getString("translationText");
            incorrect2 = questions.get(randomUniqueNumbers.get(2)).getJSONObject("translation").getString("translationText");
            incorrect3 = questions.get(randomUniqueNumbers.get(3)).getJSONObject("translation").getString("translationText");
        }else{
            correctAnswer = question.getJSONObject("word").getString("text");
            incorrect1 = questions.get(randomUniqueNumbers.get(1)).getJSONObject("word").getString("text");
            incorrect2 = questions.get(randomUniqueNumbers.get(2)).getJSONObject("word").getString("text");
            incorrect3 = questions.get(randomUniqueNumbers.get(3)).getJSONObject("word").getString("text");
        }

        answersStrings = new ArrayList<>();
        answersStrings.add(correctAnswer);
        answersStrings.add(incorrect1);
        answersStrings.add(incorrect2);
        answersStrings.add(incorrect3);

        return question;
    }


    void setParams() throws JSONException {
            JSONObject question = prepareQuestion();

            Answers answers = new ConcreteAnswers(answersStrings, correctAnswer);
            orderChange = new AnswersOrderChange(answers);
            Answers answers2 = new ConcreteAnswers(answersStrings, correctAnswer);
            Answers orderChange2 = new AnswersOrderChange(answers2);
            letterChange = new AnswersLetterChange(orderChange2);

            // switch text or image
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
    public void nextQuestion() {
        fragmentFlag = !fragmentFlag;
        try {
            setParams();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
