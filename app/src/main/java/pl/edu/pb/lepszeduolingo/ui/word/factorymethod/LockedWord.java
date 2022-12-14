package pl.edu.pb.lepszeduolingo.ui.word.factorymethod;

public class LockedWord implements Word{
    String word;
    int icon; //TODO = R.drwable...

    public LockedWord(String word) {
        this.word = word;
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public int getIcon() {
        return icon;
    }
}
