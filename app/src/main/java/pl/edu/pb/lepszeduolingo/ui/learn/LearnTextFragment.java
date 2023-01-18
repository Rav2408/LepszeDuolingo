package pl.edu.pb.lepszeduolingo.ui.learn;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import pl.edu.pb.lepszeduolingo.R;

public class LearnTextFragment extends LearnFragment {
    TextView questionView;
    String word;
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
            translation = question.getJSONObject("translation").getString("translationText");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_learn_text, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        answerView1 = view.findViewById(R.id.answer_1_text);
        answerView2 = view.findViewById(R.id.answer_2_text);
        answerView3 = view.findViewById(R.id.answer_3_text);
        answerView4 = view.findViewById(R.id.answer_4_text);
        questionView = view.findViewById(R.id.questionView_text);
        // TODO: set random wrong answers
        // set view
        questionView.setText(word);
        answerView1.setText(((LearnActivity)getActivity()).orderChange.getAnswers().get(0));
        answerView2.setText(((LearnActivity)getActivity()).orderChange.getAnswers().get(1));
        answerView3.setText(((LearnActivity)getActivity()).orderChange.getAnswers().get(2));
        answerView4.setText(((LearnActivity)getActivity()).orderChange.getAnswers().get(3));
        // get answer
        getAnswer();
    }
}
