package pl.edu.pb.lepszeduolingo.decorator;

public class AnswersOrderChange extends AnswersDecorator {

    public AnswersOrderChange(Answers _answers) {
        super(_answers);
        changeOrderRandomly();
    }

    private void changeOrderRandomly(){
        String odp1 = answers.getAnswer(0);
        String odp2 = answers.getAnswer(1);
        String odp3 = answers.getAnswer(2);
        String odp4 = answers.getAnswer(3);

        //TODO randomowo ustawiać odpowiedzi
        //answers.setAnswer();
    }
}
