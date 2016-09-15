package ec.com.altura.aclient.core;

import android.content.Context;

import ec.com.altura.aclient.db.DBUser;

/**
 * Created by caelvaso on 10/2/16.
 */
public class User {
    DBUser dbUser;

    public User(Context context){
        dbUser = new DBUser(context);
    }



}
