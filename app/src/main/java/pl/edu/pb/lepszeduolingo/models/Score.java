package pl.edu.pb.lepszeduolingo.models;

import com.j256.ormlite.field.DatabaseField;

public class Score {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private Integer bestScore;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private User user;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Difficulty difficulty;
}
