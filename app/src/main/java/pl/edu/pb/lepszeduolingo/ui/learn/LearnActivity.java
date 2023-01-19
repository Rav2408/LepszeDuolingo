package pl.edu.pb.lepszeduolingo.ui.learn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.decorator.Answers;
import pl.edu.pb.lepszeduolingo.decorator.AnswersLetterChange;
import pl.edu.pb.lepszeduolingo.decorator.AnswersOrderChange;
import pl.edu.pb.lepszeduolingo.decorator.ConcreteAnswers;

public class LearnActivity extends AppCompatActivity implements AnswerListener{
    Answers orderChange;
    Answers letterChange;
    Fragment textFragment = new LearnTextFragment();
    Fragment imageFragment = new LearnImageFragment();
    Random rand = new Random();
    List<JSONObject> questions;
    int questionNumber;
    boolean fragmentFlag = false;

    private List<Integer> drawRandomNumbers(int max){
        List<Integer> list = new ArrayList<>();
        List<Integer> outputList = new ArrayList<>();

        for (int i=1; i<max; i++) list.add(i);
        Collections.shuffle(list);
        for (int i=0; i<4; i++){
           outputList.add(list.get(i));
        }
        return outputList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        // db
        DatabaseFacade databaseFacade = new DatabaseFacade(this);
        questions = databaseFacade.getQuestionsByCategoryId(getIntent().getExtras().getInt("categoryId"));
        // set parameters for db
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
    // set parameters for db
    void setParams() throws JSONException {
            // choose answer
            List<Integer> randomUniqueNumbers = drawRandomNumbers(questions.size());
            questionNumber = randomUniqueNumbers.get(0);
            JSONObject question = questions.get(questionNumber);

            String correctAnswer;
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

            List<String> answersStrings = new ArrayList<>();
            answersStrings.add(correctAnswer);
            answersStrings.add(incorrect1);
            answersStrings.add(incorrect2);
            answersStrings.add(incorrect3);

            Answers answers = new ConcreteAnswers(answersStrings, correctAnswer);
            orderChange = new AnswersOrderChange(answers);
            Answers answers2 = new ConcreteAnswers(answersStrings, correctAnswer);
            Answers orderChange2 = new AnswersOrderChange(answers2);
            letterChange = new AnswersLetterChange(orderChange2);
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
        try {
            setParams();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
