package pl.edu.pb.lepszeduolingo.ui.challenge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class TimeMode implements IChallengeStarter{
    @Override
    public void startChallenge(Context context) {
        // arguments
        Points points = new Points(new TimeStrategy());
        String activityOption = "play";
        // bundle
        Intent intent = new Intent(context, ChallengeActivity.class);
        Bundle bundle = new Bundle();
        // pass
        bundle.putString("key", activityOption);
        bundle.putString("points", GsonUtils.getGsonParser().toJson(points));
        intent.putExtras(bundle);
        ((ChallengeActivity)context).getIntent().putExtras(intent);
        ((ChallengeActivity)context).recreate();
    }
}
