package pl.edu.pb.lepszeduolingo.ui.dictionary;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
    private int unlockedWords = databaseFacade.getUnlockedWords().length();
    private int allWords = databaseFacade.getWords().length();
    private DictionaryViewModel dictionaryViewModel;
    private FragmentDictionaryBinding binding;
    TextView unlockedView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dictionaryViewModel =
                new ViewModelProvider(this).get(DictionaryViewModel.class);
        binding = FragmentDictionaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // set unlocked header
        unlockedView = root.findViewById(R.id.unlocked);
        String unlockedString = String.format("Unlocked %s / %s", unlockedWords,
                allWords);
        unlockedView.setText(unlockedString);
        handleWordCounter();
        // get list data
        JSONArray unlockedWords = databaseFacade.getUnlockedWords();
        ArrayList<String> wordsData = new ArrayList<>();
        for(int i=0;i<unlockedWords.length();i++){
            try {
                wordsData.add(unlockedWords.getJSONObject(i).getJSONObject("word").getString("text"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // set recycler view
        RecyclerView recyclerView = root.findViewById(R.id.dictRecyclerView);
        Dict_RecyclerViewAdapter adapter = new Dict_RecyclerViewAdapter(this, wordsData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }
    private void handleWordCounter(){
        // TODO: same problem as in the admin
        // have to reload to see update
        // user is global
        // dictionary shows item twice
        databaseFacade.updateUnlockedWords();
        unlockedWords = databaseFacade.getUnlockedWords().length();
        allWords = databaseFacade.getWords().length();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    // set onClick
    @Override
    public void onWordClick(int position) {
        // send parameter wordId and start activity word
        Intent intent = new Intent(this.getActivity(), WordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", position);
        intent.putExtras(bundle);
        startActivity(intent);
        Log.d(TAG, String.format("Word: %2d", position));
    }
}