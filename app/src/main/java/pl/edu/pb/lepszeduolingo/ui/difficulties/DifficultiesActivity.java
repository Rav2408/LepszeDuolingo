package pl.edu.pb.lepszeduolingo.ui.difficulties;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pl.edu.pb.lepszeduolingo.DrawerMainActivity;
import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.ActivityDifficultiesBinding;
import pl.edu.pb.lepszeduolingo.databinding.ActivityMainBinding;

public class DifficultiesActivity extends DrawerMainActivity {
    ActivityDifficultiesBinding activityDifficultiesBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDifficultiesBinding = ActivityDifficultiesBinding.inflate(getLayoutInflater());
        setContentView(activityDifficultiesBinding.getRoot());
        allocateActivityTitle("Difficulties");
    }
}