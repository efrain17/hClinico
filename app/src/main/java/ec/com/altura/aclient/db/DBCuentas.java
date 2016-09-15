package ec.com.altura.aclient.db;

import android.content.ContentValues;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


import ec.com.altura.aclient.Factura2;
import ec.com.altura.aclient.R;
import ec.com.altura.aclient.out.ContentMain;
import ec.com.altura.aclient.out.CuentasActivity;
import ec.com.altura.aclient.out.Factura;
import ec.com.altura.aclient.out.Login;
import systems.altura.db.Base;
import systems.altura.db.ResultSet;
import systems.altura.db.printer.StringArrayRowData;
import systems.altura.util.Log;
import systems.altura.util.layout.base.List;
import systems.altura.util.layout.base.ListItem;

/**
 * Created by CarlosEfrain on 10/02/2016.
 */
public class DBCuentas extends Base {


    private final static String TABLE_NAME = "account";
    private final static String TABLE_SQL_CREATE =
            "create table " + TABLE_NAME + "( codigo text primary key, datos_factura text not null, cabecera_servicio text not null," +
                    "id_usuario text not null )";


    static {
        create(TABLE_NAME, TABLE_SQL_CREATE);
    }

    public DBCuentas(Context context) {
        super(context, TABLE_NAME);
    }


    public void insertCuenta(String codigo, String datos_factura, String cabecera_factura, String idUsuario) throws Exception {
        open();
        ContentValues values = new ContentValues();
        values.put("codigo", codigo);
        values.put("datos_factura", datos_factura);
        values.put("cabecera_servicio", cabecera_factura);

        values.put("id_usuario", idUsuario);
        insert(values);
        close();
    }

    //consulta facturas or servicio
    public List getDatosFacturas(String codServicio) throws Exception {
        open();
        //para lista
        final List list = new List(new Factura2.BasicLoaderGrid(), R.layout.row_factura);

        //para json
        JSONObject jsDatos, col;
        JSONArray jsDatosPaciente=new JSONArray();

        JSONObject detail = new JSONObject();

        ResultSet res = getData("select datos_factura from " + TABLE_NAME + " where codigo='" + codServicio + "' and id_usuario='" + Login.IDUSAARIO + "'");
        //List<String> datos = new ArrayList<String>();
        while (res.next()) {
            //llena json cabecera de servicio
            jsDatosPaciente = new JSONArray(res.getString("datos_factura"));
            for (int i=0;i<jsDatosPaciente.length();i++){
                jsDatos=jsDatosPaciente.getJSONObject(i);
                list.addRow("000"+String.valueOf(i), jsDatos.getString("fechaIngreso"),jsDatos.getString("VALOR") ,jsDatos.getString("ESTADO"));

            }
            //numero factura, fecha, valor , estado

        }
        res.close();
        close();
        return list;
    }

    public List getDatosFacturas2(String codServicio) throws Exception {
        open();
        //para lista
        final List list = new List(new Factura2.BasicLoaderGrid(), R.layout.row_factura);

        //para json
        JSONObject jsDatosFactura, col;
        JSONArray row;

        JSONObject detail = new JSONObject();

        ResultSet res = getData("select datos_factura from " + TABLE_NAME + " where codigo='" + codServicio + "' and id_usuario='" + Login.IDUSAARIO + "'");
        //List<String> datos = new ArrayList<String>();
        while (res.next()) {
            //llena json cabecera de servicio
            jsDatosFactura = new JSONObject(res.getString("datos_factura"));
            col = jsDatosFactura.getJSONObject("col");
            row = jsDatosFactura.getJSONArray("row");
            //numero factura, fecha, valor , estado
            //String jsDatosFActura= "{col:{numFactura:0,fecha:1,valor:2,saldo:3,estado:4,consumo:5}, " +//         "row:[[\"1234\",\"enero\",90,0,\"activo\",122],[\"4567\",\"febrero\",90,0,\"activo\",122]]}";
            //numFactura:0,fecha_emision:1,fecha_vencimiento:2,monto:3,saldo:4,estado:5,consumo
            //"col":{"ESTADO_FACTURA":5,"CONSUMO_KWH":6,"CODIGO_FACTURA":0,"MONTO_FACTURA":3,"FECHA_EMISION":1,"FECHA_VENCIMIENTO":2,"SALDO_FACTURA":4}}

            for (int i = 0; i < row.length(); i++) {
                list.addRow(row.getJSONArray(i).getString(0), row.getJSONArray(i).getString(1), String.valueOf(row.getJSONArray(i).getString(3)), row.getJSONArray(i).getString(5));
            }
            //list.addRow("4/2/2015","0003","55","No pagado");
        }
        res.close();
        close();
        return list;
    }


