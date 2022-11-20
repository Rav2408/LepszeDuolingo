package pl.edu.pb.lepszeduolingo.models;

import com.j256.ormlite.field.DatabaseField;

public class Category {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String name;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Difficulty difficulty;
}
