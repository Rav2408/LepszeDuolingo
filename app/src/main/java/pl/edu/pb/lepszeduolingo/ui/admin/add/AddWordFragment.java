package pl.edu.pb.lepszeduolingo.ui.admin.add;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import pl.edu.pb.lepszeduolingo.R;

import pl.edu.pb.lepszeduolingo.databinding.FragmentAddWordBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseHelper;

public class AddWordFragment extends Fragment
        implements
        AddWordTranslationsDialog.AddWordTranslationDialogListener,
        AddWordTranslationsAdapter.AddWordTranslationsAdapterListener {
    private static final int UPLOAD_ID = 1000;
    private Uri selectedImage;
    private AddWordTranslationsAdapter translationsAdapter;
    EditText AddWordText;
    EditText AddWordTranslation;
    ImageButton UploadImageBtn;
    Spinner DifficultiesSpinner;
    Spinner LanguageSpinner;
    Button PublishBtn;
    ImageButton TranslationBtn;
    ListView TranslationsListView;
    LinkedHashMap<String, String> TranslationsTemp = new LinkedHashMap<String, String>();

    private FragmentAddWordBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddWordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // text inputs
        AddWordText = root.findViewById(R.id.addwordText);
        // list
        // test values
        TranslationsTemp.put("Word", "test");
        TranslationsTemp.put("Slowo", "Tlumaczenia");
        TranslationsListView = root.findViewById(R.id.addwordTranslationList);
        translationsAdapter = new AddWordTranslationsAdapter(
                this.getActivity(),
                TranslationsTemp,
                this);
        TranslationsListView.setAdapter(translationsAdapter);
        // button
        UploadImageBtn = root.findViewById(R.id.addwordImage);
        UploadImageBtn.setOnClickListener(v -> handleAddImage());
        TranslationBtn = root.findViewById(R.id.addwordTranslationBtn);
        TranslationBtn.setOnClickListener(v -> handleAddTranslation());
        PublishBtn = root.findViewById(R.id.addwordAddBtn);
        PublishBtn.setOnClickListener(v -> performAuth());
        // spinners
        DifficultiesSpinner = root.findViewById(R.id.addwordDifficulty);
        LanguageSpinner = root.findViewById(R.id.addwordLanguage);
        // db
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this.getContext());
        JSONArray difficulties = databaseHelper.getDifficulties();
        JSONArray languages = databaseHelper.getLanguages();
        ArrayList<String> difficultiesData = new ArrayList<>();
        ArrayList<String> languagesData = new ArrayList<>();
        for(int i=0;i<difficulties.length();i++){
            try {
                difficultiesData.add(difficulties.getJSONObject(i).getString("level"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<languages.length();i++){
            try {
                languagesData.add(languages.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> diffAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, difficultiesData);
        ArrayAdapter<String> lanAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, languagesData);
        diffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DifficultiesSpinner.setAdapter(diffAdapter);
        LanguageSpinner.setAdapter(lanAdapter);
        return root;
    }
    private void performAuth(){
        String wordOrigin = AddWordText.getText().toString();
        /*String imagePath = selectedImage.getPath();*/
        String wordDiff = DifficultiesSpinner.getSelectedItem().toString();
        String wordLan = LanguageSpinner.getSelectedItem().toString();
        if(wordOrigin.isEmpty()){
            AddWordText.setError("Word is missing");
        } else if(wordOrigin.length() > 15){
            AddWordText.setError("Word is too long");
        } else if(selectedImage == null){
            Toast toast = Toast.makeText(this.getActivity(), "Add image", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Log.d("wordPublish", wordOrigin);
            Log.d("wordPublish", selectedImage.toString());
            Log.d("wordPublish", wordDiff);
            Log.d("wordPublish", wordLan);
            for(String word: TranslationsTemp.keySet()){
                Log.d("wordPublish", word);
            }
        }
    }
    private void handleAddImage(){
        Intent iGallery = new Intent(Intent.ACTION_PICK);
        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iGallery, UPLOAD_ID);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == UPLOAD_ID && data != null){
            selectedImage = data.getData();
            UploadImageBtn.setImageURI(selectedImage);
        }
    }
    private void handleAddTranslation(){
        AddWordTranslationsDialog translationDialog = new AddWordTranslationsDialog();
        translationDialog.setTargetFragment(this, 0);
        translationDialog.show(getActivity().getSupportFragmentManager(), "translation dialog");
    }
    // get translation from dialog
    @Override
    public void passChoice(String translation, String language) {
        TranslationsTemp.put(translation, language);
        updateTranslationList();
        Log.d("translationTemp", translation+language);
    }
    // update adapter list
    private void updateTranslationList() {
        translationsAdapter.setList(TranslationsTemp);
        translationsAdapter.notifyDataSetChanged();
    }
    // get updated list from list adapter
    @Override
    public void updateList(LinkedHashMap<String, String> translationsTemp) {
        for(String word: translationsTemp.keySet()){
            Log.d("translationReceive", word);
        }
    }
}