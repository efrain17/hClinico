package ec.com.altura.aclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

import dalvik.system.BaseDexClassLoader;
import ec.com.altura.aclient.db.DBCuentas;
import ec.com.altura.aclient.out.CuentasActivity;
import ec.com.altura.aclient.out.Login;
import systems.altura.util.Log;

public class InicioActivity extends AppCompatActivity implements Dialogo_personalizado.OnSimpleDialogListener{
DBCuentas cuentas= new DBCuentas(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Dialogo_personalizado().show(getSupportFragmentManager(), "SimpleDialog");
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
    }
    public void insertar(View view){
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

                String jsCabeceraServicio = "{col:{direccion:0,nombre:1}, row:[[\"Manta Azul\",\"Elias Vargas Vargas\"]]}";

                String codigoServico=String.valueOf(new Random().nextInt());
                //jason
                JSONObject ofac = new JSONObject(jsDatosFActura);
                JSONObject col = ofac.getJSONObject("col");
                JSONArray row = ofac.getJSONArray("row");
                //reccorido del json

               // cuentas.insertCuenta(codigoServico, jsDatosFActura, jsCabeceraServicio, Login.IDUSAARIO);
                Toast.makeText(this, "Se cargaron los datos de la cuentan :)",
                        Toast.LENGTH_SHORT).show();}
        catch (Exception e) {
                Log.e(e);
            }}

    public void servicios(View view){
       // dialogo.show(fragmentManager, "tagAlerta");
        //dialogo.show(fragmentManager2,"ssd");
       // new Dialogo_personalizado().show(getSupportFragmentManager(), "SimpleDialog");
        Intent i = new Intent(this, CuentasActivity.class );
        startActivity(i);
      }


    @Override
    public void onPossitiveButtonClick(String Codigo) {
        Toast.makeText(this, "lo oprimio",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeButtonClick() {

    }
}
