package pl.edu.pb.lepszeduolingo.ui.difficulty;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DifficultyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DifficultyViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}