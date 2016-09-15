package systems.altura.db.printer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import systems.altura.db.ResultSet;
import systems.altura.util.layout.base.List;


public class StringArrayRowData implements Data<String[]>{
    ResultSet data;
    @Override
    public String[] get() throws Exception {
        if(data==null)
            return null;
        int c = data.getColumnCount();
        String[] salida = new String[c];
        //while(data.next()){
            for(int i=0;i<c;i++){
                salida[i]=(data.getString(i));
            }
        //}
        data.close();
        return salida;
    }

    @Override
    public void set(ResultSet data) throws Exception {
        this.data = data;
    }
}
