package ch.ronoli.tensing.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;
import java.util.List;
import org.apache.http.MethodNotSupportedException;

/**
 * Created by nathic on 26.04.2015.
 */
public abstract class DataSource<DatabaseTableType> {
    protected Context context;
    protected SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DataSource(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Diese Methode öffnet die Datenbank-Session um aus der Datenbank zu lesen.
     * Diese Methode muss <b>vor</b> jedem Lesezugriff auf der Datenbank aufgerufen werden
     *
     * @throws SQLException
     */
    protected void openReadConnection() throws SQLException {
        database = dbHelper.getReadableDatabase();
    }

    /**
     * Diese Methode öffnet die Datenbank-Session um auf der Datenbank zu schreiben.
     * Diese Methode muss <b>vor</b> jedem Schreibzugriff auf der Datenbank aufgerufen werden
     *
     * @throws SQLException
     */
    protected void openWriteConnection() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Diese Methode schliesst die Datenbank-Session.
     * Diese Methode muss <b>nach</b> jedem Zugriff auf der Datenbank aufgerufen werden
     *
     * @throws SQLException
     */
    protected void closeConnection() {
        dbHelper.close();
    }

    /**
     * Diese Methode wandelt ein Datenbankeintrag zu einem Objekt um
     * @param cursor der Curor auf dem gewünschten Datenbankeintrag
     * @return Ein Model-Objekt
     * @throws SQLException
     */
    protected abstract DatabaseTableType cursorToObject(Cursor cursor) throws SQLException;

    /**
     *  Diese Methode wandelt ein Objekt zu einem ContentValue um
     * @param object Das Model-Objekt
     * @return ein ContentValue
     * @throws SQLException
     */
    protected abstract ContentValues objectToContentValues(DatabaseTableType object) throws SQLException;

    /**
     *  Diese Methode gibt die Id eines Datenbank-Objektes zurück falls diese Existiert
     * @param object ein Model-Objekt
     * @return die Id
     * @throws SQLException
     */
    protected abstract long getIdIfExists(DatabaseTableType object) throws SQLException;

    /**
     * Diese Methode sucht den Datenbankobjekt mit der jeweiligen id
     * @param id die id (primary key) des gesuchten Objekts
     * @return ein Model-Objekt oder null
     * @throws SQLException
     */
    public abstract DatabaseTableType getById(long id) throws SQLException;

    /**
     * Diese Methode gibt alle Datenbankobjekte zurück
     * @return eine Liste von Model-Objekten
     * @throws SQLException
     */
    public abstract List<DatabaseTableType> getAll() throws SQLException;

    /**
     * Diese Methode schreibt einen Objekt in der Datenbank ab
     * @param object Das Model-Objekt
     * @return die Id des Objektes
     * @throws SQLException
     */
    public abstract long save(DatabaseTableType object) throws SQLException;

    /**
     * Diese Methode updated ein Objekt in der Datenbank
     * @param object Das Model-Objekt
     * @throws SQLException
     * @throws MethodNotSupportedException
     */
    public abstract void update(DatabaseTableType object) throws SQLException, MethodNotSupportedException;

    /**
     * Diese Methode löscht ein Objekt von der Datenbank
     * @param id die id (primary key) des Objektes
     * @throws SQLException
     * @throws MethodNotSupportedException
     */
    public abstract void delete(long id) throws SQLException, MethodNotSupportedException;
}
