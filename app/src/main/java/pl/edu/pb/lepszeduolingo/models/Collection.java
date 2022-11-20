package pl.edu.pb.lepszeduolingo.models;

import com.j256.ormlite.field.DatabaseField;

public class Collection {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Category category;
}
