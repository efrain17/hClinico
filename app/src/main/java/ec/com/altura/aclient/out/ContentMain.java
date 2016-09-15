package ec.com.altura.aclient.out;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ec.com.altura.aclient.R;
import systems.altura.util.layout.base.List;
import systems.altura.util.layout.base.ListItem;

public class ContentMain extends AppCompatActivity {



    public static class BasicLoaderGrid implements List.LoaderList {
        public BasicLoaderGrid(){}
        public void start(View gridView,ListItem rowData) {
            ImageView imageView = (ImageView) gridView.findViewById(R.id.rlm_img_lef);
            imageView.setImageResource(rowData.getInt(0));
            TextView rlm_txt_tit = (TextView) gridView.findViewById(R.id.txtServicio);
            rlm_txt_tit.setText(rowData.getString(1));
        }
    }
    protected static void LoadCreate(View view) {
        /*ListView listView = (ListView)view.findViewById(R.id.main_list);
        List list = new List(new BasicLoaderGrid(),R.layout.row_list_main);
        list.addRow(R.drawable.ic_flash_on_24dp,"Hola Mundo","Sync")
                .addRow(R.drawable.ic_flash_on_24dp,"Hola Mundo","Sync")
                .addRow(R.drawable.ic_flash_on_24dp, "Hola Mundo", "Sync");
        listView.setAdapter(new Adpater(view.getContext(), list));*/
    }
}
