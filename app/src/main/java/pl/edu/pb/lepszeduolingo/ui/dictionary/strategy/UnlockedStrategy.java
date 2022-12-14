package pl.edu.pb.lepszeduolingo.ui.dictionary.strategy;

import pl.edu.pb.lepszeduolingo.R;

public class UnlockedStrategy implements Strategy{
    @Override
    public int getColor() {
        return R.color.teal_200;   //TODO wstawić odpowiedni kolor
    }

    @Override
    public int getIcon() {
        return 0;       //TODO wstawić odpowiednią ikonę(zamknięta kłódka)
    }
}
