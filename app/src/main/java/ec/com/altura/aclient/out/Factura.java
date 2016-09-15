package ec.com.altura.aclient.out;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import ec.com.altura.aclient.ContactanosFragment;
import ec.com.altura.aclient.DetalleFactura;
import ec.com.altura.aclient.R;
import ec.com.altura.aclient.db.DBCuentas;
import systems.altura.util.Log;
import systems.altura.util.layout.base.Adpater;
import systems.altura.util.layout.base.ListItem;

public class Factura extends AppCompatActivity {
    public TextView textServicio, textTitular, textDireccion;
    DBCuentas cuentas = new DBCuentas(this);
    ListView listView;
    Button btnGraficar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        Bundle bundle = getIntent().getExtras();

        // asignacion
        textServicio = (TextView) findViewById(R.id.textCodServicio);
        textTitular = (TextView) findViewById(R.id.textTitular);
        textDireccion = (TextView) findViewById(R.id.textDireccion);
        //seteo
        textServicio.setText(bundle.getString("servicio"));
        textTitular.setText(bundle.getString("titular"));
        textDireccion.setText(bundle.getString("direccion"));


        // lista de factura
        listView = (ListView) findViewById(R.id.lvFactura);
        btnGraficar=(Button)findViewById(R.id.btnGraficar);
        systems.altura.util.layout.base.List list = new systems.altura.util.layout.base.List(new BasicLoaderGrid(), R.layout.row_factura);

        ///////////////////llama al delatte de la factura
        // list.addRow("4/2/2015","0003","55","No pagado");
        try {
            list = cuentas.getDatosFacturas(bundle.getString("servicio"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        listView.setAdapter(new Adpater(listView.getContext(), list));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, new ContactanosFragment()).commit();
                startActivity(new Intent(Factura.this, DetalleFactura.class));
                // System.out.print("hola que hace :) ");
            }

        });

        btnGraficar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    /////////////////////////////////

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                ///   Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    class BasicLoaderGrid implements systems.altura.util.layout.base.List.LoaderList {
        public BasicLoaderGrid() {
        }

        public void start(View rowView, ListItem rowData) {
            //ImageView imageView = (ImageView) rowView.findViewById(R.id.rlm_img_lef);
            //imageView.setImageResource(rowData.getInt(0));
            //numero factura, fecha, valor , estado
            TextView textNumFactura = (TextView) rowView.findViewById(R.id.textNumFactura);
            textNumFactura.setText(rowData.getString(0));

            TextView textFecha = (TextView) rowView.findViewById(R.id.textFecha);
            textFecha.setText(rowData.getString(1));

            TextView textValor = (TextView) rowView.findViewById(R.id.textValor);
            textValor.setText(rowData.getString(2));

            TextView textEstado = (TextView) rowView.findViewById(R.id.textEstado);
            if (!rowData.getString(3).equals("Pagado")) textEstado.setTextColor(Color.RED);
            else textEstado.setTextColor(Color.GREEN);
            textEstado.setText(rowData.getString(3));


        }
    }
}
