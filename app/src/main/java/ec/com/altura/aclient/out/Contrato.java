package ec.com.altura.aclient.out;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ec.com.altura.aclient.R;
import systems.altura.util.layout.base.List;
import systems.altura.util.layout.base.ListItem;

public class Contrato {

    protected View parent;
    protected View view;
    public void Contrato(View parent){
        this.parent = parent;
        this.view = parent.findViewById(R.id.contentMain);
    }
    public View findViewById(int id){
        return view.findViewById(id);
    }
    public static class BasicLoaderGrid implements List.LoaderList {
        public BasicLoaderGrid(){}
        public void start(View gridView,ListItem rowData) {
           ImageView imageView = (ImageView) gridView.findViewById(R.id.rlm_img_lef);
           imageView.setImageResource(rowData.getInt(0));
            TextView rlm_txt_tit = (TextView) gridView.findViewById(R.id.txtServicio);
            rlm_txt_tit.setText(rowData.getString(1));
        }
    }
    public void load() {
        /*ListView listView = (ListView)findViewById(R.id.main_list);
        List list = new List(null,R.layout.row_list_main);
        list.addRow(R.drawable.ic_flash_on_24dp,"Hola Mundo","Sync")
                .addRow(R.drawable.ic_flash_on_24dp,"Hola Mundo","Sync")
                .addRow(R.drawable.ic_flash_on_24dp, "Hola Mundo", "Sync");
        listView.setAdapter(new Adpater(view.getContext(), list));*/
    }
}
