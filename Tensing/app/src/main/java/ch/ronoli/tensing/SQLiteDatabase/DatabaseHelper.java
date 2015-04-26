package ch.ronoli.tensing.SQLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tensingapp.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DatabaseContract.TypeTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.CategoryTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.ExerciseTable.CREATE_TABLE);

        Log.i(TAG, "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.ExerciseTable.DROP_TABLE);
        db.execSQL(DatabaseContract.CategoryTable.DROP_TABLE);
        db.execSQL(DatabaseContract.TypeTable.DROP_TABLE);

        Log.i(TAG, "Database dropped");

        onCreate(db);
    }
}
