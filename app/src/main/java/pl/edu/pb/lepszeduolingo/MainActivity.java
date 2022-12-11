package pl.edu.pb.lepszeduolingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.edu.pb.lepszeduolingo.databinding.ActivityMainBinding;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MenuItem logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dictionary,
                R.id.nav_level)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //TODO przykładowe zapytanie GET - aby z niego skorzystać należy odpalić server i zmienić tutaj adres IP
        VolleyRequest.getInstance(this, new IVolley() {
//            public void onResponse(String responde) {
//                //Show result from API
//                //Toast.makeText(getActivity(),""+responde,Toast.LENGTH_SHORT).show();
//                System.out.println("lalalal"+responde);
//                try {
//                    //JSONObject obj = new JSONObject(responde);
//                    JSONArray array = new JSONArray(responde);
//                    JSONObject obj = array.getJSONObject(0);
//                    System.out.println(obj.getString("text"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }

            @Override
            public void onResponse(JSONArray jsonArray) {
                System.out.println("lalalal");
                try {
                    //JSONObject obj = new JSONObject(responde);
                    //JSONArray array = new JSONArray(responde);
                    JSONObject obj = jsonArray.getJSONObject(0);
                    System.out.println(obj.getString("text"));
                    System.out.println(obj.getString("imagePath"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).getRequest("http://82.139.136.139:8090/api/word"); //TODO swój adres

//        //    final TextView textView = (TextView) findViewById(R.id.text);
//// ...
//
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://192.168.56.1:8091/example";
//
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Toast.makeText(MainActivity.this, response.toString(),Toast.LENGTH_LONG);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "Something Wrong",Toast.LENGTH_LONG);
//            }
//        });
//        queue.add(request);
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
////                    textView.setText("Response is: " + response.substring(0,500));
//                        System.out.println("Response is: " + response.substring(0,500));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////            textView.setText("That didn't work!");
//                error.printStackTrace();
//                //System.out.println("Response is: " + ;);
//            }
//        });
//
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        logoutButton = menu.findItem(R.id.action_logout);
        logoutButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, TitleActivity.class));
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}