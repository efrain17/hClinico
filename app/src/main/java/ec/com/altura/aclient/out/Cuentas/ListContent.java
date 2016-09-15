package ec.com.altura.aclient.out.Cuentas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListContent {

    public static final List<ListItem> ITEMS = new ArrayList<ListItem>();

    public static final Map<String, ListItem> ITEM_MAP = new HashMap<String, ListItem>();

    private static final int COUNT = 25;

    static {
        for(int i=0;i<10;i++)
            addItem(createListItem(i,"Hola"+i,"Holadetalle"+i));
    }

    private static void addItem(ListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ListItem createListItem(int position,String master, String details) {
        return new ListItem(String.valueOf(position), master, details);
    }


    public static class ListItem {
        public final String id;
        public final String dir;
        public final String nom;

        public ListItem(String id, String dir, String nom) {
            this.id = id;
            this.dir = dir;
            this.nom = nom;
        }

        @Override
        public String toString() {
            return "id:"+id+" dir:"+dir+" nom:"+nom;
        }
    }
}
