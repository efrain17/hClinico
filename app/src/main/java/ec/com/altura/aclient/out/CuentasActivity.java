package ec.com.altura.aclient.out;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ec.com.altura.aclient.Dialogo_personalizado;
import ec.com.altura.aclient.R;
import ec.com.altura.aclient.db.DBCuentas;
import ec.com.altura.aclient.db.DBUser;
import systems.altura.db.printer.DatosCuenta;
import systems.altura.util.Log;
import systems.altura.util.layout.base.Adpater;
import systems.altura.util.layout.base.ListItem;

public class CuentasActivity extends AppCompatActivity implements Dialogo_personalizado.OnSimpleDialogListener{

    DBUser user1= new DBUser(this);
    DBCuentas cuentas= new DBCuentas(this);
    private ListView lvServicios;
    systems.altura.util.layout.base.List list ;
    ListView listView ;
    List TAREAS  = new ArrayList<DatosCuenta>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //lvServicios =(ListView)findViewById(R.id.lvServicios);cd ...
        listView = (ListView)findViewById(R.id.lvServicios);

        //list.addRow("Servicio 001","El colibri ","Juan Carlos Lopes Marcillo");
        // parametrizar la lista
       cargarLista();
       /* try {
            list =cuentas.getCabeceraServicio();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        listView.setAdapter(new Adpater(listView.getContext(), list));

        // evento al hacer click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                // Intent i = new Intent(null, Factura.class );
                // startActivity(i);
               // envia parametros
                Intent intent = new Intent(CuentasActivity.this, Factura.class);
                intent.putExtra("servicio", list.getRow(position).getString(0));
                intent.putExtra("titular",  list.getRow(position).getString(2));
                intent.putExtra("direccion", list.getRow(position).getString(1));
                // llama a la actividad factura
                CuentasActivity.this.startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View view) {
                // llama al dialogo
                new Dialogo_personalizado().show(getSupportFragmentManager(), "Agregar Servicio");
                // Snackbar.make(view, "Agregar Servicios", Snackbar.LENGTH_LONG)
              //          .setAction("Action", null).show();
            }
        });}





    public static class BasicLoaderGrid implements systems.altura.util.layout.base.List.LoaderList {
        public BasicLoaderGrid(){}
        public void start(View rowView,ListItem rowData) {
            ImageView imageView = (ImageView) rowView.findViewById(R.id.rlm_img_lef);
            //imageView.setImageResource(rowData.getInt(0));
            TextView rlm_txt_tit = (TextView) rowView.findViewById(R.id.txtServicio);
            rlm_txt_tit.setText(rowData.getString(0));
            TextView rlm_txt_dir = (TextView) rowView.findViewById(R.id.txtDireccion);
            rlm_txt_dir.setText(rowData.getString(1));
            TextView rlm_txt_nom = (TextView) rowView.findViewById(R.id.txtTitular);
            rlm_txt_nom.setText(rowData.getString(2));
            Log.i(rowData.getString(0));
        }
    }
    public void cargarLista(){
        list = new systems.altura.util.layout.base.List(new BasicLoaderGrid(),R.layout.row_list_main);
        list = new systems.altura.util.layout.base.List(new BasicLoaderGrid(),R.layout.row_list_main);
        //list.addRow("Servicio 001","El colibri ","Juan Carlos Lopes Marcillo");
        // parametrizar la lista
        try {
            list =cuentas.getCabeceraServicio(Login.IDUSAARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listView.setAdapter(new Adpater(listView.getContext(), list));
        }


    ////// interfas del dialogo
    @Override
    public void onPossitiveButtonClick(String codigo) {

        agregarServcio(codigo);
        cargarLista();
        //Toast.makeText(this, "lo oprimio",
               // Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeButtonClick() {

    }


    public void agregarServcio(String codigo){

        //ingresa con json en la tabla cuentas
        try {   // codigo,datos_factura,cabecera_factur- esto llega del servidor
            String jsDatosFActura= "{col:{numFactura:0,fecha:1,valor:2,saldo:3,estado:4,consumo:5}, " +
                    "row:[" +
                    "[\"1234\",\"enero\",90,0,\"No pagado\",122]," +
                    "[\"4567\",\"febrero\",90,0,\"No pagado\",122]," +
                    "[\"8921\",\"marzo\",90,0,\"Pagago\",122]," +
                    "[\"8923\",\"abril\",90,0,\"activo\",122]," +
                    "[\"4567\",\"mayo\",90,0,\"Pagado\",122]," +
                    "[\"0001\",\"junio\",90,0,\"No pagado\",122]," +
                    "[\"0001\",\"julio\",90,0,\"No pagado\",122]," +
                    "[\"0002\",\"agosto\",90,0,\"No pagado\",122]," +
                    "[\"0008\",\"septiembre\",90,0,\"No pagado\",122]," +
                    "[\"0004\",\"octubre\",90,0,\"No pagado\",122]" +
                    "]" +
                    "}";
            //  String jsDatosFActura= "{col:{numFactura:0,fecha:1,valor:2,saldo:3,estado:4,consumo:5}, " +
            //        "row:[[\"1234\",\"enero\",90,0,\"No pagado\",122],[\"4567\",\"febrero\",90,0,\"No pagado\",122]]}";
            // esto tambien llega del servidor
            String jsCabeceraServicio = "{col:{direccion:0,nombre:1}, row:[[\"Manta Azul\",\"Elias Vargas Vargas\"]]}";

            //esto lo ingresa el usuario
            // Random ram=new Random();
            //int i=new Random().nextInt();
            String codigoServico=codigo;
            //jason
            JSONObject ofac = new JSONObject(jsDatosFActura);
            JSONObject col = ofac.getJSONObject("col");
            JSONArray row = ofac.getJSONArray("row");
            //reccorido del json
                /*for(int i =0; i<row.length();i++){
                    System.out.println(row.getJSONArray(i).getString(0));
                    //  System.out.println(row.getJSONArray(i).getString(1));
                }*/

          //  cuentas.insertCuenta(codigoServico, jsDatosFActura, jsCabeceraServicio,Login.IDUSAARIO);
            Toast.makeText(this, "Servicio agregado Correctamente",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Log.e(e);
        }
    }



}

