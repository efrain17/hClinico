package systems.altura.db.printer;

import org.json.JSONArray;
import org.json.JSONObject;

import systems.altura.db.ResultSet;


public class JSONData implements Data<JSONObject>{
    ResultSet data;
    @Override
    public JSONObject get() throws Exception {
        if(data==null)
            return null;
        JSONObject json = new JSONObject();
        JSONObject col = new JSONObject();
        JSONArray rows = new JSONArray();
        int c = data.getColumnCount();
        for(int i=0;i<c;i++){
            col.put(data.getColumnName(i),i);
        }
        json.put("col", col);
        String sep = "";
        while(data.next()){
            JSONArray row = new JSONArray();
            for(int i=0;i<c;i++){
                String cell = data.getString(i);
                row.put(cell==null?"":cell);
            }
            rows.put(row);
        }
        json.put("row", rows);
        data.close();
        return json;
    }

    @Override
    public void set(ResultSet data) throws Exception {
        this.data = data;
    }
}
