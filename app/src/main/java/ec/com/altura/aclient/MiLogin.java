package ec.com.altura.aclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ec.com.altura.aclient.out.CuentasActivity;

public class MiLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_login);
    }

    public void registrar(){
        new RegistroUsuarioDialog().show(getSupportFragmentManager(), "Registro de Usuario");
    }

    public void iniciarSecion (){
        Intent i = new Intent(this, CuentasActivity.class );
        startActivity(i);
    }
}
