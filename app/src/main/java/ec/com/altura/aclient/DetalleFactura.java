package ec.com.altura.aclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;

import ec.com.altura.aclient.core.Facturas;
import ec.com.altura.aclient.db.DBCuentas;
import ec.com.altura.aclient.db.DBFacturas;

public class DetalleFactura extends AppCompatActivity {
    private PieChart pieChart;
    LineChart lineaChart;
    DBFacturas detalleFActuras=new DBFacturas(this);
    DBCuentas cuenta=new DBCuentas(this);
    String servicio,factura;
    Facturas facturas=new Facturas(this);
    TextView texservicio,fechaEmision,fechaVencimiento, consumo,monto,saldo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_factura);
        //  getActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();

        //seteo de tex
        texservicio=(TextView)findViewById(R.id.txtServicio);
        fechaEmision=(TextView)findViewById(R.id.txtDireccion);
        fechaVencimiento=(TextView)findViewById(R.id.txtTitular);
        consumo=(TextView)findViewById(R.id.textConsumo);
        monto=(TextView)findViewById(R.id.textMonto);
        saldo=(TextView)findViewById(R.id.textSaldo);

        servicio=bundle.getString("servicio");
        factura=bundle.getString("factura");
        texservicio.setText(factura);
        try {
            setdatosFactura();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Parametros de Paciente");
        try {
            graficar();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void graficar() throws Exception {
        pieChart = (PieChart) findViewById(R.id.pieChart);

        /*definimos algunos atributos*/
        pieChart.setHoleRadius(40f);
        pieChart.setDrawYValues(true);
        pieChart.setDrawXValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);
        PieData data = facturas.graficaDetalleFactura(factura, servicio);
        //PieData data =new PieData(set1,valsY);
        //PieData data = new PieData(valsX, set1);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();

        /*Ocutar descripcion*/
        pieChart.setDescription("");
        /*Ocultar leyenda*/
        pieChart.setDrawLegend(false);
    }
    public void setdatosFactura() throws Exception {
        ArrayList<String> d = new ArrayList<String>();
        d=cuenta.getDatosFacturaServicio(servicio,factura);
        fechaEmision.setText(d.get(0));
        fechaVencimiento.setText(d.get(1));
        consumo.setText(d.get(2));
        monto.setText(d.get(3));
        saldo.setText(d.get(4));

    }


}
