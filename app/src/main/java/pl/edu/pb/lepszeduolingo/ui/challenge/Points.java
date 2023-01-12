package pl.edu.pb.lepszeduolingo.ui.challenge;

public class Points {
    private IStrategy strategy;
    private double score = 0;
    private double winStreak;
    private int currentQuestionTimeInSeconds;
    private boolean isCurrentCorrect;

    public Points(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void countPoints(boolean isCorrect, int timeInSeconds){
        this.isCurrentCorrect = isCorrect;
        this.currentQuestionTimeInSeconds = timeInSeconds;
        winStreak = isCorrect ? winStreak * 1.1 : 1;
        score += strategy.countPoints(this);
    }

    public double getScore() {
        return score;
    }

    public double getWinStreak() {
        return winStreak;
    }

    public int getCurrentQuestionTimeInSeconds() {
        return currentQuestionTimeInSeconds;
    }

    public boolean isCurrentCorrect() {
        return isCurrentCorrect;
    }
}
