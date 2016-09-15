package systems.altura.db;

import android.database.Cursor;
import java.sql.SQLException;
import systems.altura.db.printer.Data;

/**
 * Created by caelvaso on 26/1/16.
 */
public class ResultSet{
    private Cursor cursor;
    public ResultSet(Cursor cursor){
        this.cursor = cursor;
    }
    public Cursor getCursor(){
        return cursor;
    }
    public boolean next(){
        return cursor.moveToNext();
    }

    public Object getData(Data data) throws Exception {
        data.set(this);
        return data.get();
    }

    public int getColumnCount(){
        return cursor.getColumnCount();
    }
    public String getColumnName(int i){
        return cursor.getColumnName(i);
    }
    public String getString(String colunmid, String defauld) throws SQLException {
        return (String) isNull(getString(colunmid), defauld);
    }

    public String getString(String colunmid) throws SQLException {
        return cursor.getString(cursor.getColumnIndex(colunmid));
    }

    public Integer getInt(String colunmid, String defauld) throws SQLException {
        return (Integer) isNull(getInt(colunmid), defauld);
    }

    public Integer getInt(String colunmid) throws SQLException {
        return cursor.getInt(cursor.getColumnIndex(colunmid));
    }

    public String getString(int colunmid, String defauld) throws SQLException {
        return (String) isNull(getString(colunmid), defauld);
    }

    public String getString(int colunmid) throws SQLException {
        return cursor.getString(colunmid);
    }
    public Integer getInt(int colunmid, String defauld) throws SQLException {
        return (Integer) isNull(getInt(colunmid), defauld);
    }

    public Integer getInt(int colunmid) throws SQLException {
        return cursor.getInt(colunmid);
    }

    private Object isNull(Object cadena, Object defauld) {
        return cadena == null ? defauld : cadena;
    }
    public void close(){
        cursor.close();
    }

}
