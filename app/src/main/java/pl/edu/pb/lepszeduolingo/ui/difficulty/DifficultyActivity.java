package pl.edu.pb.lepszeduolingo.ui.difficulty;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.DrawerMainActivity;
import pl.edu.pb.lepszeduolingo.LearnActivity;
import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.ActivityDifficultyBinding;

public class DifficultyActivity extends DrawerMainActivity implements DifficultyRecyclerViewAdapter.onCategoryListener{
    ActivityDifficultyBinding activityDifficultyBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDifficultyBinding = ActivityDifficultyBinding.inflate(getLayoutInflater());
        setContentView(activityDifficultyBinding.getRoot());

        // test data
        ArrayList<String> difficultyData = new ArrayList<>();
        difficultyData.add("Birds");
        difficultyData.add("Sloths");
        difficultyData.add(":{");

        RecyclerView recyclerView = findViewById(R.id.diffRecyclerView);
        DifficultyRecyclerViewAdapter adapter = new DifficultyRecyclerViewAdapter(this, difficultyData, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    // set onClick
    @Override
    public void onCategoryClick(int position) {
        Intent intent = new Intent(this, LearnActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", position);
        intent.putExtras(bundle);
        startActivity(intent);
        Log.d("category", String.format("Category: %2d", position));
    }
}