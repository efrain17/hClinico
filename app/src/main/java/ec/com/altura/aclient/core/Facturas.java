package ec.com.altura.aclient.core;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ec.com.altura.aclient.R;
import ec.com.altura.aclient.db.DBCuentas;
import ec.com.altura.aclient.db.DBFacturas;
import ec.com.altura.aclient.out.Login;
import ec.com.altura.aclient.util.Me;
import systems.altura.db.ResultSet;
import systems.altura.util.Log;

/**
 * Created by caelvaso on 25/02/2016.
 */
public class Facturas {
    Context context;
    DBFacturas facturas;
    DBCuentas cuentas;
    public Facturas(Context context){
        this.context = context;
        this.facturas = new DBFacturas(context);
        this.cuentas=new DBCuentas(context);
    }




    public PieData graficaDetalleFactura(String numero_factura,String idServicio) throws Exception {
        facturas.open();

        ArrayList<Entry> valsY = new ArrayList<Entry>();
        ArrayList<String> valsX = new ArrayList<String>();
        JSONObject jsDatosFactura, col;
        JSONArray row;
        ResultSet res = facturas.getDetalleFacturas(numero_factura,idServicio);
        //List<String> datos = new ArrayList<String>();
        int c=0;
        while (res.next()) {
            //llena json cabecera de servicio
            if(Integer.parseInt(res.getString("valor"))>0){
                valsX.add(res.getString("rubros"));
                valsY.add(new Entry(Float.parseFloat(res.getString("valor")), c));
                Log.i("GRAFICO" + res.getString("rubros"));
                c++;
            }
        }
        res.close();
        facturas.close();

        PieDataSet set1 = new PieDataSet(valsY, "Resultados");
        set1.setSliceSpace(2f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        //colors.add(getResources().getColor(R.color.md_cyan_500));
        set1.setColors(Me.COLORS_DEFAULD,context);
        PieData data = new PieData(valsX, set1);
        return data;
    }





    public LineData getConsumosFacturas(String codServicio, TextView consumo) throws Exception {
        cuentas.open();
        //para lista
        //JSONObject jsDatosFactura, col;
        JSONArray jsDatosFactura;
        ArrayList<Entry> valsY = new ArrayList<Entry>();
        String ultimoConsumo = "";
        /*texto que cambia en el xml*/
        TextView textNumUltimo = consumo;

 		/*creamos una lista para los valores X*/
        ArrayList<String> valsX = new ArrayList<String>();

        ResultSet res =cuentas.getDatosFactura(codServicio);
        //List<String> datos = new ArrayList<String>();
        int c = 0;
        while (res.next()) {
            //llena json cabecera de servicio

            //numero factura, fecha, valor , estado
            //numFactura:0,fecha_emision:1,fecha_vencimiento:2,monto:3,saldo:4,estado:5,consumo 6
            jsDatosFactura = new JSONArray(res.getString("datos_factura"));


            for (int i = 0; i < jsDatosFactura.length(); i++) {
                JSONObject jsonobject = jsDatosFactura.getJSONObject(i);
                String fch=jsonobject.getString("fechaIngreso");
                char[] arrayChar = jsonobject.getString("VALOR").toCharArray();
                Log.e(String.valueOf(arrayChar.length));

                valsY.add(new Entry(arrayChar.length, c));
                valsX.add(jsonobject.getString("fechaIngreso"));
                ultimoConsumo = "FFFF";
                c++;
            }
            //list.addRow("4/2/2015","0003","55","No pagado");
        }
        textNumUltimo.setText(ultimoConsumo + "Kwh");

        res.close();
        cuentas.close();
        /*seteamos los valores de Y y los colores*/
        LineDataSet dataset = new LineDataSet(valsY, "Pagos $");
        dataset.setColors(new int[]{R.color.md_blue_500},context);
        dataset.setDrawFilled(true);
        dataset.setLineWidth(5f);
        LineData data2 = new LineData(valsX, dataset);
        return data2;
    }








    public LineData getPagosFacturas(String codServicio, TextView pagos) throws Exception {
        cuentas.open();
        //para lista
        JSONObject jsDatosFactura, col;
        JSONArray row;
        ArrayList<Entry> valsY = new ArrayList<Entry>();
        String ultimoConsumo = "";
        /*texto que cambia en el xml*/
        TextView textNumUltimo = pagos;
 		/*creamos una lista para los valores X*/
        ArrayList<String> valsX = new ArrayList<String>();

 		/*creamos una lista de colores*/
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(R.color.md_light_green_500);

        JSONArray jsDatosFactura2 = new JSONArray();

        ResultSet res = cuentas.getDatosFactura(codServicio);
        //List<String> datos = new ArrayList<String>();
        int c = 0;
        while (res.next()) {
            //llena json cabecera de servicio
            jsDatosFactura2 = new JSONArray(res.getString("datos_factura"));


            for (int i = 0; i < jsDatosFactura2.length(); i++) {
                JSONObject jsonobject = jsDatosFactura2.getJSONObject(i);
                String fch=jsonobject.getString("fechaIngreso");
                String v=jsonobject.getString("VALOR");
                char[] arrayChar = jsonobject.getString("VALOR").toCharArray();
                Log.e(String.valueOf(arrayChar.length));
                valsY.add(new Entry(arrayChar.length, c));
                valsX.add(jsonobject.getString("fechaIngreso"));
                ultimoConsumo = "FFFF";
                c++;
            }
        }
        textNumUltimo.setText(ultimoConsumo + "$");
        res.close();
        cuentas.close();
        /*seteamos los valores de Y y los colores*/
        LineDataSet dataset = new LineDataSet(valsY, "Pagos $");
        dataset.setColors(new int[]{R.color.md_light_green_500},context);
        dataset.setDrawFilled(true);
        dataset.setLineWidth(5f);
        LineData data2 = new LineData(valsX, dataset);
        return data2;


    }
}
