package pl.edu.pb.lepszeduolingo.ui.difficulty3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Difficulty3ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Difficulty3ViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}