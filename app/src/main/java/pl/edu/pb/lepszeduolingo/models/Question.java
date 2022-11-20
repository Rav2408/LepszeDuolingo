package pl.edu.pb.lepszeduolingo.models;

import com.j256.ormlite.field.DatabaseField;

public class Question {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String type;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Word word;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Translation translation;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Collection collection;
}
