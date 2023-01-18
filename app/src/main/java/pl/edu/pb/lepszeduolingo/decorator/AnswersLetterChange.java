package pl.edu.pb.lepszeduolingo.decorator;
import java.util.List;

public class AnswersLetterChange extends AnswersDecorator {
    public AnswersLetterChange(Answers answers) {
        super(answers);
        changeLettersRandomly();
    }


    @Override
    public String getCorrectAnswer() {
        return super.getCorrectAnswer();
    }

    private void changeLettersRandomly(){

    }
}
