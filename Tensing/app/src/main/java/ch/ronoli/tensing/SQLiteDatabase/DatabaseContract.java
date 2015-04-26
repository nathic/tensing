package ch.ronoli.tensing.SQLiteDatabase;

import android.provider.BaseColumns;

public final class DatabaseContract {
    private static final String INT_TYPE = " INTEGER";
    private static final String VARCHAR_TYPE = " VARCHAR(100)";
    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA = ", ";

    private static final String DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS ";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String NOT_NULL = " NOT NULL";

    // To prevent someone from accidentally instantiating the contract class
    public DatabaseContract() {}

    // Data Tables
    public static abstract class TypeTable implements BaseColumns {
        public static final String TABLE_NAME = "types";
        public static final String COLUMN_NAME_NAME = "name";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + INT_TYPE + PRIMARY_KEY + COMMA +
                        COLUMN_NAME_NAME + VARCHAR_TYPE + NOT_NULL + COMMA +
                        " );";

        public static final String DROP_TABLE = DROP_TABLE_STATEMENT + TABLE_NAME + ";";
    }

    public static abstract class CategoryTable implements BaseColumns {
        public static final String TABLE_NAME = "categories";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_TYPES = "types";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + INT_TYPE + PRIMARY_KEY + COMMA +
                        COLUMN_NAME_NAME + VARCHAR_TYPE + NOT_NULL + COMMA +
                        COLUMN_NAME_TYPE + COLUMN_NAME_TYPES + NOT_NULL +COMMA +
                        " FOREIGN KEY (" + COLUMN_NAME_TYPE + ") REFERENCES " + TypeTable.TABLE_NAME + " (" + TypeTable._ID + ")" +
                        " );";

        public static final String DROP_TABLE = DROP_TABLE_STATEMENT + TABLE_NAME + ";";
    }

    public static abstract class ExerciseTable implements BaseColumns {
        public static final String TABLE_NAME = "exercises";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_CATEGORIES = "categories";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + INT_TYPE + PRIMARY_KEY + COMMA +
                        COLUMN_NAME_NAME + VARCHAR_TYPE + NOT_NULL + COMMA +
                        COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA +
                        COLUMN_NAME_TEXT + TEXT_TYPE + NOT_NULL + COMMA +
                        COLUMN_NAME_LINK + VARCHAR_TYPE + COMMA +
                        COLUMN_NAME_THUMBNAIL + VARCHAR_TYPE + COMMA +
                        COLUMN_NAME_CATEGORY + COLUMN_NAME_CATEGORIES + NOT_NULL + COMMA +
                        " FOREIGN KEY (" + COLUMN_NAME_CATEGORY + ") REFERENCES " + CategoryTable.TABLE_NAME + " (" + CategoryTable._ID + ")" +
                        " );";

        public static final String DROP_TABLE = DROP_TABLE_STATEMENT + TABLE_NAME + ";";
    }
}