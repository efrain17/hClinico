package systems.altura.util;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by caelvaso on 27/1/16.
 */
public class App extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }

    public static Integer colorByIdName(String name) {
        Resources res = App.getContext().getResources();
        Integer i = res.getIdentifier(name, "color", App.getContext().getPackageName());
        return (i);
    }
    public static Integer drawableByIdName(String name) {
        Resources res = App.getContext().getResources();
        Integer i = res.getIdentifier(name, "drawable", App.getContext().getPackageName());
        return (i);
    }
}