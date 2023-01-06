package pl.edu.pb.lepszeduolingo.ui.admin.add;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.db.DatabaseHelper;

public class AddWordTranslationsDialog extends AppCompatDialogFragment {
    private EditText ViewTranslation;
    private Spinner ViewLanguages;
    private AddWordTranslationDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.admintranslations_add_dialog, null);
        // views
        ViewTranslation = view.findViewById(R.id.translations_add_translation);
        ViewLanguages = view.findViewById(R.id.translations_add_language);
        // builder
        builder.setView(view)
                .setTitle("Translation")
                .setNegativeButton("Cancel", (dialog, which) -> {})
                .setPositiveButton("Ok", (dialog, which) -> {
                    // pass data to fragment
                    String translation = ViewTranslation.getText().toString();
                    String language = ViewLanguages.getSelectedItem().toString();
                    listener.passChoice(translation, language);
                });
        // db
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this.getContext());
        JSONArray languages = databaseHelper.getLanguages();
        ArrayList<String> languagesData = new ArrayList<>();
        for(int i=0; i<languages.length(); i++){
            try {
                languagesData.add(languages.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // spinner
        ArrayAdapter<String> lanAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, languagesData);
        lanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ViewLanguages.setAdapter(lanAdapter);

        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            /*
            * throws error
            * E/ImeBackDispatcher: Ime callback not found. Ignoring unregisterReceivedCallback
            * idk what it is but still works
            */
            listener = (AddWordTranslationDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement AddWordTranslationDialogListener");
        }
    }
    public interface AddWordTranslationDialogListener{
        void passChoice(String translation, String language);
    }
}
