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

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAddCategoryBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;

public class AddCategoryFragment extends Fragment {
    private FragmentAddCategoryBinding binding;
    EditText AddCategoryText;
    Spinner DifficultiesSpinner;
    Button PublishBtn;
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
        AddCategoryText = root.findViewById(R.id.addcategoryText);
        DifficultiesSpinner = root.findViewById(R.id.addcategoryDifficulty);
        PublishBtn = root.findViewById(R.id.addcategoryAddBtn);
        // db
        DatabaseFacade databaseFacade = new DatabaseFacade(getContext());
        // difficulties
        JSONArray difficulties = databaseFacade.getDifficulties();
        ArrayList<String> difficultiesData = new ArrayList<>();
        for(int i=0;i<difficulties.length();i++){
            try {
                difficultiesData.add(difficulties.getJSONObject(i).getString("level"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> diffAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, difficultiesData);
        diffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DifficultiesSpinner.setAdapter(diffAdapter);
        // publish
        PublishBtn.setOnClickListener(v -> performAuth());
        return root;
    }
    private void performAuth(){
        Log.d("categories", "publish");
    }
}