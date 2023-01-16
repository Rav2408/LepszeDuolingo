package pl.edu.pb.lepszeduolingo.ui.challenge;

public class TimeModeFactory extends ChallengeFactory{

    @Override
    IChallengeStarter createStarter() {
        return new TimeMode();
    }
}
