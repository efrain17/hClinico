package systems.altura.util.layout.base;

import systems.altura.util.App;

/**
 * Created by caelvaso on 31/1/16.
 */
public class ListItem {

    Object [] row;
    public ListItem(){}
    public ListItem set(Object[] row){
        this.row = row;
        return this;
    }
    public ListItem(Object... row){
        this.row = row;
    }
    public int getInt(int i){
        return (Integer)row[i];
    }
    public String getString(int i){
        return (String)row[i];
    }
    public int getColor(int i){
        return App.colorByIdName(getString(i));
    }
    public int getIcon(int i){
        return App.drawableByIdName(getString(i));
    }
}
