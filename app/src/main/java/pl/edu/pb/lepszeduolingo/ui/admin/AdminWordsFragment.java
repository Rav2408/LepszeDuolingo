package pl.edu.pb.lepszeduolingo.ui.admin;

import static pl.edu.pb.lepszeduolingo.Constants.URL;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAdminWordsBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;
import pl.edu.pb.lepszeduolingo.ui.admin.add.AdminAddActivity;


public class AdminWordsFragment extends Fragment implements AdminWords_RecyclerViewAdapter.onDataListener{
    private FragmentAdminWordsBinding binding;
    JSONArray words;
    Button addButton;
    ArrayList<String> wordsData = new ArrayList<>();
    ArrayList<Integer> wordsIds = new ArrayList<>();
    DatabaseFacade databaseFacade;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminWordsBinding.inflate(inflater, container, false);
        databaseFacade = new DatabaseFacade(getContext());
        View root = binding.getRoot();
        addButton = root.findViewById(R.id.addAdminWordBtn);
        addButton.setOnClickListener(v -> onWordAdd());
        // get every word
        DatabaseFacade databaseFacade = new DatabaseFacade(getContext());
        words = databaseFacade.getWords();
        for(int i=0;i<words.length();i++){
            try {
                wordsData.add(words.getJSONObject(i).getString("text"));
                wordsIds.add(words.getJSONObject(i).getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // set recycler view
        RecyclerView recyclerView = root.findViewById(R.id.adminWordsRecyclerView);
        AdminWords_RecyclerViewAdapter adapter = new AdminWords_RecyclerViewAdapter(this, wordsData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }
    void onWordAdd(){
        // add
        String activityOption = "word";
        Intent intent = new Intent(this.getActivity(), AdminAddActivity.class);
        intent.putExtra("key", activityOption);
        startActivity(intent);
    }
    @Override
    public void onWordClick(int position) {
        // edit
        Log.d("admin_test", "edit");
    }
    @Override
    public void onWordDelete(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("Do you want to delete category "+wordsData.get(position)+"?")
                .setCancelable(false)
                .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        VolleyRequest.getInstance(getContext(), new IVolley() {
                            @Override
                            public void onResponse() {
                                databaseFacade.updateWords();
                                //TODO tutaj
                            }
                        }).deleteRequest(URL +"word/" + wordsIds.get(position));
                        //TODO lub tutaj, zależy jak wygodniej trzeba z listy słów które wyświetlają się, usunąć GUI tego słowa(chodzi mi po prostu o element na liście) bo to co wyżej usuwa na serwerze
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}