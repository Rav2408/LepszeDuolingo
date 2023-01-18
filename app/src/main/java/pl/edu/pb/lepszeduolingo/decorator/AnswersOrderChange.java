package pl.edu.pb.lepszeduolingo.decorator;

import java.util.Collections;
import java.util.List;

public class AnswersOrderChange extends AnswersDecorator {

    public AnswersOrderChange(Answers _answers) {
        super(_answers);
        changeOrderRandomly();
    }

    private void changeOrderRandomly(){
        List<String> answerList = answers.getAnswers();
        Collections.shuffle(answerList);
        answers.setAnswers(answerList);
    }
}
