package pl.edu.pb.lepszeduolingo.models;

import com.j256.ormlite.field.DatabaseField;

public class UnlockedWord {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Word word;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private User user;

}
