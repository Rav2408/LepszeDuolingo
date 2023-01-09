package pl.edu.pb.lepszeduolingo.ui.admin.add;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import pl.edu.pb.lepszeduolingo.R;

public class AddWordImageDialog  extends AppCompatDialogFragment {
    private EditText imageUrlView;
    private AddWordImageDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.adminimages_add_dialog, null);
        // views
        imageUrlView = view.findViewById(R.id.word_add_image_url);
        // builder
        builder.setView(view)
                .setTitle("Image")
                .setNegativeButton("Cancel", (dialog, which) -> {})
                .setPositiveButton("Ok", (dialog, which) -> {
                    // pass data to fragment
                    String imageUrl = imageUrlView.getText().toString();
                    listener.passImageUrl(imageUrl);
                });

        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddWordImageDialog.AddWordImageDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement AddWordImageDialogListener");
        }
    }
    public interface AddWordImageDialogListener{
        void passImageUrl(String imageUrl);
    }
}
