package pl.edu.pb.lepszeduolingo.models;


import com.j256.ormlite.field.DatabaseField;

public class Difficulty {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String level;
}
