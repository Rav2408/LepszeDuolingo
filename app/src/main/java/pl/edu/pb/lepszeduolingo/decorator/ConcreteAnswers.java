package pl.edu.pb.lepszeduolingo.decorator;

import java.util.List;

public class ConcreteAnswers implements Answers {
    List<String> answers;
    String correctAnswer;

    public ConcreteAnswers(List<String> answers, String correctAnswer) {
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }


    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


}
