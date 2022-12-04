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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentDifficulty1Binding;
import pl.edu.pb.lepszeduolingo.ui.dictionary.Dict_RecyclerViewAdapter;

public class Difficulty1Fragment extends Fragment {

    private Difficulty1ViewModel difficulty1ViewModel;
    private FragmentDifficulty1Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        difficulty1ViewModel =
                new ViewModelProvider(this).get(Difficulty1ViewModel.class);

        binding = FragmentDifficulty1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // set recycler view
        RecyclerView recyclerView = root.findViewById(R.id.diffRecyclerView);
        Diff_RecyclerViewAdapter adapter = new Diff_RecyclerViewAdapter();
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