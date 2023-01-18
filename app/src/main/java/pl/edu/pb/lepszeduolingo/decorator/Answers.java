package pl.edu.pb.lepszeduolingo.decorator;
import pl.edu.pb.lepszeduolingo.models.*;

public interface Answers {
    public String getAnswer(int i);
    public String getCorrectAnswer();
    public void setAnswer(int position, String answer);
}
