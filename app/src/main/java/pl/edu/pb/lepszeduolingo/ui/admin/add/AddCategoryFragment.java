package pl.edu.pb.lepszeduolingo.ui.admin.add;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.builder.JsonBuilder;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAddCategoryBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;

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
        publishBtn.setOnClickListener(v -> save(diffIds));
        return root;
    }
    private void save(List<Integer> diffIds){
        VolleyRequest.getInstance(getContext(), new IVolley() {
        }).postRequest("http://34.118.90.148:8090/api/category",
                new JsonBuilder(getContext()).create()
                        .put("name", addCategoryText.getText().toString())
                        .put("difficulties",
                                new JsonBuilder(getContext())
                                .create()
                                .put("id",diffIds.get(difficultiesSpinner.getSelectedItemPosition()))
                                .build()
                        )
                        .build());
        Log.d("categories", "publish");
        databaseFacade.updateCategories();
    }
}