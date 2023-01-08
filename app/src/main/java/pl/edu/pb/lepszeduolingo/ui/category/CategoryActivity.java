package pl.edu.pb.lepszeduolingo.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.pb.lepszeduolingo.DrawerMainActivity;
import pl.edu.pb.lepszeduolingo.databinding.ActivityDifficultyBinding;
import pl.edu.pb.lepszeduolingo.ui.admin.add.AdminAddActivity;
import pl.edu.pb.lepszeduolingo.ui.challenge.ChallengeActivity;
import pl.edu.pb.lepszeduolingo.ui.learn.LearnActivity;
import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;

public class CategoryActivity extends DrawerMainActivity implements DifficultyRecyclerViewAdapter.onCategoryListener{
    ActivityDifficultyBinding activityDifficultyBinding;
    int difficultyId;
    String difficultyName;
    Button challengeBtn;
    List<JSONObject> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDifficultyBinding = ActivityDifficultyBinding.inflate(getLayoutInflater());
        setContentView(activityDifficultyBinding.getRoot());
        challengeBtn = findViewById(R.id.challengeButton);
        challengeBtn.setOnClickListener(v -> handleChallengeBtn());
        // retrieve data fromm difficulty
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            difficultyId = extras.getInt("difficultyId");
            difficultyName = extras.getString("difficultyName");
        }
        DatabaseFacade databaseFacade = new DatabaseFacade(this);
        categories = databaseFacade.getCategoriesByDifficultyId(getIntent().getExtras().getInt("difficultyId"));
        List<String> categoryNames = categories.stream().map(c-> {
            try {
                return c.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        RecyclerView recyclerView = findViewById(R.id.diffRecyclerView);
        DifficultyRecyclerViewAdapter adapter = new DifficultyRecyclerViewAdapter(this, categoryNames, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    // set onClick
    @Override
    public void onCategoryClick(int position) {
        Intent intent = new Intent(this, LearnActivity.class);
        Bundle bundle = new Bundle();
        try {
            bundle.putInt("categoryId", categories.get(position).getInt("id") );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.putExtras(bundle);
        startActivity(intent);
        Log.d("category", String.format("Category: %2d", position));
    }
    private void handleChallengeBtn(){
        // add
        String activityOption = "start";
        Intent intent = new Intent(this, ChallengeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("difficultyName", difficultyName);
        bundle.putString("key", activityOption);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}