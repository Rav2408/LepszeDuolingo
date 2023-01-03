package pl.edu.pb.lepszeduolingo.ui.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAdminBinding;
import pl.edu.pb.lepszeduolingo.databinding.FragmentDictionaryBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseHelper;
import pl.edu.pb.lepszeduolingo.ui.dictionary.Dict_RecyclerViewAdapter;
import pl.edu.pb.lepszeduolingo.ui.dictionary.DictionaryViewModel;

public class AdminFragment extends Fragment implements Admin_RecyclerViewAdapter.onDataListener{
    private FragmentAdminBinding binding;
    Fragment wordsFragment = new AdminWordsFragment();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // set recycler view
        RecyclerView recyclerView = root.findViewById(R.id.adminRecyclerView);
        Admin_RecyclerViewAdapter adapter = new Admin_RecyclerViewAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDataClick(int position) {
        if(position == 0){
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flAdmin, wordsFragment).addToBackStack(null).commit();
        }
    }
}