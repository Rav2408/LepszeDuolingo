package pl.edu.pb.lepszeduolingo.ui.admin.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.net.URI;
import java.util.Objects;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.ui.admin.AdminFragment;

public class AdminAddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);

        Fragment addWordFragment = new AddWordFragment();
        Fragment addCategoryFragment = new AddCategoryFragment();
        Fragment addDifficultyFragment = new AddDifficultyFragment();
        Fragment addLanguageFragment = new AddLanguageFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String activityOption = extras.getString("key");
            if(Objects.equals(activityOption, "word")){
                fragmentTransaction.replace(R.id.flAdminAdd, addWordFragment).commit();
            }else if(Objects.equals(activityOption, "category")){
                fragmentTransaction.replace(R.id.flAdminAdd, addCategoryFragment).commit();
            }else if(Objects.equals(activityOption, "difficulty")){
                fragmentTransaction.replace(R.id.flAdminAdd, addDifficultyFragment).commit();
            }else if(Objects.equals(activityOption, "language")){
                fragmentTransaction.replace(R.id.flAdminAdd, addLanguageFragment).commit();
            }
        }
    }
    void showMessage(String message, boolean isBack){
        int messageDelay = 400;
        int switchDelay = 600;
        Toast answerMessage = Toast.makeText(this, message, Toast.LENGTH_LONG);
        answerMessage.show();
        // make toast disappear faster
        Handler handler = new Handler();
        handler.postDelayed(answerMessage::cancel, messageDelay);
        // get back to list view
        if(isBack){
            handler.postDelayed(() -> this.onBackPressed(), switchDelay);
        }
    }
}