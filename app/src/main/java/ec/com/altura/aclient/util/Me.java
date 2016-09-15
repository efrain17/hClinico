package ec.com.altura.aclient.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ec.com.altura.aclient.R;
import systems.altura.util.layout.base.List;
import systems.altura.util.layout.base.ListItem;

/**
 * Created by caelvaso on 4/2/16.
 */
public class Me {

    public static String URL_SERVER = "";
    public static int[] COLORS_DEFAULD = {
            R.color.md_deep_purple_500,
            R.color.md_orange_500,
            R.color.md_indigo_500,
            R.color.md_blue_500,
            R.color.md_amber_500,
            R.color.md_cyan_500,
            R.color.md_lime_500
    };

    public static class BasicLoaderGrid implements List.LoaderList {
        public BasicLoaderGrid(){}
        public void start(View gridView,ListItem rowData) {
            ImageView imageView = (ImageView) gridView.findViewById(R.id.rlm_img_lef);
            imageView.setImageResource(rowData.getInt(0));
            TextView rlm_txt_tit = (TextView) gridView.findViewById(R.id.txtServicio);
//            rlm_txt_tit.setText(rowData.getString(1));
        }
    }
    public static List LIST_MAIN =
            new List(new BasicLoaderGrid(),R.layout.row_list_main)
            .addRow(1,"Hola 1","Detalle 1")
            .addRow(1, "Hola 2", "Detalle 2")
            .addRow(1, "Hola 3", "Detalle 3")
                    .addRow(1, "Hola 3", "Detalle 3");
}
