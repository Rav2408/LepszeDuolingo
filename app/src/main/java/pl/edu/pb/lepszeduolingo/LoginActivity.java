package pl.edu.pb.lepszeduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import pl.edu.pb.lepszeduolingo.builder.WordJsonBuilder;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;
import pl.edu.pb.lepszeduolingo.ui.admin.AdminActivity;

public class LoginActivity extends AppCompatActivity {
    Button getBack;
    Button loginButton;
    EditText inputEmail, inputPassword;
    View progresBar;
    String currentSalt;
    JSONObject currentUser;
    String stringHash;
    DatabaseFacade databaseFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progresBar = findViewById(R.id.loadingPanel);
        progresBar.setVisibility(View.GONE);
        databaseFacade = new DatabaseFacade(this);

        // init
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        // Buttons logic
        getBack = (Button) findViewById(R.id.getBackLoginButton);
        getBack.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, TitleActivity.class))
        );
        loginButton = (Button) findViewById(R.id.confirmLoginButton);
        loginButton.setOnClickListener(v -> authentication());
    }


    private void authentication() {
        progresBar.setVisibility(View.VISIBLE);

        Context context = this;
        String email = inputEmail.getText().toString();

        VolleyRequest.getInstance(this, new IVolley() {
            @Override
            public void onResponse(String salt) {
                if(salt==null || salt.equals("")){
                    progresBar.setVisibility(View.INVISIBLE);
                    inputEmail.setError("Wrong Email");
                }else{
                    currentSalt = Optional.of(salt).orElseThrow(
                            ()-> new IllegalStateException("User with this email does not exists"));
                    Log.d("AUTH", "current salt: "+ currentSalt);
                    String password = inputPassword.getText().toString();
                    try {
                        byte[] decodedSalt = Base64.getDecoder().decode(new String(currentSalt).getBytes("UTF-8"));
                        KeySpec spec = new PBEKeySpec(password.toCharArray(), decodedSalt, 65536, 128);
                        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                        byte[] hash = factory.generateSecret(spec).getEncoded();
                        byte[] base64Hash = Base64.getEncoder().encode(hash);
                        stringHash = new String(base64Hash, StandardCharsets.UTF_8);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    VolleyRequest.getInstance(context, new IVolley() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("AUTH", "current user: "+ jsonObject.toString());
                            try {
                                if(!jsonObject.isNull("name")){
                                    currentUser = jsonObject;
                                    databaseFacade.setUser(currentUser);
                                    progresBar.setVisibility(View.INVISIBLE);
                                    authorization();
                                }else{
                                    progresBar.setVisibility(View.INVISIBLE);
                                    inputPassword.setError("Wrong Password");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        @Override
                        public void onResponse(String string) {
                        Log.d("AUTH", "current user: "+ string);
                                progresBar.setVisibility(View.INVISIBLE);
                                inputPassword.setError("Wrong Password");
                        }
                    }).postRequest("http://34.118.90.148:8090/api/duolingouser/auth",
                            new WordJsonBuilder(context).create().put("email",email).put("hash",stringHash).build());
                }
            }
        }).getRequestString("http://34.118.90.148:8090/api/duolingouser/salt?email=" + email);
    }

    private void authorization() throws JSONException {
        if(currentUser.getString("role").equals("ADMIN")){
            startActivity(new Intent(this, AdminActivity.class));
        }else{
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}


