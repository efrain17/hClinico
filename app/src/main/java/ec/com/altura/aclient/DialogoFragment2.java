package ec.com.altura.aclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class DialogoFragment2 extends android.support.v4.app.DialogFragment {
    int mNum;
    private EditText txtCodigoS;
    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static DialogoFragment2 newInstance(int num) {
        DialogoFragment2 f = new DialogoFragment2();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum-1)%6) {
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mNum-1)%6) {
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        inflater = getActivity().getLayoutInflater();
        // getActivity().findViewById(R.id.txtServicioAgregado);

        View v = inflater.inflate(R.layout.activity_dialogo_personalizado, null);

        //builder.setView(v);

        //  final EditText txtservicio= new EditText(getActivity());
        //txtCodigoS=new EditText(getActivity());
        // Dialog dialogo= new Dialogo_personalizado();

        builder.setView(v);
        txtCodigoS = (EditText) v.findViewById(R.id.editTextHola);

        // Button signin = (Button) v.findViewById(R.id.entrar_boton);
        // (TextView) v.findViewById(R.id.TexviewServicio).setTextAlignment("de");

        builder.setView(inflater.inflate(R.layout.activity_dialogo_personalizado, null))
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        // almacena la informacion
                        String h = txtCodigoS.getText().toString();
                        System.out.print("imprimiiiii:   " + txtCodigoS.getText().toString());
                        //escucha el listener
                        // listener.onPossitiveButtonClick(txtCodigoS.getText().toString());
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //Button button = (Button)v.findViewById(R.id.Mibutton);
      /*  button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // When button is clicked, call up to owning activity.
                //((DialogoFragment2) getActivity()).showDialog();
            }
        });*/

       // builder.setView(txtCodigoS);
        builder.setTitle("Ingrese Codigo de Servicio");
        builder.create();

        return v;
    }
}