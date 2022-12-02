package pl.edu.pb.lepszeduolingo.ui.difficulty3;

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

import pl.edu.pb.lepszeduolingo.databinding.FragmentDifficulty3Binding;
// data binding sometimes shows 'does not exists' which is gradle problem and does not affect app'
public class Difficulty3Fragment extends Fragment {

    private Difficulty3ViewModel difficulty3ViewModel;
    private FragmentDifficulty3Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        difficulty3ViewModel =
                new ViewModelProvider(this).get(Difficulty3ViewModel.class);

        binding = FragmentDifficulty3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDifficulty3;
        difficulty3ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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