package pl.edu.pb.lepszeduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import pl.edu.pb.lepszeduolingo.builder.WordJsonBuilder;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;

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
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            try {
                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                byte[] hash = factory.generateSecret(spec).getEncoded();
                byte[] base64Hash = Base64.getEncoder().encode(hash); //szyfrowanie
                byte[] base64Salt = Base64.getEncoder().encode(salt); //szyfrowanie

//                byte[] decodedString = Base64.getDecoder().decode(new String(name).getBytes("UTF-8"));
//                Log.d("AUTH","base64 odszyfr" + new String(decodedString));

                String stringSalt = new String(base64Salt, StandardCharsets.UTF_8);
                String stringHash = new String(base64Hash, StandardCharsets.UTF_8);

                VolleyRequest.getInstance(this, new IVolley() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        System.out.println(jsonObject.toString());
                    }
                }).postRequest("http://34.118.90.148:8090/api/duolingouser",
                        new WordJsonBuilder(this).create()
                                .put("name", email)
                                .put("email", email)
                                .put("role", "USER")
                                .put("salt", stringSalt)
                                .put("hash", stringHash)
                                .build());

                Log.d("AUTH", new String(hash, StandardCharsets.ISO_8859_1));

                Log.d("AUTH","Password: "+password+  " salt: " +new String(salt, StandardCharsets.US_ASCII)+ " hash: " + new String(hash, StandardCharsets.US_ASCII));
                Log.d("AUTH","Password: "+password+  " salt: " +new String(salt, StandardCharsets.ISO_8859_1)+ " hash: " + new String(hash, StandardCharsets.ISO_8859_1));
                Log.d("AUTH","Password: "+password+  " salt: " +new String(salt, StandardCharsets.UTF_16)+ " hash: " + new String(hash, StandardCharsets.UTF_16));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }

            startActivity(new Intent(RegisterActivity.this, TitleActivity.class));
        }
    }
}