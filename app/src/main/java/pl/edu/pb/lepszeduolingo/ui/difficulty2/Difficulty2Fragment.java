package pl.edu.pb.lepszeduolingo.ui.difficulty2;

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

import pl.edu.pb.lepszeduolingo.databinding.FragmentDifficulty2Binding;

public class Difficulty2Fragment extends Fragment {

    private Difficulty2ViewModel difficulty2ViewModel;
    private FragmentDifficulty2Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        difficulty2ViewModel =
                new ViewModelProvider(this).get(Difficulty2ViewModel.class);

        binding = FragmentDifficulty2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDifficulty2;
        difficulty2ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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