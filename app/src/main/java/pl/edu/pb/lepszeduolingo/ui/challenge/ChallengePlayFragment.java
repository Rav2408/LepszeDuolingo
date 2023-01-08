package pl.edu.pb.lepszeduolingo.ui.challenge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentChallengePlayBinding;
import pl.edu.pb.lepszeduolingo.databinding.FragmentChallengeStartBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.models.Question;
import pl.edu.pb.lepszeduolingo.ui.learn.AnswerListener;

public class ChallengePlayFragment extends Fragment {
    FragmentChallengePlayBinding binding;
    AppCompatButton AnswerView_1;
    AppCompatButton AnswerView_2;
    AppCompatButton AnswerView_3;
    AppCompatButton AnswerView_4;
    TextView QuestionView;
    JSONArray questions;
    int questionCounter;
    boolean isCorrect;
    int score;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChallengePlayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // get elements
        AnswerView_1 = root.findViewById(R.id.challengeAnswer_1_text);
        AnswerView_2 = root.findViewById(R.id.challengeAnswer_2_text);
        AnswerView_3 = root.findViewById(R.id.challengeAnswer_3_text);
        AnswerView_4 = root.findViewById(R.id.challengeAnswer_4_text);
        QuestionView = root.findViewById(R.id.challengeQuestionView);
        // buttons
        AnswerView_1.setOnClickListener(v -> handleAnswer(0));
        AnswerView_2.setOnClickListener(v -> handleAnswer(1));
        AnswerView_3.setOnClickListener(v -> handleAnswer(2));
        AnswerView_4.setOnClickListener(v -> handleAnswer(3));
        // db
        DatabaseFacade databaseFacade = new DatabaseFacade(this.getActivity());
        questions = databaseFacade.getQuestions();
        // init
        // prevents internet privacy error
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setParams();
        return root;
    }
    void setParams(){
        try {
            // choose answer
            Random rand = new Random();
            int questionNumber = rand.nextInt(questions.length());
            JSONObject question = questions.getJSONObject(questionNumber);
            String word = question.getJSONObject("word").getString("text");
            String url = question.getJSONObject("word").getString("imagePath");
            // set question
            URL newUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) newUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();
            // download image
            // TODO: change it
            //  this take a lot of time
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Drawable dr = new BitmapDrawable(myBitmap);
            QuestionView.setBackground(dr);
            QuestionView.setText("");
            // set answers
            AnswerView_1.setText("answer 1");
            AnswerView_2.setText("answer 2");
            AnswerView_3.setText("answer 3");
            AnswerView_4.setText(word);
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
        if(isCorrect == true){
            score += 100;
        } else {
            score -= 10;
        }
        // set back message
        handleAnswerReset();
    }
    void buttonsEnabled(boolean flag){
        AnswerView_1.setEnabled(flag);
        AnswerView_2.setEnabled(flag);
        AnswerView_3.setEnabled(flag);
        AnswerView_4.setEnabled(flag);
    }
    private void handleAnswerReset(){
        if(questionCounter++ > 10){
            // end challenge
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("key", "end");
            bundle.putInt("score", score);
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
