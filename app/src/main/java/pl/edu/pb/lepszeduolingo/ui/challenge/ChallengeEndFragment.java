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
import pl.edu.pb.lepszeduolingo.databinding.FragmentChallengeEndBinding;
import pl.edu.pb.lepszeduolingo.databinding.FragmentChallengeStartBinding;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;

public class ChallengeEndFragment extends Fragment {
    private FragmentChallengeEndBinding binding;
    DatabaseFacade facade = new DatabaseFacade(this.getContext());
    TextView scoreView;
    TextView bestScoreView;
    Button againBtn;
    Button backBtn;
    String difficultyName;
    int difficultyId;
    int score;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChallengeEndBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // bundle
        Bundle arguments = getArguments();
        score = arguments.getInt("score");
        // difficulty
        difficultyName = ((ChallengeActivity)requireActivity()).difficultyName;
        difficultyId = facade.getDifficultyIdByName(difficultyName);
        // set score to db
        facade.setBestScore(difficultyId, score);
        // get elements
        scoreView = root.findViewById(R.id.challenge_end_score);
        bestScoreView = root.findViewById(R.id.challenge_end_best_score);
        againBtn = root.findViewById(R.id.challenge_end_again_btn);
        backBtn = root.findViewById(R.id.challenge_end_back_btn);
        // buttons
        againBtn.setOnClickListener(v -> handleAgain());
        backBtn.setOnClickListener(v -> getActivity().finish());
        // set elements
        scoreView.setText(String.valueOf(score));
        bestScoreView.setText(getString(R.string.best_score, facade.getBestScore(difficultyId)));
        return root;
    }
    private void handleAgain(){
        // start challenge play fragment
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("key", "start");
        intent.putExtras(bundle);
        getActivity().getIntent().putExtras(intent);
        getActivity().recreate();
    }
}
