package pl.edu.pb.lepszeduolingo.ui.difficulties;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.DrawerMainActivity;
import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.ActivityDifficultiesBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseHelper;
import pl.edu.pb.lepszeduolingo.ui.difficulty.DifficultyActivity;

public class DifficultiesActivity extends DrawerMainActivity implements DifficultiesRecyclerViewAdapter.onDifficultyListener{
    ActivityDifficultiesBinding activityDifficultiesBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDifficultiesBinding = ActivityDifficultiesBinding.inflate(getLayoutInflater());
        setContentView(activityDifficultiesBinding.getRoot());

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        JSONArray difficulties = databaseHelper.getDifficulties();


        ArrayList<String> difficultiesData = new ArrayList<>();
        for(int i=0;i<difficulties.length();i++){
            try {
                difficultiesData.add(difficulties.getJSONObject(i).getString("level"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // test data

//        difficultiesData.add("A1");
//        difficultiesData.add("A2");
//        difficultiesData.add("A3");
//        difficultiesData.add("A4");
//        difficultiesData.add("B1");

        RecyclerView recyclerView = findViewById(R.id.difficultiesRecyclerView);
        DifficultiesRecyclerViewAdapter adapter = new DifficultiesRecyclerViewAdapter(this, difficultiesData, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onDifficultyClick(int position) {
        Intent intent = new Intent(this, DifficultyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", position);
        intent.putExtras(bundle);
        startActivity(intent);
        Log.d("difficulty", String.format("difficulty: %2d", position));
    }
}