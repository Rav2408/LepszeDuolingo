package pl.edu.pb.lepszeduolingo.models;

import com.j256.ormlite.field.DatabaseField;

public class Translation {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String translationText;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Language language;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Word word;

}
