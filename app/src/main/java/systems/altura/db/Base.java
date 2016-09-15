package systems.altura.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by caelvaso on 25/1/16.
 */
public class Base extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "amobile.db";
    public static final int DATABASE_VERSION = 4;
    public static Map<String,String> TABLE_SQL_CREATE = new LinkedHashMap();
    public String tableName;
    private SQLiteDatabase db;
    private Base base;
    public static final String TAG = Base.class.getClass().getName();

    public Base(Context context,String tableName) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.tableName = tableName;
        base = this;
    }

    public static void create(String name,String sql_create){
        TABLE_SQL_CREATE.put(name,sql_create);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(String table_name : TABLE_SQL_CREATE.keySet()) {
            db.execSQL(TABLE_SQL_CREATE.get(table_name));
            Log.i(TAG,TABLE_SQL_CREATE.get(table_name));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(String table_name : TABLE_SQL_CREATE.keySet()) {
            db.execSQL("DROP TABLE IF EXISTS " + table_name);
        }
        onCreate(db);
    }
    public void open() throws SQLException {
        db = base.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insert(ContentValues values) {
        long insertId = db.insert(tableName, null, values);
        return insertId;
    }

    public Cursor query(String[] columns,String where){
        Cursor cursor = db.query(tableName, columns, where, null,null, null, null);
        return cursor;
    }
    public int deleteComment(String where) {
        return db.delete(tableName, where, null);
    }

    public ResultSet getData(String sql) throws Exception {
        Cursor c = db.rawQuery(sql, null);
        return new ResultSet(c);
    }
    public ResultSet getAllData() throws Exception {
        return getData("select * from "+tableName);
    }

}
