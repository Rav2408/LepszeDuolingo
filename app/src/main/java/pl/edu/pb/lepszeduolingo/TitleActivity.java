package pl.edu.pb.lepszeduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pl.edu.pb.lepszeduolingo.db.DatabaseHelper;

// lets you login or register
public class TitleActivity extends AppCompatActivity {
    Button loginButton;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        //databaseHelper.printConsole();

        loginButton = (Button) findViewById(R.id.buttonLogin);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(TitleActivity.this, LoginActivity.class));
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(TitleActivity.this, RegisterActivity.class));
            }
        });
    }
}