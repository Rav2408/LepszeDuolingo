package pl.edu.pb.lepszeduolingo.decorator;

public class AnswersDecorator implements Answers {
    Answers answers;
    public AnswersDecorator(Answers _answers){
        answers = _answers;
    }

    @Override
    public String getAnswer(int i) {
        return answers.getAnswer(i);
    }

    @Override
    public String getCorrectAnswer() {
        return answers.getCorrectAnswer();
    }
}
