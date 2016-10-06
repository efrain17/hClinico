package ec.com.altura.aclient.db;

import android.content.ContentValues;
import android.content.Context;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ec.com.altura.aclient.Factura2;

import ec.com.altura.aclient.out.CuentasActivity;
import ec.com.altura.aclient.out.Factura;
import ec.com.altura.aclient.out.Login;
import ec.com.altura.aclient.util.Me;
import systems.altura.db.Base;
import systems.altura.db.ResultSet;
import systems.altura.util.Log;
import systems.altura.util.layout.base.List;

/**
 * Created by InvitadoX on 19/02/2016.
 */
public class DBFacturas extends Base {


    private final static String TABLE_NAME = "facturas_detalle";
    private final static String TABLE_SQL_CREATE =
    "create table "+TABLE_NAME+"( id INTEGER PRIMARY KEY AUTOINCREMENT, rubros text not null, valor text not null, numero_factura text not null, id_servicio text not null)" ;

    static {
        create(TABLE_NAME, TABLE_SQL_CREATE);}

    public DBFacturas(Context context) {
        super(context, TABLE_NAME);}




    public void insertFactura(String numero_factura, String rubros, String valor, String servicio  ) throws Exception {
        open();

        ContentValues values = new ContentValues();
        values.put("rubros",rubros);
        values.put("valor",valor);
        values.put("numero_factura",numero_factura);
        values.put("id_servicio", servicio);
        insert(values);
        close();
    }
    public void delete(String servicio ) throws Exception {
        open();
        deleteComment("id_servicio = '"+servicio+"'");
        close();
    }
    //consulta facturas or servicio

    public ResultSet getDetalleFacturas(String numero_factura,String idServicio) throws Exception {
        ResultSet res = getData("select rubros,valor from " + TABLE_NAME + " where numero_factura='"+numero_factura+"' and id_servicio='"+idServicio+"'");
        return res;
    }

}
