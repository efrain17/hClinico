package ec.com.altura.aclient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import ec.com.altura.aclient.out.Login;
import ec.com.altura.aclient.out.Main;

public class RegistroUsuarioDialog extends android.support.v4.app.DialogFragment {
    private Login.UserLoginTask mAuthTask = null;
    EditText nombre,correo,correoConfirmado,cedula,contrasena,contrasenaConfir;
    Switch terminos;

    @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {

        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_registro_usuario_dialog);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.activity_registro_usuario_dialog, null);

        //builder.setView(v);

        final EditText txtservicio= new EditText(getActivity());
       // txtCodigoS=new EditText(getActivity());

        // Button signin = (Button) v.findViewById(R.id.entrar_boton);
        // (TextView) v.findViewById(R.id.TexviewServicio).setTextAlignment("de");
        nombre=(EditText) v.findViewById(R.id.textNombre);
        correo=(EditText) v.findViewById(R.id.textEmail);
        correoConfirmado=(EditText) v.findViewById(R.id.textConfirEmail);
        cedula=(EditText) v.findViewById(R.id.textCedula);
        contrasena=(EditText) v.findViewById(R.id.textContrasena);
        contrasenaConfir=(EditText) v.findViewById(R.id.textConfirContrasena);
        terminos=(Switch) v.findViewById(R.id.switchEstado);

        builder.setView(v)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        attemptLogin();
                        System.out.print("Hola que hace XD");
                        //dialog.cancel();
                    }
                })

                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
     //   builder.setView(txtCodigoS);
        builder.setView(v);
        return builder.create();
    }
////////////////////////////////////////////////////////////////////////////////////////////
@Override
public void onStart()
{
    super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
    AlertDialog d = (AlertDialog)getDialog();
    if(d != null)
    {
        Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { attemptLogin();
                Boolean wantToCloseDialog = false;
                //Do stuff, possibly set wantToCloseDialog to true then...
                if(wantToCloseDialog)
                    dismiss();
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
    }}


    /////////////////////////////////////////////////////////////////////////////////////////
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        nombre.setError(null);
        correo.setError(null);
        correoConfirmado.setError(null);
        cedula.setError(null);
        contrasena.setError(null);
        contrasenaConfir.setError(null);
        terminos.setError(null);


        // Store values at the time of the login attempt.
        String varnombre= nombre.getText().toString();
        String varcorreo= correo.getText().toString();
        String varcorreoConfir= correoConfirmado.getText().toString();
        String varcedula= cedula.getText().toString();
        String varcontrasena= contrasena.getText().toString();
        String varcontrasenaConfir =contrasenaConfir.getText().toString();

        boolean cancel = false;
        View focusView = null;
        if(!terminos.isChecked()){
            terminos.setError("Terms not accepted");
            focusView = terminos;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(varcontrasena)) {
            contrasena.setError(getString(R.string.error_invalid_password));
            focusView = contrasena;
            cancel = true;
        } else if(!varcontrasena.equals(varcontrasenaConfir)){
            contrasena.setError(getString(R.string.error_incorrect_password));
            focusView = contrasena;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(varcorreo)) {
            correo.setError(getString(R.string.error_field_required));
            focusView = correo;
            cancel = true;
        } else if (!isEmailValid(varcorreo)) {
            correo.setError(getString(R.string.error_invalid_email));
            focusView = correo;
            cancel = true;
        }else if (!varcorreo.equals(varcorreoConfir)) {
            correo.setError(getString(R.string.error_invalid_email));
            focusView = correo;
            cancel = true;
        }
        //cedula
        if (TextUtils.isEmpty(varcedula)) {
            cedula.setError(getString(R.string.error_field_required));
            focusView = cedula;
            cancel = true;
        }
        //nombre
        if (TextUtils.isEmpty(varnombre)) {
            nombre.setError(getString(R.string.error_field_required));
            focusView = nombre;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
    }

    /////////////////////
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
}
