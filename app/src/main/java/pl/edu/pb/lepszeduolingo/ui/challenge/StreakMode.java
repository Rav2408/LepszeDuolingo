package pl.edu.pb.lepszeduolingo.ui.challenge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class StreakMode implements IChallengeStarter{
    @Override
    public void startChallenge(Context context) {
        Points points = new Points(new WinStreakStrategy());
        String activityOption = "play";

        Intent intent = new Intent(context, ChallengeActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("key", activityOption);
        bundle.putString("points", GsonUtils.getGsonParser().toJson(points));
        intent.putExtras(bundle);
        ((ChallengeActivity)context).getIntent().putExtras(intent);
        ((ChallengeActivity)context).recreate();
    }
}
