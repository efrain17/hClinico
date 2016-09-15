package ec.com.altura.aclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

import ec.com.altura.aclient.db.DBCuentas;
import ec.com.altura.aclient.out.CuentasActivity;
import systems.altura.util.Log;

public class Dialogo_personalizado extends android.support.v4.app.DialogFragment
{

    OnSimpleDialogListener listener;
    private EditText txtCodigoS;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.activity_dialogo_personalizado, null);
        txtCodigoS = (EditText) v.findViewById(R.id.editTextHola);
        builder.setView(v)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // almacena la informacion
                        System.out.print("imprimiiiii:   " + txtCodigoS.getText().toString());
                        //escucha el listener
                        listener.onPossitiveButtonClick(txtCodigoS.getText().toString());
                        dialog.cancel();
                    }
                })
       .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               dialog.cancel(); } });
       // .setTitle("Ingrese Código de Servicio");

        builder.setView(v);
        return builder.create();
    }

    public interface OnSimpleDialogListener {
        void onPossitiveButtonClick(String codigo);// Eventos Botón Positivo

        void onNegativeButtonClick();// Eventos Botón Negativo
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnSimpleDialogListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }


    /////////////


}
