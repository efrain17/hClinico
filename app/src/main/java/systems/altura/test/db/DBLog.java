package systems.altura.test.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import systems.altura.db.Base;
import systems.altura.db.ResultSet;

/**
 * Created by caelvaso on 25/1/16.
 */
public class DBLog extends Base {

    private final static String TABLE_NAME = "loger";
    private final static String TABLE_SQL_CREATE = "create table "+TABLE_NAME+"(COLUMN_ID integer primary key autoincrement, name text not null)";

    static {
        create(TABLE_NAME, TABLE_SQL_CREATE);
    }

    public DBLog(Context context) {
        super(context,TABLE_NAME);
    }

    public void inserta(String name){
        ContentValues values = new ContentValues();
        values.put("name",name);
        insert(values);
    }

    public ResultSet getLogById(String id) throws Exception {
        return getData("select * from loger where COLUMN_ID="+id);
    }
}
