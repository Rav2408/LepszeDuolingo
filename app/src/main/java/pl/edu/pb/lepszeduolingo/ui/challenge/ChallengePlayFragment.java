package pl.edu.pb.lepszeduolingo.ui.challenge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentChallengePlayBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.decorator.Answers;
import pl.edu.pb.lepszeduolingo.decorator.AnswersLetterChange;
import pl.edu.pb.lepszeduolingo.decorator.AnswersOrderChange;
import pl.edu.pb.lepszeduolingo.decorator.ConcreteAnswers;
import pl.edu.pb.lepszeduolingo.utils.RandomUtil;

public class ChallengePlayFragment extends Fragment {
    FragmentChallengePlayBinding binding;
    AppCompatButton answerButton1;
    AppCompatButton answerButton2;
    AppCompatButton answerButton3;
    AppCompatButton answerButton4;
    TextView questionView;
    JSONArray questions;
    String correctAnswer;
    int questionCounter = 0;
    boolean isCorrect;
    int questionStarted;
    Points points;
    boolean fragmentFlag = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        String pointsJsonString = args.getString("points");
        points = GsonUtils.getGsonParser().fromJson(pointsJsonString, Points.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChallengePlayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        answerButton1 = root.findViewById(R.id.challengeAnswer_1_text);
        answerButton2 = root.findViewById(R.id.challengeAnswer_2_text);
        answerButton3 = root.findViewById(R.id.challengeAnswer_3_text);
        answerButton4 = root.findViewById(R.id.challengeAnswer_4_text);
        questionView = root.findViewById(R.id.challengeQuestionView);

        answerButton1.setOnClickListener(v -> handleAnswer(0));
        answerButton2.setOnClickListener(v -> handleAnswer(1));
        answerButton3.setOnClickListener(v -> handleAnswer(2));
        answerButton4.setOnClickListener(v -> handleAnswer(3));

        DatabaseFacade databaseFacade = new DatabaseFacade(this.getActivity());
        questions = databaseFacade.getQuestions();

        // prevents internet privacy error
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setParams();
        return root;
    }

    private List<String> prepareAnswers(JSONObject question, List<Integer> randomUniqueNumbers ) throws JSONException, IOException {
        String word = question.getJSONObject("word").getString("text");
        String url = question.getJSONObject("word").getString("imagePath");

        String incorrect1, incorrect2, incorrect3;

        if(fragmentFlag){
            questionView.setText(word);
            questionView.setBackgroundColor(80000000);
            correctAnswer = question.getJSONObject("translation").getString("translationText");
            incorrect1 = questions.getJSONObject(randomUniqueNumbers.get(1)).getJSONObject("translation").getString("translationText");
            incorrect2 = questions.getJSONObject(randomUniqueNumbers.get(2)).getJSONObject("translation").getString("translationText");
            incorrect3 = questions.getJSONObject(randomUniqueNumbers.get(3)).getJSONObject("translation").getString("translationText");
        }else{
            URL newUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) newUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();
            // download image
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Drawable dr = new BitmapDrawable(myBitmap);
            questionView.setBackground(dr);
            questionView.setText("");
            correctAnswer = question.getJSONObject("word").getString("text");
            incorrect1 = questions.getJSONObject(randomUniqueNumbers.get(1)).getJSONObject("word").getString("text");
            incorrect2 = questions.getJSONObject(randomUniqueNumbers.get(2)).getJSONObject("word").getString("text");
            incorrect3 = questions.getJSONObject(randomUniqueNumbers.get(3)).getJSONObject("word").getString("text");
        }
        List<String> answersStrings = new ArrayList<>();
        answersStrings.add(correctAnswer);
        answersStrings.add(incorrect1);
        answersStrings.add(incorrect2);
        answersStrings.add(incorrect3);

        return answersStrings;
    }


    void setParams(){
        try {
            fragmentFlag = !fragmentFlag;
            List<Integer> randomUniqueNumbers = RandomUtil.drawRandomNumbers(questions.length());
            int questionNumber = randomUniqueNumbers.get(0);
            JSONObject question = questions.getJSONObject(questionNumber);


            Answers answers = new ConcreteAnswers(prepareAnswers(question,randomUniqueNumbers), correctAnswer);
            Answers orderChange = new AnswersOrderChange(answers);
            Answers letterChange = new AnswersLetterChange(orderChange);

            List<String> answersNames = letterChange.getAnswers();
            // set answers
            answerButton1.setText(answersNames.get(0));
            answerButton2.setText(answersNames.get(1));
            answerButton3.setText(answersNames.get(2));
            answerButton4.setText(answersNames.get(3));

            questionStarted = new Date().getSeconds();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void handleAnswer(int chosenAnswer){
        buttonsEnabled(false);
        isCorrect = Objects.equals(chosenAnswer, 3);
        int time = new Date().getSeconds() - questionStarted;
        points.countPoints(isCorrect, time);

        // set back message
        handleAnswerReset();
    }
    void buttonsEnabled(boolean flag){
        answerButton1.setEnabled(flag);
        answerButton2.setEnabled(flag);
        answerButton3.setEnabled(flag);
        answerButton4.setEnabled(flag);
    }
    private void handleAnswerReset(){
        Log.d("playChallenge", String.valueOf(points.getScore()));
        if(++questionCounter > 10){

            // end challenge
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("key", "end");
            bundle.putInt("score", (int)points.getScore());
            intent.putExtras(bundle);
            getActivity().getIntent().putExtras(intent);
            getActivity().recreate();
        } else {
            // new question
            buttonsEnabled(true);
            setParams();
        }
    }
}
