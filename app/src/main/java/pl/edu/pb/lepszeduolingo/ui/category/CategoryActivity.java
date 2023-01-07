package pl.edu.pb.lepszeduolingo.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.DrawerMainActivity;
import pl.edu.pb.lepszeduolingo.databinding.ActivityDifficultyBinding;
import pl.edu.pb.lepszeduolingo.ui.learn.LearnActivity;
import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;

public class CategoryActivity extends DrawerMainActivity implements DifficultyRecyclerViewAdapter.onCategoryListener{
    ActivityDifficultyBinding activityDifficultyBinding;
    int difficultyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDifficultyBinding = ActivityDifficultyBinding.inflate(getLayoutInflater());
        setContentView(activityDifficultyBinding.getRoot());
        // retrieve data fromm difficulty
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            difficultyId = extras.getInt("difficultyId");
        }
        DatabaseFacade databaseFacade = new DatabaseFacade(this);
        ArrayList<String> categories = databaseFacade.getCategoriesByDifficultyId(getIntent().getExtras().getInt("difficultyId"));

        RecyclerView recyclerView = findViewById(R.id.diffRecyclerView);
        DifficultyRecyclerViewAdapter adapter = new DifficultyRecyclerViewAdapter(this, categories, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    // set onClick
    @Override
    public void onCategoryClick(int position) {
        Intent intent = new Intent(this, LearnActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", position);
        bundle.putInt("difficultyId", difficultyId);
        intent.putExtras(bundle);
        startActivity(intent);
        Log.d("category", String.format("Category: %2d", position));
    }
}