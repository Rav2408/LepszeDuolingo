package pl.edu.pb.lepszeduolingo.decorator;

import java.util.List;

public class AnswersDecorator implements Answers {
    Answers answers;
    public AnswersDecorator(Answers _answers){
        answers = _answers;
    }


    @Override
    public List<String> getAnswers() {
        return answers.getAnswers();
    }

    @Override
    public String getCorrectAnswer() {
        return answers.getCorrectAnswer();
    }

    @Override
    public void setAnswers(List<String> answerList) {
        answers.setAnswers(answerList);
    }


}
