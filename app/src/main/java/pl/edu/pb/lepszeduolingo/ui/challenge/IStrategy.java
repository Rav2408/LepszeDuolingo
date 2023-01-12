package pl.edu.pb.lepszeduolingo.ui.challenge;

public interface IStrategy {
    double QUESTION_VALUE = 100;

    double countPoints(Points points);
}
