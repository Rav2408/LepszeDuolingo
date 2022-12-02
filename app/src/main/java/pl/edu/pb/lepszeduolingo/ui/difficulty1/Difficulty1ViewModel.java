package pl.edu.pb.lepszeduolingo.ui.difficulty1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Difficulty1ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Difficulty1ViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}