package pl.edu.pb.lepszeduolingo;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.edu.pb.lepszeduolingo.databinding.ActivityMainBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseHelper;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;
import pl.edu.pb.lepszeduolingo.ui.dictionary.DictionaryFragment;

public class MainActivity extends DrawerMainActivity {

    ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        databaseHelper.printConsole();

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        // set dictionary fragment(home)
        if(findViewById(R.id.fragmentDictionary) != null){
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentDictionary, new DictionaryFragment(), null).commit();
        }


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
}