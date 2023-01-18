package pl.edu.pb.lepszeduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import pl.edu.pb.lepszeduolingo.ui.category.CategoryActivity;
import pl.edu.pb.lepszeduolingo.ui.difficulty.DifficultyActivity;

public class StartMenuActivity extends AppCompatActivity {
    Button dictionaryButton;
    Button playButton;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        // elements
        dictionaryButton = findViewById(R.id.starter_dictionary_btn);
        playButton = findViewById(R.id.starter_play_btn);
        logoutButton = findViewById(R.id.starter_logout_btn);
        // buttons
        dictionaryButton.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        playButton.setOnClickListener(v -> startActivity(new Intent(this, DifficultyActivity.class)));
        logoutButton.setOnClickListener(v -> startActivity(new Intent(this, TitleActivity.class)));
    }
}