package pl.edu.pb.lepszeduolingo.ui.learn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Random;
import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;

public class LearnActivity extends AppCompatActivity implements AnswerListener{
    Fragment textFragment = new LearnTextFragment();
    Fragment imageFragment = new LearnImageFragment();
    Random rand = new Random();
    JSONArray questions;
    int questionNumber;
    boolean fragmentFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        // db
        DatabaseFacade databaseFacade = new DatabaseFacade(this);
        questions = databaseFacade.getQuestions();
        // set parameters for db
        setParams();
    }
    // set parameters for db
    void setParams(){
        try {
            // choose answer
            questionNumber = rand.nextInt(questions.length());
            JSONObject question = questions.getJSONObject(questionNumber);
            // rand if text or image
            Fragment fragmentOut = fragmentFlag ? textFragment : imageFragment;
            // pass data
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Bundle data = new Bundle();
            data.putString("Question", question.toString());
            fragmentOut.setArguments(data);
            fragmentTransaction.replace(R.id.flLearn, fragmentOut).commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // get flag from fragment
    @Override
    public void addIsCorrect(boolean isCorrect) {
        // for testing
        fragmentFlag = !fragmentFlag;
        setParams();
    }
}
