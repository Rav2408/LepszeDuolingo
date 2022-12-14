package pl.edu.pb.lepszeduolingo.ui.word.factorymethod;

public class WordFactory {

    public static Word createWord(String word, boolean unlocked){
        if(unlocked) return new UnlockedWord(word);
        else return new LockedWord(word);
    }
}
