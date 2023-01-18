package pl.edu.pb.lepszeduolingo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import pl.edu.pb.lepszeduolingo.builder.JsonBuilder;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;
import pl.edu.pb.lepszeduolingo.ui.admin.AdminActivity;

public class LoginActivity extends AppCompatActivity {
    Button getBack;
    Button loginButton;
    EditText inputEmail;
    EditText inputPassword;
    View progressBar;
    JSONObject currentUser;
    DatabaseFacade databaseFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.loadingPanel);
        progressBar.setVisibility(View.GONE);
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
        progressBar.setVisibility(View.VISIBLE);

        Context context = this;
        String email = inputEmail.getText().toString();

        VolleyRequest.getInstance(this, new IVolley() {
            @Override
            public void onResponse(String salt) {
                if(salt==null || salt.equals("")){
                    progressBar.setVisibility(View.INVISIBLE);
                    inputEmail.setError("Wrong Email");
                }else{
                    // get password
                    String stringHash = hashPassword(salt,inputPassword.getText().toString());
                    VolleyRequest.getInstance(context, new IVolley() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                                if(!jsonObject.isNull("name")){
                                    logInUser(jsonObject);
                                }else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    inputPassword.setError("Wrong Password");
                                }
                        }
                    }).postRequest("http://34.118.90.148:8090/api/duolingouser/auth",
                            new JsonBuilder(context).create().put("email",email).put("hash",stringHash).build());
                }
            }
        }).getRequestString("http://34.118.90.148:8090/api/duolingouser/salt?email=" + email);
    }

    private void logInUser(JSONObject user){
        // clear email input
        inputEmail.setText("");
        currentUser = user;
        databaseFacade.setUser(currentUser);
        databaseFacade.updateUnlockedWords();
        progressBar.setVisibility(View.INVISIBLE);
        authorization();
        // clear password input
        inputPassword.setText("");
    }

    private void authorization() {
        try {
            if(currentUser.getString("role").equals("ADMIN")){
                startActivity(new Intent(this, AdminActivity.class));
            }else{
                startActivity(new Intent(this, MainActivity.class));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String hashPassword(String salt, String password) {
        try {
            byte[] decodedSalt = Base64.getDecoder().decode(new String(salt).getBytes("UTF-8"));
            KeySpec spec = new PBEKeySpec(password.toCharArray(), decodedSalt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            byte[] base64Hash = Base64.getEncoder().encode(hash);
            String stringHash = new String(base64Hash, StandardCharsets.UTF_8);
            return stringHash;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}


