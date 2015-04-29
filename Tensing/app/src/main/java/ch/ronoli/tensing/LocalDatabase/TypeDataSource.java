package ch.ronoli.tensing.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.apache.http.MethodNotSupportedException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.ronoli.tensing.models.Type;

public class TypeDataSource extends DataSource<Type> {
    private static String REQUEST_TABLE_NAME = DatabaseContract.TypeTable.TABLE_NAME;

    private static String[] ALL_COLUMNS = {
            DatabaseContract.TypeTable._ID,
            DatabaseContract.TypeTable.COLUMN_NAME_NAME
    };

    public TypeDataSource(Context context) {
        super(context);
    }

    @Override
    public Type cursorToObject(Cursor cursor) throws SQLException {
        Type type = new Type(cursor.getString(1));
        type.setId(cursor.getLong(0));
        return type;
    }

    @Override
    protected ContentValues objectToContentValues(Type type) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TypeTable.COLUMN_NAME_NAME, type.getName());
        return values;
    }

    @Override
    protected long getIdIfExists(Type type) throws SQLException {
        String where = DatabaseContract.TypeTable.COLUMN_NAME_NAME + " = ?";
        String[] whereValues = {
                type.getName()
        };

        openReadConnection();
        Cursor cursor = database.query(REQUEST_TABLE_NAME, ALL_COLUMNS, where, whereValues, null, null, null);
        Type tempType = null;
        if (cursor != null && cursor.moveToFirst()) {
            tempType = cursorToObject(cursor);
        }
        closeConnection();

        if (tempType == null)
            return 0;
        else
            return tempType.getId();
    }

    @Override
    public Type getById(long id) throws SQLException {
        String where = DatabaseContract.TypeTable._ID + " = ?";
        String[] whereValues = {Long.toString(id)};

        openReadConnection();
        Cursor cursor = database.query(REQUEST_TABLE_NAME, ALL_COLUMNS, where, whereValues, null, null, null);
        Type type = null;
        if (cursor != null && cursor.moveToFirst()) {
            type = cursorToObject(cursor);
        }
        closeConnection();

        return type;
    }

    @Override
    public List<Type> getAll() throws SQLException {
        openReadConnection();
        Cursor cursor = database.query(REQUEST_TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);

        ArrayList<Type> types = new ArrayList<Type>();
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                types.add(cursorToObject(cursor));
                cursor.moveToNext();
            }
        }
        closeConnection();
        return types;
    }

    @Override
    public long save(Type type) throws SQLException {
        long typeId = getIdIfExists(type);
        if (typeId != 0) return typeId;

        openWriteConnection();
        long rowId = database.insert(REQUEST_TABLE_NAME, null, objectToContentValues(type));
        closeConnection();

        type.setId(rowId);

        return rowId;
    }

    @Override
    public void update(Type type) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("Update Method for types not implemented. Please use delete(oldRouteId) followed by save(Route updatedRoute)");
    }

    @Override
    public void delete(long id) throws SQLException {
        Type type = getById(id);
        if (type == null) throw new SQLException("No type with this id found in database.");

        String where = DatabaseContract.TypeTable._ID + " = ?";
        String[] whereValues = {Long.toString(id)};
        openWriteConnection();
        database.delete(REQUEST_TABLE_NAME, where, whereValues);
        closeConnection();
    }
}