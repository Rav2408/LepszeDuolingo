package pl.edu.pb.lepszeduolingo.ui.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentDictionaryBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.ui.word.WordActivity;

public class DictionaryFragment extends Fragment implements Dict_RecyclerViewAdapter.onWordListener{
    DatabaseFacade databaseFacade = new DatabaseFacade(getContext());
    private int unlockedWordsCount;
    private int allWordsCount;
    private JSONArray unlockedWords;
    private FragmentDictionaryBinding binding;
    TextView unlockedView;
    Dict_RecyclerViewAdapter adapter;
    ArrayList<String> wordsData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDictionaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // elements
        unlockedView = root.findViewById(R.id.unlocked);
        RecyclerView recyclerView = root.findViewById(R.id.dictRecyclerView);
        // set recycler view
        setDictionaryData();
        adapter = new Dict_RecyclerViewAdapter(this, wordsData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }

    public void setDictionaryData() {
        databaseFacade.updateUnlockedWords();
        updateWordCounter();
        updateListData();
    }

    private void updateListData(){
        unlockedWords = databaseFacade.getUnlockedWords();
        wordsData = new ArrayList<>();
        for(int i=0;i<unlockedWords.length();i++){
            try {
                wordsData.add(unlockedWords.getJSONObject(i).getJSONObject("word").getString("text"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateWordCounter(){
        unlockedWordsCount = databaseFacade.getUnlockedWords().length();
        allWordsCount = databaseFacade.getWords().length();
        String unlockedString = String.format("Unlocked %s / %s", unlockedWordsCount,
                allWordsCount);
        unlockedView.setText(unlockedString);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onWordClick(int position) {
        // send parameter wordId and start activity word
        Intent intent = new Intent(this.getActivity(), WordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
