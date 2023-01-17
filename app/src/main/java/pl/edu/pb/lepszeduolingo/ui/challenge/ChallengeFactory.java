package pl.edu.pb.lepszeduolingo.ui.challenge;

public class ChallengeFactory {
    public IChallengeStarter create(String strategy){
        if(strategy.equals("time")){
            return new TimeMode();
        }else if(strategy.equals("streak")){
            return new StreakMode();
        }else{
            throw new RuntimeException("Error in game starter");
        }
    }
}
