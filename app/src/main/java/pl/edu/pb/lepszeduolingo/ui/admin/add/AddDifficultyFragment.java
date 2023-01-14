package pl.edu.pb.lepszeduolingo.ui.admin.add;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAddCategoryBinding;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAddDifficultyBinding;

public class AddDifficultyFragment extends Fragment {
    private FragmentAddDifficultyBinding binding;
    EditText AddDifficultyText;
    Button PublishBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddDifficultyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // inputs
        AddDifficultyText = root.findViewById(R.id.adddifficultyText);
        PublishBtn = root.findViewById(R.id.adddifficultyAddBtn);
        // publish
        PublishBtn.setOnClickListener(v -> {
            performAuth();
            // prompt
            ((AdminAddActivity)getActivity()).showMessage("Success", true);
        });
        return root;
    }
    private void performAuth(){
        Log.d("difficulty", AddDifficultyText.getText().toString());
    }
}