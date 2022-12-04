package pl.edu.pb.lepszeduolingo.ui.dictionary;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentDictionaryBinding;

public class DictionaryFragment extends Fragment {
    // test values
    private int unlockedWords = 20;
    private int allWords = 50;

    private DictionaryViewModel dictionaryViewModel;
    private FragmentDictionaryBinding binding;
    TextView unlockedView;

    @SuppressLint("DefaultLocale")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dictionaryViewModel =
                new ViewModelProvider(this).get(DictionaryViewModel.class);
        binding = FragmentDictionaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // set unlocked header
        unlockedView = root.findViewById(R.id.unlocked);
        String unlockedString = String.format("Unlocked %s / %s", String.valueOf(unlockedWords),
                String.valueOf(allWords));
        unlockedView.setText(unlockedString);
        // set recycler view
        RecyclerView recyclerView = root.findViewById(R.id.dictRecyclerView);
        Dict_RecyclerViewAdapter adapter = new Dict_RecyclerViewAdapter(this.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}