package pl.edu.pb.lepszeduolingo.ui.admin;

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
import org.json.JSONObject;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAdminWordsBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.ui.admin.add.AdminAddActivity;

public class AdminWordsFragment extends Fragment implements AdminWords_RecyclerViewAdapter.onDataListener{
    private FragmentAdminWordsBinding binding;
    JSONArray words;
    Button addButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminWordsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        addButton = root.findViewById(R.id.addAdminWordBtn);
        addButton.setOnClickListener(v -> onWordAdd());
        // get every word
        DatabaseFacade databaseFacade = new DatabaseFacade(getContext());
        words = databaseFacade.getWords();
        ArrayList<String> wordsData = new ArrayList<>();
        for(int i=0;i<words.length();i++){
            try {
                wordsData.add(words.getJSONObject(i).getString("text"));
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
        // delete
        try {
            JSONObject obj = words.getJSONObject(position);
            Log.d("admin_test", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            VolleyRequest.getInstance(this, new IVolley() {
//                @Override
//                public void onResponse(JSONObject jsonObject) {
//                    System.out.println(jsonObject.toString());
//                }
//            }).postRequest("http://34.118.90.148:8090/api/duolingouser",
//                    new JSONObject("{\"role\":\"USER\",\"name\":\"Testowy POST\"}"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}