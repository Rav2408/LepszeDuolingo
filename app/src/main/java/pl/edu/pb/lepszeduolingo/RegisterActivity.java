package pl.edu.pb.lepszeduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Optional;

public class RegisterActivity extends AppCompatActivity {
    Button getBack;
    Button registerButton;
    EditText inputEmail, inputPassword, inputPasswordRe;
    String emailPattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    // test
    class User {
        public String email;
        public String password;
        User(String email, String password){
            this.email = email;
            this.password = password;
        }
        String getEmail(){
            return email;
        }
        String getPassword(){
            return password;
        }
    }
    ArrayList<User> data = new ArrayList<User>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // init
        inputEmail = findViewById(R.id.inputRegisterEmail);
        inputPassword = findViewById(R.id.inputRegisterPassword);
        inputPasswordRe = findViewById(R.id.inputRegisterPasswordRe);
        // test database
        data.add(new User("admin@gmail.com", "adminadmin"));
        // Buttons logic
        getBack = (Button) findViewById(R.id.getBackRegisterButton);
        getBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, TitleActivity.class));
            }
        });
        registerButton = (Button) findViewById(R.id.confirmRegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performAuth();
            }
        });
    }
    private void performAuth() {
        String email = inputEmail.getText().toString();
        // user with that email
        Optional<User> user = data.stream().filter(c -> c.getEmail().equals(email)).findAny();
        String password = inputPassword.getText().toString();
        String passwordRe = inputPasswordRe.getText().toString();
        if(user.isPresent()){
            inputEmail.setError("Email already exists");
        } else if(!email.matches(emailPattern) || email.isEmpty()){
            inputEmail.setError("Enter correct email");
        } else if(password.isEmpty() || password.length() < 6){
            inputPassword.setError("Enter correct password");
        } else if(!passwordRe.equals(password)){
            inputPasswordRe.setError("Passwords differs");
        } else {
            // success
            // TODO: add loading anim
            // TODO: add user to db
            startActivity(new Intent(RegisterActivity.this, TitleActivity.class));
        }
    }
}