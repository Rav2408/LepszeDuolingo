package pl.edu.pb.lepszeduolingo.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAdminDifficultiesBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.ui.admin.add.AdminAddActivity;

public class AdminDifficultiesFragment extends Fragment implements AdminDifficulties_RecyclerViewAdapter.onDataListener{
    private FragmentAdminDifficultiesBinding binding;
    Button AddAdminDifficultiesBtn;
    JSONArray difficulties;
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminDifficultiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // button
        AddAdminDifficultiesBtn = root.findViewById(R.id.addAdminDifficultyBtn);
        AddAdminDifficultiesBtn.setOnClickListener(v -> onDifficultyAdd());
        // get categories
        DatabaseFacade databaseFacade = new DatabaseFacade(getContext());
        difficulties = databaseFacade.getDifficulties();
        ArrayList<String> difficultiesData = new ArrayList<>();
        for(int i=0; i<difficulties.length(); i++){
            try {
                difficultiesData.add(difficulties.getJSONObject(i).getString("level"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // set recycler view
        RecyclerView recyclerView = root.findViewById(R.id.adminDifficultiesRecyclerView);
        AdminDifficulties_RecyclerViewAdapter adapter = new AdminDifficulties_RecyclerViewAdapter(this, difficultiesData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }

    private void onDifficultyAdd() {
        // add
        String activityOption = "difficulty";
        Intent intent = new Intent(this.getActivity(), AdminAddActivity.class);
        intent.putExtra("key", activityOption);
        startActivity(intent);
    }

    @Override
    public void onDifficultyClick(int position) {
        // edit

    }

    @Override
    public void onDifficultyDelete(int position) {
        // delete

    }
}