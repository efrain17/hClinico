package ec.com.altura.aclient;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ec.com.altura.aclient.core.ConsultasServer;
import ec.com.altura.aclient.db.DBCuentas;
import ec.com.altura.aclient.out.Login;
import ec.com.altura.aclient.out.Main;
import systems.altura.util.App;
import systems.altura.util.Log;
import systems.altura.util.layout.base.Adpater;
import systems.altura.util.layout.base.ListItem;


/**

 */
public class ServiciosFragment extends ListFragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    //parametros de fragmento
    systems.altura.util.layout.base.List list;
    ListView listView;
    DBCuentas cuentas;
    ConsultasServer server;

    View v;
    Intent i;
    Cursor cursor;

    String[] listitems = {
            "Activity 1",
            "Activity 2",
            "Activity 3"
    };
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        cuentas = new DBCuentas(App.getContext());
        server=new ConsultasServer(App.getContext());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){

        super.onActivityCreated(savedInstanceState);
        try {
            cargarLista();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listitems))

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
                i = new Intent(getActivity(), Factura2.class);
                i.putExtra("servicio", list.getRow(position).getString(0));
                i.putExtra("titular", list.getRow(position).getString(2));
                i.putExtra("direccion", list.getRow(position).getString(1));


        startActivity(i);
    }



    public static class BasicLoaderGrid implements systems.altura.util.layout.base.List.LoaderList {

        public BasicLoaderGrid() {
        }

        public void start(View rowView, ListItem rowData) {
            ImageView imageView = (ImageView) rowView.findViewById(R.id.rlm_img_lef);
            //imageView.setImageResource(rowData.getInt(0));
            TextView rlm_txt_tit = (TextView) rowView.findViewById(R.id.txtServicio);
            //objeMain.agregarServcio(rowData.getString(0));

            rlm_txt_tit.setText(rowData.getString(0));
            TextView rlm_txt_dir = (TextView) rowView.findViewById(R.id.txtDireccion);
            rlm_txt_dir.setText(rowData.getString(1));
            TextView rlm_txt_nom = (TextView) rowView.findViewById(R.id.txtTitular);
            rlm_txt_nom.setText(rowData.getString(2));
            Log.i(rowData.getString(0));
        }
    }

    public void cargarLista() throws Exception {
        //list = new systems.altura.util.layout.base.List(new BasicLoaderGrid(),R.layout.row_list_main);
        list = new systems.altura.util.layout.base.List(new BasicLoaderGrid(), R.layout.row_list_main);
        //list.addRow("Servicio 001","El colibri ","Juan Carlos Lopes Marcillo");
        // parametrizar la lista
        //server.actualizar();
        try {
            list = cuentas.getCabeceraServicio(Login.IDUSAARIO);

        } catch (Exception e) {
            e.printStackTrace();
        }
       setListAdapter(new Adpater(getActivity(), list));
    }
}
