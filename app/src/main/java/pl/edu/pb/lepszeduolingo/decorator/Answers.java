package pl.edu.pb.lepszeduolingo.decorator;
import java.util.List;

public interface Answers {
    public List<String> getAnswers();
    public String getCorrectAnswer();
    public void setAnswers(List<String> answerList);
}
