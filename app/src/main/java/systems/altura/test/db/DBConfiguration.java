package systems.altura.test.db;

import android.content.ContentValues;
import android.content.Context;

import systems.altura.db.Base;

/**
 * Created by caelvaso on 25/1/16.
 */
public class DBConfiguration extends Base {

    private static String TABLE_NAME = "config";
    private static String TABLE_SQL_CREATE = "create table "+TABLE_NAME+"(COLUMN_ID integer primary key autoincrement, name text not null)";
    static {
        create(TABLE_NAME,TABLE_SQL_CREATE);
    }

    public DBConfiguration(Context context) {
        super(context,TABLE_NAME);
    }
    public void inserta(String name){
        ContentValues values = new ContentValues();
        values.put("name",name);
        insert(values);
    }
}
