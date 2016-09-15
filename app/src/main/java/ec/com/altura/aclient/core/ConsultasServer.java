package ec.com.altura.aclient.core;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.StrictMode;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import ec.com.altura.aclient.db.DBCuentas;
import ec.com.altura.aclient.db.DBFacturas;
import ec.com.altura.aclient.out.Login;
import ec.com.altura.aclient.util.Server;
import systems.altura.util.Log;

/**
 * Created by carlo on 28/7/2016.
 */
public class ConsultasServer {
    DBFacturas detalleFactura;
    DBCuentas cuentas;
    Context context;
    DBFacturas facturas;
    public ConsultasServer(Context context){
        this.context = context;
        this.detalleFactura = new DBFacturas(context);
        this.cuentas=new DBCuentas(context);
    }

    public void actualizar() throws Exception {
        ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Actualizando datos, por favor espere...");
        ArrayList<String> al = cuentas.getDatosActualizarServicio(Login.IDUSAARIO);
        for(int x=0;x<al.size();x++) {
            System.out.println(al.get(x));
            agregarServcio(al.get(x).toString(),progress);
        }

        //new MyTask(progress,codigo).execute();
    }
    public void agregarServcio(String codigo,ProgressDialog progress) {

        //ingresa con json en la tabla cuentas
        try {   // codigo,datos_factura,cabecera_factur- esto llega del servidor
            String jsDatosFActura = consultarDetallesParam(codigo);



            String fch2="ini";
            boolean estado=false,valor=false;
            JSONObject JsDatosPaciente =new JSONObject();
            JSONArray jsArrayPAciente =new JSONArray();
            if(jsDatosFActura.length()>0){
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
                //cuentas.insertCuenta(codigo, jsArrayPAciente.toString(), jsCabeceraServicio, Login.IDUSAARIO);
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





}
