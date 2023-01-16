package pl.edu.pb.lepszeduolingo.ui.challenge;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

public class GsonUtils {
    private static Gson gson;

    public static Gson getGsonParser() {
        if(null == gson) {
            final RuntimeTypeAdapterFactory<IStrategy> strategyTypeFactory = RuntimeTypeAdapterFactory
                    .of(IStrategy.class, "type")
                    .registerSubtype(TimeStrategy.class)
                    .registerSubtype(WinStreakStrategy.class);
            GsonBuilder builder = new GsonBuilder();
            gson = builder.registerTypeAdapterFactory(strategyTypeFactory).create();
        }
        return gson;
    }
}
