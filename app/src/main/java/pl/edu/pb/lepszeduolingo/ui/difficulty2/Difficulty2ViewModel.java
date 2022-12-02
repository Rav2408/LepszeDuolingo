package pl.edu.pb.lepszeduolingo.ui.difficulty2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Difficulty2ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Difficulty2ViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}