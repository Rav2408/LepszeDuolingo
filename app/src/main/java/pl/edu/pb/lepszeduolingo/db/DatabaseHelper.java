//package pl.edu.pb.lepszeduolingo.db;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
//import com.j256.ormlite.support.ConnectionSource;
//import com.j256.ormlite.table.TableUtils;
//
//import java.sql.SQLException;
//
//import pl.edu.pb.lepszeduolingo.R;
//import pl.edu.pb.lepszeduolingo.models.Language;
//
//public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
//
//    private static final String DATABASE_NAME = "";
//    private static final int DATABASE_VERSION = 1;
//
//    public DatabaseHelper(Context context){
//        super(context,DATABASE_NAME,null,DATABASE_VERSION, R.raw.ormlite_config);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
//        try {
//            TableUtils.createTable(connectionSource, Language.class)
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//        try {
//            TableUtils.dropTable(connectionSource,Language.class,true);
//            onCreate(database, connectionSource);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//}
