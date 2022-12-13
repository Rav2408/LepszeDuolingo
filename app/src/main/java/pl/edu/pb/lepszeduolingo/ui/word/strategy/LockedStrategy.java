package pl.edu.pb.lepszeduolingo.ui.word.strategy;

import pl.edu.pb.lepszeduolingo.R;

public class LockedStrategy implements Strategy{
    @Override
    public int getColor() {
        return R.color.purple_200;   //TODO wstawić odpowiedni kolor
    }

    @Override
    public int getIcon() {
        return 0;    //TODO wstawić odpowiednią ikonę(zamknięta kłódka)
    }
}
