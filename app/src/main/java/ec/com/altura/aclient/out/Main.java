package ec.com.altura.aclient.out;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.JsonReader;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import ec.com.altura.aclient.ContactanosFragment;
import ec.com.altura.aclient.Dialogo_personalizado;
import ec.com.altura.aclient.MapsActivity;
import ec.com.altura.aclient.R;
import ec.com.altura.aclient.ServiciosFragment;
import ec.com.altura.aclient.db.DBCuentas;
import ec.com.altura.aclient.db.DBFacturas;
import ec.com.altura.aclient.db.DBUser;
import ec.com.altura.aclient.util.Server;
import ec.com.altura.aclient.util.URLData;
import systems.altura.util.Log;
import systems.altura.util.layout.base.Adpater;
import systems.altura.util.layout.base.List;
import systems.altura.util.layout.base.ListItem;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Dialogo_personalizado.OnSimpleDialogListener {
    DBCuentas cuentas = new DBCuentas(this);

    DBFacturas detalleFactura = new DBFacturas(this);
    boolean miFragmenList = false;
    ListFragment lFragment;
    android.support.v4.app.Fragment fragment = null;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // agregar el fragmento
        setContentView(R.layout.activity_main);
        lFragment = new ServiciosFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.contentMain, lFragment)
                .commit();
        miFragmenList = true;

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {

            //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Snackbar.make(view, "Prueba de el boton", Snackbar.LENGTH_LONG)
                //          .setAction("Action", null).show();

                new Dialogo_personalizado().show(getSupportFragmentManager().beginTransaction(), "Agregar Servicio");

                //  agregarServcio(String.valueOf(new Random().nextInt()));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View includedLayout = findViewById(R.id.contentMain);
        ContentMain.LoadCreate(includedLayout);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean fragmentTransaction = false;
        fragment = new Fragment();
        android.support.v4.app.Fragment fragmentOld = null;

        if (id == R.id.nav_contacto) {
            fragmentTransaction = true;
            fragment = new ContactanosFragment();
            fragmentOld = fragment;

        } else if (id == R.id.nav_consumo) {
            miFragmenList = true;

        } else if (id == R.id.nav_contratos) {

        } else if (id == R.id.nav_oficina) {
            startActivity(new Intent(this, MapsActivity.class));
            // fragmentTransaction = true;
            // getFragmentManager().beginTransaction().replace(R.id.contentMain, new MapsActivity()) .commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // si elige algun fragmen
        if (fragmentTransaction) {
            if (miFragmenList) {
                getFragmentManager().beginTransaction().remove(lFragment).commit();
                miFragmenList = false;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, fragment).commit();
        }
        //si no elige ningun pero elige la lista
        else if (miFragmenList) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, fragment).commit();
            getFragmentManager().beginTransaction()
                    .replace(R.id.contentMain, lFragment)
                    .commit();

        }
        // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onPossitiveButtonClick(String codigo) {
        ///////////////////////////////////////////////////////////////
        //fragment = new Fragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, fragment) .commit();

        //agregarServcio(codigo);
         progress = new ProgressDialog(this);
        progress.setMessage("Actualizando datos, por favor espere...");
        new MyTask(progress,codigo).execute();
        //actualizar
        miFragmenList = true;
        fragment = new Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, fragment).commit();
        lFragment = new ServiciosFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.contentMain, lFragment)
                .commit();
        //Toast.makeText(this, "lo oprimio",
        // Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeButtonClick() {

    }

    public void agregarServcio(String codigo) {

        //ingresa con json en la tabla cuentas
        try {   // codigo,datos_factura,cabecera_factur- esto llega del servidor
            String jsDatosFActura = consultarDetallesParam(codigo);
            String jsCabeceraServicio = consultarCabesera(codigo);
            JSONArray JsaCabesera=new JSONArray(jsCabeceraServicio);


            String fch2="ini";
            boolean estado=false,valor=false;
            JSONObject JsDatosPaciente =new JSONObject();
            JSONArray jsArrayPAciente =new JSONArray();
            if(JsaCabesera.length()>0){
                //consultar jason
               //{"idparametros_paciente":"31","nombre":"GLUCOSA","valor":"10","fechaIngreso":"2016\/07\/26"},
                JSONArray jsonarrayCabecera = new JSONArray(jsDatosFActura);
                for (int i=0;i<jsonarrayCabecera.length();i++){
                    JSONObject jsonobject = jsonarrayCabecera.getJSONObject(i);
                    String fch=jsonobject.getString("fechaIngreso");

                    if(!fch.equals(fch2)){
                        if(!fch2.equals("ini")){
                            if(!estado)JsDatosPaciente.put("ESTADO","Saludable");

                            if(!valor) JsDatosPaciente.put("VALOR","*****");
                            valor=false;
                            estado=false;
                            //if(jsonarrayCabecera.length()==i+1)JsDatosPaciente.put("fechaIngreso",fch);
                            jsArrayPAciente.put(JsDatosPaciente);
                            //fch2="ini";
                            JsDatosPaciente=new JSONObject();  }

                        JsDatosPaciente.put("fechaIngreso",fch);

                        if(jsonarrayCabecera.length()==i+1){

                            if(jsonobject.getString("nombre").equals("ESTADO") & estado==false){
                                JsDatosPaciente.put("ESTADO",jsonobject.getString("valor"));
                                estado=true;
                            }
                            if(jsonobject.getString("nombre").equals("VALOR") & valor==false){
                                JsDatosPaciente.put("VALOR",jsonobject.getString("valor"));
                                valor=true;
                            }
                            if(!estado)JsDatosPaciente.put("ESTADO","Saludable");

                            if(!valor) JsDatosPaciente.put("VALOR","*****");
                            valor=false;
                            estado=false;
                            //if(jsonarrayCabecera.length()==i+1)JsDatosPaciente.put("fechaIngreso",fch);
                            jsArrayPAciente.put(JsDatosPaciente);
                            //fch2="ini";
                            JsDatosPaciente=new JSONObject();}

                        fch2=fch; }
                   else  if(jsonarrayCabecera.length()==i+1){

                        if(jsonobject.getString("nombre").equals("ESTADO") & estado==false){
                            JsDatosPaciente.put("ESTADO",jsonobject.getString("valor"));
                            estado=true;
                        }
                        if(jsonobject.getString("nombre").equals("VALOR") & valor==false){
                            JsDatosPaciente.put("VALOR",jsonobject.getString("valor"));
                            valor=true;
                        }
                        if(!estado)JsDatosPaciente.put("ESTADO","Saludable");

                        if(!valor) JsDatosPaciente.put("VALOR","*****");
                        valor=false;
                        estado=false;
                        //if(jsonarrayCabecera.length()==i+1)JsDatosPaciente.put("fechaIngreso",fch);
                        jsArrayPAciente.put(JsDatosPaciente);
                        //fch2="ini";
                        JsDatosPaciente=new JSONObject();}

                    if(jsonobject.getString("nombre").equals("ESTADO") & estado==false){
                        JsDatosPaciente.put("ESTADO",jsonobject.getString("valor"));
                        estado=true;
                    }
                    if(jsonobject.getString("nombre").equals("VALOR") & valor==false){
                        JsDatosPaciente.put("VALOR",jsonobject.getString("valor"));
                        valor=true;
                    }
                    //fechaIngreso
                    }
                cuentas.insertCuenta(codigo, jsArrayPAciente.toString(), jsCabeceraServicio, Login.IDUSAARIO);
                insertarDetalleFactura(codigo,jsDatosFActura);
                progress.setMessage("Paciente agregado correctamente");
            }
            else  //Toast.makeText(this, "Paciente no encontrado", Toast.LENGTH_SHORT).show();
                progress.setMessage("Paciente no registrado");
        } catch (Exception e) {
            Log.e(e);
        }
    }


    public void insertarDetalleFactura(String codigo, String jsDetalleFactura1) throws JSONException {

        JSONArray jsonarrayParametros = new JSONArray(jsDetalleFactura1);

        //String jsDetalleFactura2=consultarRubrosFactura(codigo);
        //{"idparametros_paciente":"31","nombre":"GLUCOSA","valor":"10","fechaIngreso":"2016\/07\/26"},

        try {
            detalleFactura.delete(codigo);
            for (int i=0;i<jsonarrayParametros.length();i++){
                JSONObject jsonobject = jsonarrayParametros.getJSONObject(i);
                detalleFactura.insertFactura(jsonobject.getString("fechaIngreso"), jsonobject.getString("nombre"), jsonobject.getString("valor"), codigo);
            }
        } catch (Exception e) {
            Log.e(e.getMessage());

        }
    }

    public void insertarDetalleFactura2(String codigo) {
        //para json
        JSONObject jsDatosFactura, col;
        JSONArray row;
        String jsDetalleFactura = "{col:{numFactura:0,rubro:1,valor:2}, " +
                "row:[" +
                "[\"1234\",\"Glucosa\",128]," +
                "[\"1234\",\"Creatinina \",30]," +
                "[\"1234\",\"Trigliceridos\",12]," +
                "[\"1234\",\"colesterol LDL\",111]," +
                "[\"1234\",\"colesterol HDL\",50]," +
                "[\"4567\",\"Glucosa\",128]," +
                "[\"4567\",\"Creatinina \",30]," +
                "[\"4567\",\"Trigliceridos\",12]," +
                "[\"4567\",\"colesterol LDL\",111]," +
                "[\"4567\",\"colesterol HDL\",50]," +
                "[\"0001\",\"Glucosa\",128]," +
                "[\"0001\",\"Creatinina \",30]," +
                "[\"0001\",\"Trigliceridos\",12]," +
                "[\"0002\",\"Trigliceridos\",12]," +
                "[\"0002\",\"colesterol LDL\",111]," +
                "[\"0008\",\"colesterol HDL\",50]," +
                "[\"0002\",\"Trigliceridos\",12]," +
                "[\"0004\",\"colesterol LDL\",111]," +
                "[\"0004\",\"colesterol HDL\",50]," +
                "[\"0002\",\"Glucosa\",128]," +
                "[\"0002\",\"Creatinina \",30]" +
                "]" +
                "}";
        //String jsDetalleFactura2=consultarRubrosFactura(codigo);
        String jsDetalleFactura2=jsDetalleFactura;
        try {
            //jsDatosFactura = new JSONObject(jsDetalleFactura);
            jsDatosFactura = new JSONObject(jsDetalleFactura2);
            col = jsDatosFactura.getJSONObject("col");
            row = jsDatosFactura.getJSONArray("row");
            for (int i = 0; i < row.length(); i++) {
            detalleFactura.insertFactura(row.getJSONArray(i).getString(0), row.getJSONArray(i).getString(1), String.valueOf(row.getJSONArray(i).getInt(2)), codigo);
            }
        } catch (Exception e) {
            Log.e(e.getMessage());

        }
    }


    public String consultarCabesera(String codigoServicio) {
        Server s = new Server();
        java.util.List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add (new BasicNameValuePair("VARIABLE", codigoServicio));

        String json = null;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            json = s.getJSON2("http://desarrollo.paratodo.com.ec/efra/paciente.php", params);
            android.util.Log.i("DEMO", json.toString());
        } catch (IOException e) {
            android.util.Log.e("DEMO", "ERROR", e);
        } catch (NoSuchAlgorithmException e) {
            android.util.Log.e("DEMO", "ERROR", e);
        } catch (JSONException e) {
            android.util.Log.e("DEMO", "ERROR", e);
        }
        Log.e(json);
        return json;

    }


    public String consultarDetallesParam(String codigoServicio) {
        Server s = new Server();
        java.util.List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add (new BasicNameValuePair("idPaciente", codigoServicio));

        String json = null;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            json = s.getJSON2("http://desarrollo.paratodo.com.ec/efra/parametros.php", params);
            android.util.Log.i("DEMO", json.toString());
        } catch (IOException e) {
            android.util.Log.e("DEMO", "ERROR", e);
        } catch (NoSuchAlgorithmException e) {
            android.util.Log.e("DEMO", "ERROR", e);
        } catch (JSONException e) {
            android.util.Log.e("DEMO", "ERROR", e);
        }
        Log.e(json);
        return json;

    }






    //{"row":[["9873285","04-02-2016","14-02-2016","39.08","0.00","PAGADA","295"],["9579015","05-01-2016","15-01-2016","29.12","0.00","PAGADA","212"],["9304736","03-12-2015","13-12-2015","31.58","0.00","PAGADA","199"],["9037251","05-11-2015","15-11-2015","30.57","0.00","PAGADA","190"],["8728554","04-10-2015","14-10-2015","30.02","0.00","PAGADA","185"],["8409186","03-09-2015","13-09-2015","36.88","0.00","PAGADA","244"],["8125525","02-08-2015","12-08-2015","49.53","0.00","PAGADA","378"],["7826744","02-07-2015","12-07-2015","31.31","0.00","PAGADA","231"],["7539440","02-06-2015","12-06-2015","49.07","0.00","PAGADA","343"],["7237823","02-05-2015","12-05-2015","34.42","0.00","PAGADA","257"],["6952295","02-04-2015","12-04-2015","25.86","0.00","PAGADA","150"],["6656201","03-03-2015","13-03-2015","14.31","0.00","PAGADA","123"]],
    // "col":{"ESTADO_FACTURA":5,"CONSUMO_KWH":6,"CODIGO_FACTURA":0,"MONTO_FACTURA":3,"FECHA_EMISION":1,"FECHA_VENCIMIENTO":2,"SALDO_FACTURA":4}}
    public String consultarDatosFactura(String codigoServicio) {
        Server s = new Server();
        URLData data = new URLData("query");
        data.put("id_query", "3");
        //1105544051
        data.put("NS", codigoServicio);
        JSONObject json = new JSONObject();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            json = s.getJSON("http://190.214.22.245:8081/amc/i/", data);
            android.util.Log.i("DEMO", json.toString());
        } catch (IOException e) {
            android.util.Log.e("DEMO", "ERROR", e);
        } catch (NoSuchAlgorithmException e) {
            android.util.Log.e("DEMO", "ERROR", e);
        } catch (JSONException e) {
            android.util.Log.e("DEMO", "ERROR", e);
        }
       // "col":{"ESTADO_FACTURA":5,"CONSUMO_KWH":6,"CODIGO_FACTURA":0,"MONTO_FACTURA":3,"FECHA_EMISION":1,"FECHA_VENCIMIENTO":2,"SALDO_FACTURA":4}}
    return json.toString();

    }



    public String consultarRubrosFactura(String codigoServicio) {
        Server s = new Server();
        URLData data = new URLData("query");
        //data.put("id_query", "5");
        //1105544051
        data.put("VARIABLE", codigoServicio);
        JSONObject json = new JSONObject();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            json = s.getJSON("http://desarrollo.paratodo.com.ec/efra/paciente.php", data);
            android.util.Log.i("DEMO", json.toString());
        } catch (IOException e) {
            android.util.Log.e("DEMO", "ERROR", e);
        } catch (NoSuchAlgorithmException e) {
            android.util.Log.e("DEMO", "ERROR", e);
        } catch (JSONException e) {
            android.util.Log.e("DEMO", "ERROR", e);
        }
        // "col":{"ESTADO_FACTURA":5,"CONSUMO_KWH":6,"CODIGO_FACTURA":0,"MONTO_FACTURA":3,"FECHA_EMISION":1,"FECHA_VENCIMIENTO":2,"SALDO_FACTURA":4}}
        Log.e(json.toString());
        return json.toString();

    }



    public class MyTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progress;
        //MainActivity act;
        String codigo;

        public MyTask(ProgressDialog progress,String codigo) {
            this.progress = progress;
            this.codigo = codigo;
        }

        public void onPreExecute() {
            progress.show();
//aquí se puede colocar código a ejecutarse previo
//a la operación
        }

        public void onPostExecute(Void unused) {
//aquí se puede colocar código que
//se ejecutará tras finalizar
            miFragmenList = true;
            fragment = new Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.contentMain, fragment).commit();
            lFragment = new ServiciosFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.contentMain, lFragment)
                    .commit();
            progress.dismiss();
        }

        protected Void doInBackground(Void... params) {

//realizar la operación aquí
            agregarServcio(codigo);

            return null;
        }

    }
}
