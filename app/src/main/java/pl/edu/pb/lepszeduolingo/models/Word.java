package pl.edu.pb.lepszeduolingo.models;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "word")
class Word{

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String text;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Language language;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Difficulty difficulty;

    @DatabaseField
    private String imagePath;

    public Word() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}