package ec.com.altura.aclient.db;

import android.content.ContentValues;
import android.content.Context;

import systems.altura.db.Base;

/**
 * Created by CarlosEfrain on 10/02/2016.
 */
public class DBConfig extends Base {


    private final static String TABLE_NAME = "config";
    private final static String TABLE_SQL_CREATE =
            "create table "+TABLE_NAME+"( info_config text primary key null)" ;

    static {
        create(TABLE_NAME, TABLE_SQL_CREATE);
    }

    public DBConfig(Context context) {
        super(context, TABLE_NAME);
    }

    public int getStatus() throws Exception {
        String sql = "select info_config from "+TABLE_NAME;
        return getData(sql).getInt("info_config");
    }

    public void createUser(String text) throws Exception {

        ContentValues values = new ContentValues();
        values.put("info_config",text);
        insert(values);
        //return getData(sql).getInt("status");
    }
}
