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
import pl.edu.pb.lepszeduolingo.databinding.FragmentAdminLanguagesBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.ui.admin.add.AdminAddActivity;

public class AdminLanguagesFragment extends Fragment  implements AdminLanguages_RecyclerViewAdapter.onDataListener{
    private FragmentAdminLanguagesBinding binding;
    Button AddAdminLanguagesBtn;
    JSONArray languages;
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminLanguagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // button
        AddAdminLanguagesBtn = root.findViewById(R.id.addAdminLanguageBtn);
        AddAdminLanguagesBtn.setOnClickListener(v -> onLanguageAdd());
        // get languages
        DatabaseFacade databaseFacade = new DatabaseFacade(getContext());
        languages = databaseFacade.getLanguages();
        ArrayList<String> languagesData = new ArrayList<>();
        for(int i=0; i < languages.length(); i++){
            try {
                languagesData.add(languages.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // set recycler view
        RecyclerView recyclerView = root.findViewById(R.id.adminLanguagesRecyclerView);
        AdminLanguages_RecyclerViewAdapter adapter = new AdminLanguages_RecyclerViewAdapter(this, languagesData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }

    private void onLanguageAdd() {
        // add
        String activityOption = "language";
        Intent intent = new Intent(this.getActivity(), AdminAddActivity.class);
        intent.putExtra("key", activityOption);
        startActivity(intent);
    }

    @Override
    public void onLanguageClick(int position) {
        // edit
    }

    @Override
    public void onLanguageDelete(int position) {
        // delete
    }
}
