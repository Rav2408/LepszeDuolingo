package pl.edu.pb.lepszeduolingo.ui.challenge;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import pl.edu.pb.lepszeduolingo.R;

public class ChallengeStartStrategyDialog extends AppCompatDialogFragment {
    private ChallengeStartStrategyDialogListener listener;
    Button strategyTime_btn;
    Button strategyStreak_btn;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.challenge_start_dialog, null);
        // views
        strategyTime_btn = view.findViewById(R.id.challenge_strategy_1);
        strategyStreak_btn = view.findViewById(R.id.challenge_strategy_2);
        // builder
        builder.setView(view)
                .setCancelable(true)
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });
        // buttons
        strategyTime_btn.setOnClickListener(v -> {
            listener.pickStrategy("time");
            this.getDialog().cancel();
            listener.startGame();
        });
        strategyStreak_btn.setOnClickListener(v -> {
            listener.pickStrategy("streak");
            this.getDialog().cancel();
            listener.startGame();
        });
        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ChallengeStartStrategyDialog.ChallengeStartStrategyDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement AddWordImageDialogListener");
        }
    }
    public interface ChallengeStartStrategyDialogListener {
        void pickStrategy(String strategy);
        void startGame();
    }
}
