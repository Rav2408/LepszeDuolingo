package pl.edu.pb.lepszeduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import pl.edu.pb.lepszeduolingo.ui.admin.AdminActivity;

public class LoginActivity extends AppCompatActivity {
    Button getBack;
    Button loginButton;
    EditText inputEmail, inputPassword;
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
    ArrayList<User>  data = new ArrayList<User>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // init
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        // test database
        data.add(new User("fajnyemail@gmail.com", "fajnehaslo123!"));
        data.add(new User("trudnyemail@gmail.com", "trudnehaslo"));
        data.add(new User("skrzynkapocztowa@gmail.com", "adminadmin"));
        data.add(new User("123", "123"));
        data.add(new User("admin", "admin"));
        // Buttons logic
        getBack = (Button) findViewById(R.id.getBackLoginButton);
        getBack.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, TitleActivity.class))
        );
        loginButton = (Button) findViewById(R.id.confirmLoginButton);
        loginButton.setOnClickListener(v -> performAuth());
    }
    private void performAuth() {
        String email = inputEmail.getText().toString();
        // user with that email
        Optional<User> user = data.stream().filter(c -> c.getEmail().equals(email)).findAny();
        String password = inputPassword.getText().toString();
        if(!user.isPresent()){
            inputEmail.setError("Wrong Email");
        } else if(!Objects.equals(user.get().getPassword(), password)){
            inputPassword.setError("Wrong Password");
        } else {
            // success
            // main test
            // startActivity(new Intent(this, MainActivity.class));
            // admin test
            startActivity(new Intent(this, AdminActivity.class));
        }
    }
}
