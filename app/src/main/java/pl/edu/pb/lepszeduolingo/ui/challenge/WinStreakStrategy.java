package pl.edu.pb.lepszeduolingo.ui.challenge;

public class WinStreakStrategy implements IStrategy {

    @Override
    public double countPoints(Points points) {
        if(points.isCurrentCorrect()){
            return QUESTION_VALUE * points.getWinStreak();
        }
        return -10;
    }
}
