package pl.edu.pb.lepszeduolingo;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.edu.pb.lepszeduolingo.builder.JSONDataBuilder;
import pl.edu.pb.lepszeduolingo.builder.WordJsonBuilder;
import pl.edu.pb.lepszeduolingo.databinding.ActivityMainBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;
import pl.edu.pb.lepszeduolingo.ui.dictionary.DictionaryFragment;

public class MainActivity extends DrawerMainActivity {

    ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //databaseHelper.printConsole();

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        // set dictionary fragment(home)
        if(findViewById(R.id.fragmentDictionary) != null){
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentDictionary, new DictionaryFragment(), null).commit();
        }



//        JSONDataBuilder jsonDataBuilder = new WordJsonBuilder(this);
//        System.out.println(jsonDataBuilder.create().put("lalala", "lalala").put("word", new WordJsonBuilder(this).create().put("lalala", "lalala").build()).build());

        //TODO przykładowe zapytanie POST - aby z niego skorzystać należy podać odpowiedni adres i body czyli JSONObject
//        try {
//            VolleyRequest.getInstance(this, new IVolley() {
//                @Override
//                public void onResponse(JSONObject jsonObject) {
//                    System.out.println(jsonObject.toString());
//                }
//            }).postRequest("http://34.118.90.148:8090/api/duolingouser",
//                    new JSONObject("{\"role\":\"USER\",\"name\":\"Testowy POST\"}"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }
}