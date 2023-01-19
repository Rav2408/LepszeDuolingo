package pl.edu.pb.lepszeduolingo.ui.challenge;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.databinding.FragmentChallengeStartBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;
import pl.edu.pb.lepszeduolingo.ui.admin.add.AddWordImageDialog;

public class ChallengeStartFragment extends Fragment implements
        ChallengeStartStrategyDialog.ChallengeStartStrategyDialogListener{
    private FragmentChallengeStartBinding binding;
    DatabaseFacade facade = new DatabaseFacade(this.getContext());
    Button startBtn;
    TextView difficultyView;
    TextView bestScoreView;
    String strategy;
    String difficultyName;
    int difficultyId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChallengeStartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // difficulty
        difficultyName = ((ChallengeActivity)requireActivity()).difficultyName;
        difficultyId = facade.getDifficultyIdByName(difficultyName);
        // get elements
        startBtn = root.findViewById(R.id.challenge_start_btn);
        difficultyView = root.findViewById(R.id.challenge_start_difficulty);
        // TODO: get best score from db
        bestScoreView = root.findViewById(R.id.challenge_start_best_score);
        // set elements
        startBtn.setOnClickListener(v -> handleStartBtn());
        difficultyView.setText(((ChallengeActivity) requireActivity()).difficultyName);
        bestScoreView.setText(getString(R.string.best_score, facade.getBestScore(difficultyId)));
        return root;
    }
    private void handleStartBtn(){
        // dialog input
        ChallengeStartStrategyDialog dialog = new ChallengeStartStrategyDialog();
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getSupportFragmentManager(), "challenge dialog");
    }
    @Override
    public void pickStrategy(String strategy) {
        this.strategy = strategy;
    }
    @Override
    public void startGame() {
        ChallengeFactory challengeFactory = new ChallengeFactory();
        IChallengeStarter starter = challengeFactory.create(this.strategy);
        starter.startChallenge(this.getContext());
    }
}
