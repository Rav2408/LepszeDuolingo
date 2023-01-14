package pl.edu.pb.lepszeduolingo.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAdminBinding;

public class AdminFragment extends Fragment implements Admin_RecyclerViewAdapter.onDataListener{
    private FragmentAdminBinding binding;
    Fragment wordsFragment = new AdminWordsFragment();
    Fragment categoriesFragment = new AdminCategoriesFragment();
    Fragment difficultiesFragment = new AdminDifficultiesFragment();
    Fragment languagesFragment = new AdminLanguagesFragment();
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
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        if(position == 0){
            fragmentTransaction.replace(R.id.flAdmin, wordsFragment, "words").addToBackStack(null).commit();
        }else if(position == 1){
            fragmentTransaction.replace(R.id.flAdmin, categoriesFragment, "categories").addToBackStack(null).commit();
        }else if(position == 2){
            fragmentTransaction.replace(R.id.flAdmin, difficultiesFragment, "difficulties").addToBackStack(null).commit();
        }else if(position == 3){
            fragmentTransaction.replace(R.id.flAdmin, languagesFragment, "languages").addToBackStack(null).commit();
        }
    }
}