    // consulta encabezado de servicio
    public List getCabeceraServicio(String idUsuario) throws Exception {
        open();
        //para lista
        final List list = new List(new CuentasActivity.BasicLoaderGrid(), R.layout.row_list_main);

        //para json

        JSONArray jsonarrayCabecera;
        ResultSet res = getData("select codigo,cabecera_servicio from " + TABLE_NAME + " where id_usuario='" + idUsuario + "'");
        //List<String> datos = new ArrayList<String>();

        while (res.next()) {
            jsonarrayCabecera = new JSONArray(res.getString("cabecera_servicio"));
            JSONObject jsonobject = jsonarrayCabecera.getJSONObject(0);
            String n=jsonobject.getString("direccion");
            // codigo, direccion y titular
            Log.i("CODIGO" + res.getString("codigo"));
            list.addRow(res.getString("codigo"), jsonobject.getString("direccion"),  jsonobject.getString("nombre"));
        }
        res.close();
        close();
        return list;
    }



    public ArrayList<String> getDatosActualizarServicio(String idUsuario) throws Exception {
        open();
        //para lista

        ArrayList<String> d = new ArrayList<String>();
        //para json

        JSONArray jsDatosFactura;
        ResultSet res = getData("select codigo from " + TABLE_NAME + " where id_usuario='" + idUsuario + "'");
        //List<String> datos = new ArrayList<String>();

        while (res.next()) {
            d.add( res.getString("codigo"));
                }
        res.close();
        close();
        return d;
    }



    public List getCabeceraServicio2(String idUsuario) throws Exception {
        open();
        //para lista
        final List list = new List(new CuentasActivity.BasicLoaderGrid(), R.layout.row_list_main);

        //para json
        JSONObject jsCabecera, col;
        JSONArray row;
        ResultSet res = getData("select codigo,cabecera_servicio from " + TABLE_NAME + " where id_usuario='" + idUsuario + "'");
        //List<String> datos = new ArrayList<String>();

        while (res.next()) {
            //llena json cabecera de servicio
            //String [] srow = (String []) res.getData(new StringArrayRowData());
            //list.addRow(new ListItem().set(srow));
            jsCabecera = new JSONObject(res.getString("cabecera_servicio"));
            col = jsCabecera.getJSONObject("col");
            row = jsCabecera.getJSONArray("row");
            // manda el codigo, direccion y titular
            Log.i("CODIGO" + res.getString("codigo"));
            list.addRow(res.getString("codigo"), row.getJSONArray(0).getString(1), row.getJSONArray(0).getString(0));
        }
        res.close();
        close();
        return list;
    }




    public ResultSet getDatosFactura(String codServicio) throws Exception {
        ResultSet res = getData("select datos_factura from " + TABLE_NAME + " where codigo='" + codServicio + "' and id_usuario='" + Login.IDUSAARIO + "'");
        return res;
    }


    public ArrayList<String> getDatosFacturaServicio(String codServicio,String factura) throws Exception {
        open();
        //para lista

        ArrayList<String> d = new ArrayList<String>();
        //para json

        JSONArray jsDatosFactura;
        ResultSet res = getData("select datos_factura from " + TABLE_NAME + " where id_usuario='" + Login.IDUSAARIO + "'");
        //List<String> datos = new ArrayList<String>();

        while (res.next()) {

            jsDatosFactura = new JSONArray(res.getString("datos_factura"));


            for (int i = 0; i < jsDatosFactura.length(); i++) {
                JSONObject jsonobject = jsDatosFactura.getJSONObject(i);
                String fch=jsonobject.getString("fechaIngreso");

                if(jsonobject.getString("fechaIngreso").equals(factura)){
                //"col":{"ESTADO_FACTURA":5,"CONSUMO_KWH":6,"CODIGO_FACTURA":0,"MONTO_FACTURA":3,"FECHA_EMISION":1,"FECHA_VENCIMIENTO":2,"SALDO_FACTURA":4}}
                   //fechaEmision,fechaVencimiento, consumo,monto,saldo;
                    d.add(jsonobject.getString("fechaIngreso"));
                    d.add("");
                    d.add(jsonobject.getString("fechaIngreso"));
                    d.add(jsonobject.getString("fechaIngreso"));
                    d.add(jsonobject.getString("fechaIngreso"));
                }
                  }
        }
        res.close();
        close();
        return d;
    }











}
