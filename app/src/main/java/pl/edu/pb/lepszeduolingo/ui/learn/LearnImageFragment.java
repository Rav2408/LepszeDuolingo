package pl.edu.pb.lepszeduolingo.ui.learn;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import pl.edu.pb.lepszeduolingo.R;

public class LearnImageFragment extends LearnFragment {
    ImageView questionView;
    String word;
    String url;
    String translation;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // get params
        Bundle data = getArguments();
        String tempQuestion;
        assert data != null;
        tempQuestion = data.getString("Question");
        JSONObject question;
        try {
            question = new JSONObject(tempQuestion);
            word = question.getJSONObject("word").getString("text");
            url = question.getJSONObject("word").getString("imagePath");
            //translation = question.getJSONObject("translation").getString("translationText");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_learn_image, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        answerView1 = view.findViewById(R.id.answer_1_image);
        answerView2 = view.findViewById(R.id.answer_2_image);
        answerView3 = view.findViewById(R.id.answer_3_image);
        answerView4 = view.findViewById(R.id.answer_4_image);
        questionView = view.findViewById(R.id.imageView_image);
        // TODO: set random wrong answers
        // set view
        Picasso.get().load(url).fit().into(questionView);
        answerView1.setText("egg");
        answerView2.setText("meat");
        answerView3.setText("cheese");
        answerView4.setText(word);
        // get answer
        getAnswer();
    }
}
