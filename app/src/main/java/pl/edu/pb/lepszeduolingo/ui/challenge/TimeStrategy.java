package pl.edu.pb.lepszeduolingo.ui.challenge;

public class TimeStrategy implements IStrategy {

    @Override
    public double countPoints(Points points) {
        if(points.isCurrentCorrect()){
            if(points.getCurrentQuestionTimeInSeconds()>5){
                return QUESTION_VALUE;
            }else{
                return QUESTION_VALUE*2 - points.getCurrentQuestionTimeInSeconds()*5;
            }
        }
        return -10;
    }
}
