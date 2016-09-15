package ec.com.altura.aclient.db;

import android.content.ContentValues;
import android.content.Context;

import systems.altura.db.Base;
import systems.altura.db.ResultSet;
import systems.altura.db.printer.StringArrayRowData;

/**
 * Created by caelvaso on 10/2/16.
 */
public class DBUser extends Base {

    private final static String TABLE_NAME = "user";
    private final static String TABLE_SQL_CREATE =
            "create table "+TABLE_NAME+"(id_user text primary key, " +
                    "name text not null," +
                    "status int)";
    static {
        create(TABLE_NAME, TABLE_SQL_CREATE);
    }

    public DBUser(Context context) {
        super(context, TABLE_NAME);
    }

    public int getStatus() throws Exception {
        String sql = "select status from "+TABLE_NAME;
        return getData(sql).getInt("status");
    }

    public void insertUser(String text,String name, int status) throws Exception {
    open();
        ContentValues values = new ContentValues();
        values.put("id_user",text);
        values.put("name",name);
        values.put("status",status);
        insert(values);
        close();
        //return getData(sql).getInt("status");
    }

    public String[] getUserDate() throws Exception {
        open();
        String[] datos = new String[2];
        ResultSet res = getData("select * from user");
        if (res.next()) {
            datos = (String[]) res.getData(new StringArrayRowData());}
        close();
        return datos;}



}
