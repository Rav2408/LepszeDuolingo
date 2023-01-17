package pl.edu.pb.lepszeduolingo.ui.admin.add;

import static pl.edu.pb.lepszeduolingo.Constants.URL;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.builder.JsonBuilder;
import pl.edu.pb.lepszeduolingo.databinding.FragmentAddWordBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.rest.IVolley;
import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;

public class AddWordFragment extends Fragment
        implements
        AddWordTranslationsDialog.AddWordTranslationDialogListener,
        AddWordTranslationsAdapter.AddWordTranslationsAdapterListener,
        AddWordImageDialog.AddWordImageDialogListener{
    private static final int UPLOAD_ID = 1000;
    private static final String DEFAULT_IMAGE_URL = "https://4fun.tv/uploads/media/cache/news_big/uploads/media/news/0001/19/dd91d1823a5e9bf4b3aead76d3e56cbd79ed7b87.png";
    private Uri selectedImage;
    private AddWordTranslationsAdapter translationsAdapter;
    EditText addWordText;
    EditText addWordTranslation;
    ImageButton uploadImageBtn;
    Spinner difficultiesSpinner;
    Spinner languageSpinner;
    Button publishBtn;
    ImageButton translationBtn;
    ListView translationsListView;
    LinkedHashMap<String, String> translationsTemp = new LinkedHashMap<>();
    URL wordImageUrl;
    DatabaseFacade databaseFacade;

    private FragmentAddWordBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddWordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // prevents internet privacy error
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        databaseFacade = new DatabaseFacade(getContext());
        // text inputs
        addWordText = root.findViewById(R.id.addwordText);
        // list
        translationsListView = root.findViewById(R.id.addwordTranslationList);
        translationsAdapter = new AddWordTranslationsAdapter(
                this.getActivity(),
                translationsTemp,
                this);
        translationsListView.setAdapter(translationsAdapter);
        // button
        uploadImageBtn = root.findViewById(R.id.addwordImage);
        uploadImageBtn.setOnClickListener(v -> handleAddImage());
        translationBtn = root.findViewById(R.id.addwordTranslationBtn);
        translationBtn.setOnClickListener(v -> handleAddTranslation());
        publishBtn = root.findViewById(R.id.addwordAddBtn);

        // spinners
        difficultiesSpinner = root.findViewById(R.id.addwordDifficulty);
        languageSpinner = root.findViewById(R.id.addwordLanguage);
        // db
        DatabaseFacade databaseFacade = new DatabaseFacade(getContext());
        JSONArray difficulties = databaseFacade.getDifficulties();
        JSONArray languages = databaseFacade.getLanguages();
        ArrayList<String> difficultiesData = new ArrayList<>();
        ArrayList<Integer> difficultiesIds = new ArrayList<>();
        ArrayList<String> languagesData = new ArrayList<>();
        ArrayList<Integer> languagesIds = new ArrayList<>();
        for(int i=0;i<difficulties.length();i++){
            try {
                difficultiesData.add(difficulties.getJSONObject(i).getString("level"));
                difficultiesIds.add(difficulties.getJSONObject(i).getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<languages.length();i++){
            try {
                languagesData.add(languages.getJSONObject(i).getString("name"));
                languagesIds.add(languages.getJSONObject(i).getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> diffAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, difficultiesData);
        ArrayAdapter<String> lanAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, languagesData);
        diffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultiesSpinner.setAdapter(diffAdapter);
        languageSpinner.setAdapter(lanAdapter);

        publishBtn.setOnClickListener(v -> {
            save(
                    difficultiesIds.get(difficultiesSpinner.getSelectedItemPosition()),
                    languagesIds.get(languageSpinner.getSelectedItemPosition())
            );
            // prompt
            ((AdminAddActivity)getActivity()).showMessage("Success", true);
        });
        return root;
    }

    private void save(int difficultyId, int languageId){
        String wordOrigin = addWordText.getText().toString();
        String imagePath;
        String wordDiff = difficultiesSpinner.getSelectedItem().toString();
        if(wordOrigin.isEmpty()){
            addWordText.setError("Word is missing");
        } else if(wordOrigin.length() > 15){
            addWordText.setError("Word is too long");
        } else if(wordImageUrl ==null){
            // all good?
            sendWord(wordOrigin,languageId,difficultyId, DEFAULT_IMAGE_URL);
        } else {
            sendWord(wordOrigin,languageId,difficultyId,wordImageUrl.getPath());
            for(String word: translationsTemp.keySet()){
                Log.d("wordPublish", word);
            }
        }
    }

    private void sendWord(String word, int languageId, int difficultyId, String imagePath){
        VolleyRequest.getInstance(getContext(), new IVolley() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                databaseFacade.updateWords();
                //TODO w tym miejscu trzeba wywołać odświerzanie listy, która się wyświetla w panelu admina
            }
        }).postRequest(URL + "word", buildJsonWord(word,languageId,difficultyId,imagePath));
    }

    private JSONObject buildJsonWord(String word, int languageId, int difficultyId, String imagePath){
        return new JsonBuilder(getContext()).create()
                .put("text", word)
                .put("language",
                        new JsonBuilder(getContext())
                                .create()
                                .put("id", languageId)
                                .build()
                )
                .put("difficulty",
                        new JsonBuilder(getContext())
                                .create()
                                .put("id",  difficultyId)
                                .build()
                )
                .put("imagePath", imagePath)
                .build();
    }

    private void handleAddImage(){
        // dialog input
        AddWordImageDialog imageDialog = new AddWordImageDialog();
        imageDialog.setTargetFragment(this, 0);
        imageDialog.show(getActivity().getSupportFragmentManager(), "image dialog");
    }
    /*
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
    */
    private void handleAddTranslation(){
        AddWordTranslationsDialog translationDialog = new AddWordTranslationsDialog();
        translationDialog.setTargetFragment(this, 0);
        translationDialog.show(getActivity().getSupportFragmentManager(), "translation dialog");
    }
    // get translation from dialog
    @Override
    public void passChoice(String translation, String language) {
        translationsTemp.put(translation, language);
        updateTranslationList();
        /*
        // test
        for(String word: TranslationsTemp.keySet()){
            Log.d("translationReceive", word);
        }
        */
    }
    // update adapter list (after element addition) from fragment
    private void updateTranslationList() {
        translationsAdapter.setList(translationsTemp);
        translationsAdapter.notifyDataSetChanged();
        /*
        // test
        for(String word: TranslationsTemp.keySet()){
            Log.d("translationReceive", word);
        }
        */
    }
    // get updated list (after element deletion) from list adapter
    @Override
    public void updateList(LinkedHashMap<String, String> translationsTemp) {
        // overwrite
        this.translationsTemp = translationsTemp;
        /*
        // test
        for(String word: TranslationsTemp.keySet()){
            Log.d("translationReceive", word);
        }
        */
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void passImageUrl(String imageUrl) {
        // perform verification of url
        // if ok
        // transform string to url to bitmap
        URL newUrl = null;
        try {
            newUrl = new URL(imageUrl);
            // set url
            wordImageUrl= newUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // scale bitmap
        int currentBitmapWidth = bmp.getWidth();
        int currentBitmapHeight = bmp.getHeight();
        int ivWidth = uploadImageBtn.getWidth();
        int newWidth = ivWidth;
        int newHeight = (int) Math.floor((double) currentBitmapHeight *( (double) newWidth / (double) currentBitmapWidth));
        Bitmap newBitMap = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, true);
        // set image
        uploadImageBtn.setImageBitmap(newBitMap);
        // clear background
        uploadImageBtn.setBackgroundColor(android.R.color.transparent);
        // else
        // make toast
    }
}