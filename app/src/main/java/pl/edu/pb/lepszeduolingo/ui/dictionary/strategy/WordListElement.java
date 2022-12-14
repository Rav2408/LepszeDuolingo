package pl.edu.pb.lepszeduolingo.ui.dictionary.strategy;

import pl.edu.pb.lepszeduolingo.ui.dictionary.strategy.Strategy;

public class WordListElement {

    private String word;
    private Strategy strategy;

    public WordListElement(String word,Strategy strategy) {
        this.word = word;
        this.strategy = strategy;
    }

    public int getColor() {
        return strategy.getColor();
    }

    public int getIcon() {
        return strategy.getIcon();
    }
}
