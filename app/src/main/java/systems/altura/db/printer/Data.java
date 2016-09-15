package systems.altura.db.printer;

import systems.altura.db.ResultSet;

/**
 * Created by caelvaso on 26/1/16.
 */
public interface Data<type> {
    public type get() throws Exception;
    public void set(ResultSet data) throws Exception;
}
