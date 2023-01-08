package pl.edu.pb.lepszeduolingo.ui.challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.Objects;

import pl.edu.pb.lepszeduolingo.R;

public class ChallengeActivity extends AppCompatActivity {
    String difficultyName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challange);
        // bundle
        Bundle extras = getIntent().getExtras();
        String activityOption = extras.getString("key");
        difficultyName = extras.getString("difficultyName");
        // get fragments
        Fragment challengeStartFragment = new ChallengeStartFragment();
        Fragment challengePlayFragment = new ChallengePlayFragment();
        Fragment challengeEndFragment = new ChallengeEndFragment();
        // set view
        Fragment fragment = challengeStartFragment;
        if(Objects.equals(activityOption, "start")){
            fragment = challengeStartFragment;
        }else if(Objects.equals(activityOption, "play")){
            fragment = challengePlayFragment;
        }else if(Objects.equals(activityOption, "end")){
            fragment = challengeEndFragment;
        }
        fragment.setArguments(extras);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flChallenge, fragment).commit();
    }
}
