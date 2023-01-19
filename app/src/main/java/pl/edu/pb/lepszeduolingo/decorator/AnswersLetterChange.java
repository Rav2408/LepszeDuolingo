package pl.edu.pb.lepszeduolingo.decorator;
import android.util.Log;

import java.util.List;
import java.util.Random;

public class AnswersLetterChange extends AnswersDecorator {
    public AnswersLetterChange(Answers answers) {
        super(answers);
    }

    @Override
    public List<String> getAnswers() {
        List<String> answerList = answers.getAnswers();
        for (int i = 0; i < answerList.size(); i++) {
            String answer = answerList.get(i);
            if (!answer.equals(getCorrectAnswer())) {
                if (answer.contains("t") && !answer.contains("tt")) {
                    if (new Random().nextDouble() < 0.05) {
                        answerList.set(i, answer.replace("t", "tt"));
                    }
                }
                if (answer.contains("ch")) {
                    if (new Random().nextDouble() < 0.05) {
                        answer.replace("ch", "h");
                        answerList.set(i, answer.replace("ch", "h"));
                    }
                }
                if (answer.contains("c")) {
                    if (new Random().nextDouble() < 0.05) {
                        answer.replace("c", "k");
                        answerList.set(i, answer.replace("c", "k"));
                    }
                }
                if (answer.contains("u")) {
                    if (new Random().nextDouble() < 0.05) {
                        answer.replace("u", "ou");
                        answerList.set(i, answer.replace("u", "ou"));
                    }
                }
                if (answer.contains("h") && !answer.contains("ch")) {
                    if (new Random().nextDouble() < 0.05) {
                        answer.replace("h", "ch");
                        answerList.set(i, answer.replace("h", "ch"));
                    }
                }
            }
            answers.setAnswers(answerList);
        }
        return answerList;
    }
}
