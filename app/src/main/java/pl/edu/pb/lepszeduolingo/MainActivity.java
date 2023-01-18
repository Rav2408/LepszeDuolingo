package pl.edu.pb.lepszeduolingo;

import android.os.Bundle;

import pl.edu.pb.lepszeduolingo.databinding.ActivityMainBinding;
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
    }
}