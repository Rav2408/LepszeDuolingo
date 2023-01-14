package pl.edu.pb.lepszeduolingo.ui.admin.add;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAddDifficultyBinding;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAddLanguageBinding;

public class AddLanguageFragment extends Fragment {
    private FragmentAddLanguageBinding binding;
    EditText AddLanguageText;
    Button PublishBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddLanguageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // inputs
        AddLanguageText = root.findViewById(R.id.addlanguageText);
        PublishBtn = root.findViewById(R.id.addlanguageAddBtn);
        // publish
        PublishBtn.setOnClickListener(v -> {
            performAuth();
            // prompt
            ((AdminAddActivity)getActivity()).showMessage("Success", true);
        });
        return root;
    }
    private void performAuth(){
        Log.d("language", AddLanguageText.getText().toString());
    }
}