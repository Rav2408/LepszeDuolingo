package pl.edu.pb.lepszeduolingo.ui.admin.add;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.builder.Creator;
import pl.edu.pb.lepszeduolingo.builder.JsonBuilder;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAddCategoryBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;
import pl.edu.pb.lepszeduolingo.ui.admin.AdminActivity;
import pl.edu.pb.lepszeduolingo.ui.admin.AdminCategoriesFragment;

public class AddCategoryFragment extends Fragment {
    private FragmentAddCategoryBinding binding;
    EditText addCategoryText;
    Spinner difficultiesSpinner;
    Button publishBtn;
    DatabaseFacade databaseFacade;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // inputs
        addCategoryText = root.findViewById(R.id.addcategoryText);
        difficultiesSpinner = root.findViewById(R.id.addcategoryDifficulty);
        publishBtn = root.findViewById(R.id.addcategoryAddBtn);
        // db
        databaseFacade = new DatabaseFacade(getContext());
        // difficulties
        JSONArray difficulties = databaseFacade.getDifficulties();
        List<String> diffNames = new ArrayList<>();
        List<Integer> diffIds = new ArrayList<>();
        for(int i=0;i<difficulties.length();i++){
            try {
                diffNames.add(difficulties.getJSONObject(i).getString("level"));
                diffIds.add(difficulties.getJSONObject(i).getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> diffAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, diffNames);
        diffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultiesSpinner.setAdapter(diffAdapter);
        // publish
        publishBtn.setOnClickListener(v -> {
            // pass to db
            save(diffIds);
            // TODO: ask professor about db database problem
            // prompt
            ((AdminAddActivity)getActivity()).showMessage("Success", true, addCategoryText.getText().toString());
        });
        return root;
    }
    private void save(List<Integer> diffIds){
        VolleyRequest.getInstance(getContext(), new IVolley() {
        }).postRequest("http://34.118.90.148:8090/api/category",
                new Creator(getContext()).createCategory(
                        addCategoryText.getText().toString(),
                        diffIds.get(difficultiesSpinner.getSelectedItemPosition())
                ));

        databaseFacade.updateCategories();
    }
}