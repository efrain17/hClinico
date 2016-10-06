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

        cargarLista();
        //Toast.makeText(this, "lo oprimio",
               // Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeButtonClick() {

    }






}

