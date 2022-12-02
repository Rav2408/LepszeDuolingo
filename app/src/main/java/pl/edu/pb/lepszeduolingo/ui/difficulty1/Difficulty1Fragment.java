package pl.edu.pb.lepszeduolingo.ui.difficulty1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import pl.edu.pb.lepszeduolingo.databinding.FragmentDifficulty1Binding;

public class Difficulty1Fragment extends Fragment {

    private Difficulty1ViewModel difficulty1ViewModel;
    private FragmentDifficulty1Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        difficulty1ViewModel =
                new ViewModelProvider(this).get(Difficulty1ViewModel.class);

        binding = FragmentDifficulty1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDifficulty1;
        difficulty1ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}