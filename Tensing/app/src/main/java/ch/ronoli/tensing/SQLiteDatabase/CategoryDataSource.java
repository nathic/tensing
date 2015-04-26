package ch.ronoli.tensing.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.apache.http.MethodNotSupportedException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.ronoli.tensing.models.Category;

public class CategoryDataSource extends DataSource<Category> {
    private static String REQUEST_TABLE_NAME = DatabaseContract.CategoryTable.TABLE_NAME;

    private static String[] ALL_COLUMNS = {
            DatabaseContract.CategoryTable._ID,
            DatabaseContract.CategoryTable.COLUMN_NAME_NAME,
            DatabaseContract.CategoryTable.COLUMN_NAME_TYPE
    };

    public CategoryDataSource(Context context) {
        super(context);
    }

    @Override
    protected Category cursorToObject(Cursor cursor) throws SQLException {
        Category category = new Category();
        category.setName(cursor.getString(1));
        category.setId(cursor.getLong(0));

        TypeDataSource typeDataSource = new TypeDataSource(context);
        try {
            category.setType(typeDataSource.getById(cursor.getLong(2)));
        } catch (SQLException e) {
            throw new SQLException("Exception in TypeDataSource.getById while getting Type for Category", null, e);
        }
        return category;
    }

    @Override
    protected ContentValues objectToContentValues(Category category) throws SQLException {
        ContentValues values = new ContentValues();

        if (category.getType().getId() == 0)
            category.getType().setId(new TypeDataSource(context).save(category.getType()));

        values.put(DatabaseContract.CategoryTable.COLUMN_NAME_NAME, category.getName());
        values.put(DatabaseContract.CategoryTable.COLUMN_NAME_TYPE, category.getType().getId());
        return values;
    }

    @Override
    protected long getIdIfExists(Category category) throws SQLException {
        String where = DatabaseContract.CategoryTable.COLUMN_NAME_NAME + " = ?";
        String[] whereValues = {
                category.getName()
        };

        openReadConnection();
        Cursor cursor = database.query(REQUEST_TABLE_NAME, ALL_COLUMNS, where, whereValues, null, null, null);
        Category tempCategory = null;
        if (cursor != null && cursor.moveToFirst()) {
            tempCategory = cursorToObject(cursor);
        }
        closeConnection();

        if (tempCategory == null)
            return 0;
        else
            return tempCategory.getId();
    }

    @Override
    public Category getById(long id) throws SQLException {
        String where = DatabaseContract.CategoryTable._ID + " = ?";
        String[] whereValues = {Long.toString(id)};

        openReadConnection();
        Cursor cursor = database.query(REQUEST_TABLE_NAME, ALL_COLUMNS, where, whereValues, null, null, null);
        Category category = null;
        if (cursor != null && cursor.moveToFirst()) {
            category = cursorToObject(cursor);
        }
        closeConnection();

        return category;
    }

    @Override
    public List<Category> getAll() throws SQLException {
        openReadConnection();
        Cursor cursor = database.query(REQUEST_TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);

        ArrayList<Category> categories = new ArrayList<Category>();
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                categories.add(cursorToObject(cursor));
                cursor.moveToNext();
            }
        }
        closeConnection();
        return categories;
    }

    @Override
    public long save(Category category) throws SQLException {
        long stepId = getIdIfExists(category);
        if (stepId != 0) return stepId;

        openWriteConnection();
        long rowId = database.insert(REQUEST_TABLE_NAME, null, objectToContentValues(category));
        category.setId(rowId);
        closeConnection();

        return rowId;
    }

    @Override
    public void update(Category category) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("This method is not implemented.");
    }

    @Override
    public void delete(long id) throws SQLException {
        if (getById(id) == null) throw new SQLException("No category with this id found in database.");

        String where = DatabaseContract.CategoryTable._ID + " = ?";
        String[] whereValues = {Long.toString(id)};
        openWriteConnection();
        database.delete(REQUEST_TABLE_NAME, where, whereValues);
        closeConnection();
    }
}
