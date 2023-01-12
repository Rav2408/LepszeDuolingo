package pl.edu.pb.lepszeduolingo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import pl.edu.pb.lepszeduolingo.builder.JsonBuilder;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;

public class RegisterActivity extends AppCompatActivity {
    Button getBack;
    Button registerButton;
    EditText inputEmail, inputPassword, inputPasswordRe;
    String emailPattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    // test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // init
        inputEmail = findViewById(R.id.inputRegisterEmail);
        inputPassword = findViewById(R.id.inputRegisterPassword);
        inputPasswordRe = findViewById(R.id.inputRegisterPasswordRe);

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
                register();
            }
        });
    }
    private void register() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String passwordRe = inputPasswordRe.getText().toString();
        Context context = this;

        // user with that email
        VolleyRequest.getInstance(this, new IVolley() {
            @Override
            public void onResponse(String response) {
                if(response!=null && !response.isEmpty()){
                    inputEmail.setError("Email already exists");
                }else if(!email.matches(emailPattern) || email.isEmpty()){
                    inputEmail.setError("Enter correct email");
                } else if(password.isEmpty() || password.length() < 6){
                    inputPassword.setError("Enter correct password");
                } else if(!passwordRe.equals(password)){
                    inputPasswordRe.setError("Passwords differs");
                } else {
                    hashPasswordAndCreateUser(context, password, email);
                    startActivity(new Intent(RegisterActivity.this, TitleActivity.class));
                }
            }
        }).getRequestString("http://34.118.90.148:8090/api/duolingouser/salt?email=" + email);

    }

    private void hashPasswordAndCreateUser(Context context, String password, String email){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            byte[] base64Hash = Base64.getEncoder().encode(hash); //szyfrowanie
            byte[] base64Salt = Base64.getEncoder().encode(salt); //szyfrowanie

            String stringSalt = new String(base64Salt, StandardCharsets.UTF_8);
            String stringHash = new String(base64Hash, StandardCharsets.UTF_8);

            createUser(context, email ,stringHash, stringSalt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    private void createUser(Context context, String email, String stringHash, String stringSalt){
        VolleyRequest.getInstance(context, new IVolley() {
        }).postRequest("http://34.118.90.148:8090/api/duolingouser",
                new JsonBuilder(context).create()
                        .put("name", email)
                        .put("email", email)
                        .put("role", "USER")
                        .put("salt", stringSalt)
                        .put("hash", stringHash)
                        .build());
    }

}