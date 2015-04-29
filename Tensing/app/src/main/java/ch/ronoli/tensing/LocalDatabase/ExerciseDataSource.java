package ch.ronoli.tensing.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.apache.http.MethodNotSupportedException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.ronoli.tensing.models.Exercise;

public class ExerciseDataSource extends DataSource<Exercise> {
    private static String REQUEST_TABLE_NAME = DatabaseContract.ExerciseTable.TABLE_NAME;

    private static String[] ALL_COLUMNS = {
            DatabaseContract.ExerciseTable._ID,
            DatabaseContract.ExerciseTable.COLUMN_NAME_NAME,
            DatabaseContract.ExerciseTable.COLUMN_NAME_DESCRIPTION,
            DatabaseContract.ExerciseTable.COLUMN_NAME_TEXT,
            DatabaseContract.ExerciseTable.COLUMN_NAME_LINK,
            DatabaseContract.ExerciseTable.COLUMN_NAME_THUMBNAIL,
            DatabaseContract.ExerciseTable.COLUMN_NAME_CATEGORY
    };

    public ExerciseDataSource(Context context) {
        super(context);
    }

    @Override
    protected Exercise cursorToObject(Cursor cursor) throws SQLException {
        Exercise exercise = new Exercise();
        exercise.setId(cursor.getLong(0));
        exercise.setName(cursor.getString(1));
        exercise.setDescription(cursor.getString(2));
        exercise.setText(cursor.getString(3));
        exercise.setLink(cursor.getString(4));
        exercise.setThumbnail(cursor.getString(5));


        CategoryDataSource categoryDataSource = new CategoryDataSource(context);
        try {
            exercise.setCategory(categoryDataSource.getById(cursor.getLong(6)));
        } catch (SQLException e) {
            throw new SQLException("Exception in CategoryDataSource.getById while getting Type for Exercise", null, e);
        }
        return exercise;
    }

    @Override
    protected ContentValues objectToContentValues(Exercise exercise) throws SQLException {
        ContentValues values = new ContentValues();

        if (exercise.getCategory().getId() == 0)
            exercise.getCategory().setId(new CategoryDataSource(context).save(exercise.getCategory()));

        values.put(DatabaseContract.ExerciseTable.COLUMN_NAME_NAME, exercise.getName());
        values.put(DatabaseContract.ExerciseTable.COLUMN_NAME_DESCRIPTION, exercise.getDescription());
        values.put(DatabaseContract.ExerciseTable.COLUMN_NAME_TEXT, exercise.getText());
        values.put(DatabaseContract.ExerciseTable.COLUMN_NAME_LINK, exercise.getLink());
        values.put(DatabaseContract.ExerciseTable.COLUMN_NAME_THUMBNAIL, exercise.getThumbnail());
        values.put(DatabaseContract.ExerciseTable.COLUMN_NAME_CATEGORY, exercise.getCategory().getId());
        return values;
    }

    @Override
    protected long getIdIfExists(Exercise exercise) throws SQLException {
        String where = DatabaseContract.CategoryTable.COLUMN_NAME_NAME + " = ?";
        String[] whereValues = {
                exercise.getName()
        };

        openReadConnection();
        Cursor cursor = database.query(REQUEST_TABLE_NAME, ALL_COLUMNS, where, whereValues, null, null, null);
        Exercise tempExercise = null;
        if (cursor != null && cursor.moveToFirst()) {
            tempExercise = cursorToObject(cursor);
        }
        closeConnection();

        if (tempExercise == null)
            return 0;
        else
            return tempExercise.getId();
    }

    @Override
    public Exercise getById(long id) throws SQLException {
        String where = DatabaseContract.ExerciseTable._ID + " = ?";
        String[] whereValues = {Long.toString(id)};

        openReadConnection();
        Cursor cursor = database.query(REQUEST_TABLE_NAME, ALL_COLUMNS, where, whereValues, null, null, null);
        Exercise exercise = null;
        if (cursor != null && cursor.moveToFirst()) {
            exercise = cursorToObject(cursor);
        }
        closeConnection();

        return exercise;
    }

    @Override
    public List<Exercise> getAll() throws SQLException {
        openReadConnection();
        Cursor cursor = database.query(REQUEST_TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);

        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                exercises.add(cursorToObject(cursor));
                cursor.moveToNext();
            }
        }
        closeConnection();
        return exercises;
    }

    @Override
    public long save(Exercise exercise) throws SQLException {
        long exerciseId = getIdIfExists(exercise);
        if (exerciseId != 0) return exerciseId;

        openWriteConnection();
        long rowId = database.insert(REQUEST_TABLE_NAME, null, objectToContentValues(exercise));
        exercise.setId(rowId);
        closeConnection();

        return rowId;
    }

    @Override
    public void update(Exercise exercise) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("This method is not implemented.");
    }

    @Override
    public void delete(long id) throws SQLException {
        if (getById(id) == null) throw new SQLException("No exercise with this id found in database.");

        String where = DatabaseContract.CategoryTable._ID + " = ?";
        String[] whereValues = {Long.toString(id)};
        openWriteConnection();
        database.delete(REQUEST_TABLE_NAME, where, whereValues);
        closeConnection();
    }
}
