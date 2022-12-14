package pl.edu.pb.lepszeduolingo.ui.word.factorymethod;

public class UnlockedWord implements Word {
    String word;

    public UnlockedWord(String word) {
        this.word = word;
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public int getIcon() {
        return 0;
    }
}
