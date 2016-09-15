package ec.com.altura.aclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.LineData;

import ec.com.altura.aclient.core.Facturas;

public class DetalleConsumo extends AppCompatActivity {
    private PieChart pieChart;
    LineChart lineaChart;
    Button btnGraficar;
    boolean graficarCosumosBool=false;
    TextView textUltimoText,textNumUltimo,textServicio,textTitular,textDireccion;
    String servicio="";
    Facturas factura=new Facturas(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_consumo);
        //  getActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Estadistica de salud");
        btnGraficar=(Button)findViewById(R.id.btnCambiarGraf);
        textUltimoText=(TextView)findViewById(R.id.textUltConsumo);
        textNumUltimo=(TextView)findViewById(R.id.textConsumo);

        textServicio=(TextView)findViewById(R.id.txtServicio);
        textDireccion=(TextView)findViewById(R.id.txtDireccion);
        textTitular=(TextView)findViewById(R.id.txtTitular);



        Bundle bundle = getIntent().getExtras();
        servicio=bundle.getString("servicio");
        textServicio.setText(servicio);
        textTitular.setText(bundle.getString("titular"));
        textDireccion.setText(bundle.getString("direccion"));
        try {
            graficarConsumos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnGraficar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(graficarCosumosBool){
                    try {
                        graficarConsumos(); }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    graficarCosumosBool=false;}
                else {
                    try {
                        graficarPagos();
                        graficarCosumosBool=true;
                        }
                    catch (Exception e) {
                        e.printStackTrace();
                        }
                    }
            }
        });


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

    public void graficarConsumos() throws Exception {
        // pieChart = (PieChart) findViewById(R.id.pieChart);
        lineaChart = (LineChart) findViewById(R.id.lineChart);

        /*definimos algunos atributos*/
        lineaChart.setHighlightLineWidth(100f);
        lineaChart.setDrawYValues(true);
        //lineaChart.setDrawXValues(true);
        //lineaChart.setRotationEnabled(true);
        lineaChart.animateXY(1500, 1500);

        textUltimoText.setText("Ultimo consumo");

        LineData data2 = factura.getConsumosFacturas(servicio,textNumUltimo);
        lineaChart.setData(data2); // set the data and list of lables into chart
    }


    public void graficarPagos() throws Exception {
        // pieChart = (PieChart) findViewById(R.id.pieChart);
        lineaChart = (LineChart) findViewById(R.id.lineChart);

        /*definimos algunos atributos*/
        lineaChart.setHighlightLineWidth(100f);
        lineaChart.setDrawYValues(true);
        //lineaChart.setDrawXValues(true);
        //lineaChart.setRotationEnabled(true);
        lineaChart.animateXY(1500, 1500);

        textUltimoText.setText("Ultimo pago");

        LineData data2 = factura.getPagosFacturas(servicio,textNumUltimo);
        lineaChart.setData(data2); // set the data and list of lables into chart
    }






}
