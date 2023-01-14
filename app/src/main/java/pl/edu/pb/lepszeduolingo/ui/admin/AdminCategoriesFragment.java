package pl.edu.pb.lepszeduolingo.ui.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAdminCategoriesBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.ui.admin.add.AdminAddActivity;

public class AdminCategoriesFragment extends Fragment implements AdminCategories_RecyclerViewAdapter.onDataListener{
    private FragmentAdminCategoriesBinding binding;
    Button AddAdminCategoryBtn;
    JSONArray categories;
    View root;
    DatabaseFacade databaseFacade = new DatabaseFacade(getContext());
    ArrayList<String> categoriesData = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminCategoriesBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        // button
        AddAdminCategoryBtn = root.findViewById(R.id.addAdminCategoryBtn);
        AddAdminCategoryBtn.setOnClickListener(v -> onCategoryAdd());
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        // get categories
        databaseFacade.updateCategories();
        categories = databaseFacade.getCategories();
        for(int i=0; i<categories.length(); i++){
            try {
                categoriesData.add(categories.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // set recycler view
        RecyclerView recyclerView = root.findViewById(R.id.adminCategoriesRecyclerView);
        AdminCategories_RecyclerViewAdapter adapter = new AdminCategories_RecyclerViewAdapter(this, categoriesData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }
    void onCategoryAdd(){
        // add
        String activityOption = "category";
        Intent intent = new Intent(this.getActivity(), AdminAddActivity.class);
        intent.putExtra("key", activityOption);
        startActivity(intent);
    }
    @Override
    public void onCategoryClick(int position) {
        // edit

    }

    @Override
    public void onCategoryDelete(int position) {
        // delete
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("Do you want to delete category "+categoriesData.get(position)+"?")
                .setCancelable(false)
                .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
