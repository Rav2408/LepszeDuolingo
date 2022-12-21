package pl.edu.pb.lepszeduolingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import pl.edu.pb.lepszeduolingo.builder.WordJsonBuilder;
import pl.edu.pb.lepszeduolingo.db.ResponseListener;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;

public class LoginActivity extends AppCompatActivity implements ResponseListener {
    Button getBack;
    Button loginButton;
    EditText inputEmail, inputPassword;
    View progresBar;
    String currentSalt;
    JSONObject currentUser;
    String stringHash;

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
        progresBar = findViewById(R.id.loadingPanel);
        progresBar.setVisibility(View.GONE);

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
        //loginButton.setOnClickListener(v -> new YourAsyncTask(this,this).execute());
    }

//    public void startEvent() {
//        new YourAsyncTask(this,this).execute();
//    }

    @Override
    public void onEventCompleted() {

    }

    @Override
    public void onEventFailed() {

    }

    private void performAuth() {    //TODO pobranie pobranie salt dla danego użytkownika, wygenerowanie hash'a i porównanie ich
        progresBar.setVisibility(View.VISIBLE);

        Context context = this;
        String email = inputEmail.getText().toString();

        VolleyRequest.getInstance(this, new IVolley() {
            @Override
            public void onResponse(String salt) {
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
                        if(jsonObject!=null){
                            currentUser = jsonObject;
                            Log.d("AUTH", jsonObject.toString());
                            progresBar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(context, MainActivity.class));
                        }else{
                            progresBar.setVisibility(View.INVISIBLE);
                            inputPassword.setError("Wrong Password");
                        }
                    }
                }).postRequest("http://34.118.90.148:8090/api/duolingouser/auth",
                        new WordJsonBuilder(context).create().put("email",email).put("hash",stringHash).build());

            }
        }).getRequestString("http://34.118.90.148:8090/api/duolingouser/salt?email=" + email);



//        String email = inputEmail.getText().toString();
//        // user with that email
//        Optional<User> user = data.stream().filter(c -> c.getEmail().equals(email)).findAny();
//        String password = inputPassword.getText().toString();
//        if(!user.isPresent()){
//            inputEmail.setError("Wrong Email");
//        } else if(!Objects.equals(user.get().getPassword(), password)){
//            inputPassword.setError("Wrong Password");
//        } else {
//            // success
//            // main test
//            startActivity(new Intent(this, MainActivity.class));
//            // admin test
//            //startActivity(new Intent(this, AdminActivity.class));
//        }
    }



//    private class YourAsyncTask extends AsyncTask<Void, Void, Void> {
//        ResponseListener callback;
//        Context context;
//        String stringHash;
//
//        public YourAsyncTask(Context context, ResponseListener responseListener) {
//            this.context = context;
//            this.callback = responseListener;
//        }
//
//        @Override
//        protected Void doInBackground(Void... args) {
//            // code where data is processing
//
//            String email = inputEmail.getText().toString();
//            VolleyRequest.getInstance(context, new IVolley() {
//                @Override
//                public void onResponse(String salt) {
//                    currentSalt = Optional.of(salt).orElseThrow(
//                            ()-> new IllegalStateException("User with this email does not exists"));
//                    String password = inputPassword.getText().toString();
//                    KeySpec spec = new PBEKeySpec(password.toCharArray(), currentSalt.getBytes(StandardCharsets.UTF_8), 65536, 128);
//                    try {
//                        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//                        byte[] hash = factory.generateSecret(spec).getEncoded();
//                        stringHash = new String(hash, StandardCharsets.UTF_8);
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (InvalidKeySpecException e) {
//                        e.printStackTrace();
//                    }
//
//                    VolleyRequest.getInstance(context, new IVolley() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        if(jsonObject!=null){
//                            currentUser = jsonObject;
//                        }
//                    }
//                    }).postRequest("http://34.118.90.148:8090/api/duolingouser/auth?email=" + email
//                            +"&hash=" + stringHash, null);
//
//                }
//            }).getRequest("http://34.118.90.148:8090/api/duolingouser/salt?email" + email);
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            progresBar.setVisibility(View.INVISIBLE);
//            if(callback != null){
//                callback.onEventCompleted();
//                Log.d("AUTH",currentSalt.toString() +" \n"+ currentUser.toString());
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progresBar.setVisibility(View.VISIBLE);
//        }
//    }
}
