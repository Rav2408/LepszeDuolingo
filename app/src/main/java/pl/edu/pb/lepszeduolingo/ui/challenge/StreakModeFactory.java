package pl.edu.pb.lepszeduolingo.ui.challenge;

public class StreakModeFactory extends ChallengeFactory{

    @Override
    IChallengeStarter createStarter() {
        return new StreakMode();
    }
}
