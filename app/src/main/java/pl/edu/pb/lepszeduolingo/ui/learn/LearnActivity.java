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
import pl.edu.pb.lepszeduolingo.decorator.AnswersOrderChange;
import pl.edu.pb.lepszeduolingo.decorator.ConcreteAnswers;

public class LearnActivity extends AppCompatActivity implements AnswerListener{
    Answers orderChange;
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
            String word = question.getJSONObject("word").getString("text");
            String incorrect1 = questions.get(randomUniqueNumbers.get(1)).getJSONObject("word").getString("text");
            String incorrect2 = questions.get(randomUniqueNumbers.get(2)).getJSONObject("word").getString("text");
            String incorrect3 = questions.get(randomUniqueNumbers.get(3)).getJSONObject("word").getString("text");
            List<String> answersString = new ArrayList<>();
            answersString.add(word);
            answersString.add(incorrect1);
            answersString.add(incorrect2);
            answersString.add(incorrect3);

            Answers answers = new ConcreteAnswers(answersString, word);
            orderChange = new AnswersOrderChange(answers);

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
        try {
            setParams();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
