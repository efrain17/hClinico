package systems.altura.util.layout.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


/**
 * Created by caelvaso on 31/1/16.
 */
public class Adpater extends BaseAdapter {

    private Context context;
    private List list;
    public Adpater(Context context, List list){

        this.context = context;
        this.list = list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        List.LoaderList loader ;
        if (convertView == null) {
            convertView = inflater.inflate(list.layout,parent,false);
            loader = new Loader();
            convertView.setTag(loader);
        } else {
            loader = (List.LoaderList)convertView.getTag();
        }
        loader.start(convertView,list.getRow(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return list.index;
    }

    @Override
    public Object getItem(int position) {
        return list.getRow(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Loader implements List.LoaderList{
        @Override
        public void start(View gridView, ListItem rowData) {
            list.loaderGrid.start(gridView,rowData);
        }
    }

}