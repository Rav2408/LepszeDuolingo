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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.R;

import pl.edu.pb.lepszeduolingo.databinding.FragmentAddWordBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseHelper;

public class AddWordFragment extends Fragment {
    private static final int UPLOAD_ID = 1000;
    private Uri selectedImage;
    EditText AddWordText;
    EditText AddWordTranslation;
    ImageButton UploadImageBtn;
    Spinner DifficultiesSpinner;
    Spinner CategorySpinner;
//    Spinner LanguageSpinner;
    Button PublishBtn;

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
        AddWordTranslation = root.findViewById(R.id.addwordTranslation);
        // button
        UploadImageBtn = root.findViewById(R.id.addwordImage);
        UploadImageBtn.setOnClickListener(v -> {
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, UPLOAD_ID);
        });
        // spinners
        DifficultiesSpinner = root.findViewById(R.id.addwordDifficulty);
        CategorySpinner = root.findViewById(R.id.addwordCategory);
//        LanguageSpinner = root.findViewById(R.id.addwordLanguage);
        PublishBtn = root.findViewById(R.id.addwordAddBtn);
        PublishBtn.setOnClickListener(v -> performAuth());
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this.getContext());
        // difficulties
        JSONArray difficulties = databaseHelper.getDifficulties();
        JSONArray categories = databaseHelper.getCategories();
//        JSONArray languages = databaseHelper.getLanguages();
        ArrayList<String> difficultiesData = new ArrayList<>();
        ArrayList<String> categoriesData = new ArrayList<>();
        for(int i=0;i<difficulties.length();i++){
            try {
                difficultiesData.add(difficulties.getJSONObject(i).getString("level"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<categories.length();i++){
            try {
                categoriesData.add(categories.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> diffAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, difficultiesData);
        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categoriesData);
        diffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DifficultiesSpinner.setAdapter(diffAdapter);
        CategorySpinner.setAdapter(catAdapter);

        return root;
    }
    private void performAuth(){
        String wordOrigin = AddWordText.getText().toString();
        String wordTranslation = AddWordTranslation.getText().toString();
        /*String imagePath = selectedImage.getPath();*/
        String wordDiff = DifficultiesSpinner.getSelectedItem().toString();
        String wordCat = CategorySpinner.getSelectedItem().toString();
        if(wordOrigin.isEmpty()){
            AddWordText.setError("Word is missing");
        } else if(wordTranslation.isEmpty()){
            AddWordTranslation.setError("Translation is missing");
        } else if(wordOrigin.length() > 15){
            AddWordText.setError("Word is too long");
        } else if(wordTranslation.length() > 15){
            AddWordTranslation.setError("Translation is too long");
        } else if(selectedImage == null){
            Toast toast = Toast.makeText(this.getActivity(), "Add image", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Log.d("wordPublish", wordOrigin);
            Log.d("wordPublish", wordTranslation);
            Log.d("wordPublish", selectedImage.toString());
            Log.d("wordPublish", wordDiff);
            Log.d("wordPublish", wordCat);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == UPLOAD_ID && data != null){
            selectedImage = data.getData();
            UploadImageBtn.setImageURI(selectedImage);
        }
    }
}