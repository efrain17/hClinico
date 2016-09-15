package systems.altura.util.layout.base;

import android.view.LayoutInflater;
import android.view.View;



import java.util.LinkedHashMap;



/**
 * Created by caelvaso on 31/1/16.
 */
public class List {
    public LinkedHashMap<Integer,ListItem> rows;
    public int index = 0;
    LoaderList loaderGrid;
    public int layout;
    public List(LoaderList loaderGrid, int layout){
        this.loaderGrid = loaderGrid;
        this.rows = new LinkedHashMap<Integer, ListItem>();
        this.layout = layout;
    }
    public List addRow(ListItem rowData) {
        this.rows.put(index++, rowData);
        return  this;
    }
    public List addRow(Object ...row) {
        return  addRow(new ListItem().set(row));
    }
    public ListItem getRow(int index){
        return rows.get(index);
    }

    public View loader(View view,int index){
        ListItem rowData = getRow(index);
        loaderGrid.start(view,rowData);
        return view;

    }
    public interface LoaderList {
        public void start(View gridView, ListItem rowData);
    }
}
