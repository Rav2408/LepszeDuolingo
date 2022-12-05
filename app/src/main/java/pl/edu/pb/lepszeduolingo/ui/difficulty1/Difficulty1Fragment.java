package pl.edu.pb.lepszeduolingo.ui.difficulty1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pb.lepszeduolingo.LearnActivity;
import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentDifficulty1Binding;

public class Difficulty1Fragment extends Fragment implements Diff_RecyclerViewAdapter.onCategoryListener{

    private Difficulty1ViewModel difficulty1ViewModel;
    private FragmentDifficulty1Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        difficulty1ViewModel =
                new ViewModelProvider(this).get(Difficulty1ViewModel.class);

        binding = FragmentDifficulty1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // get recycler view
        RecyclerView recyclerView = root.findViewById(R.id.diffRecyclerView);
        // set recycler view
        Diff_RecyclerViewAdapter adapter = new Diff_RecyclerViewAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    // set onClick
    @Override
    public void onCategoryClick(int position) {
        // TODO: set a correct category
        startActivity(new Intent(this.getActivity(), LearnActivity.class));
        Log.d(TAG, String.format("Category: %2d", position));
    }
}