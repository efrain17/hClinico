package systems.altura.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.Html;
import android.widget.Toast;



/**
 * Created by caelvaso on 14/1/16.
 */
public class Msg {
    Context context;

    public static void i(Context context, String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void i(Context context, String text) {
        i(context, text, Toast.LENGTH_SHORT);
    }

    public static void confirm(Context context, String title, String text, final Click onYes, Click onNo) {
        AlertDialog.Builder build = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton(android.R.string.yes, new ListenerClick(onYes))
                .setNegativeButton(android.R.string.no, new ListenerClick(onNo));
        build.show();
    }

    public static Notify n(Context context, int smallIcon, int largeIcon, String ticker, String title, String text, String info, long time, Action[] actions) {
        Notify notify = new Notify(context);
        notify.getBuilder()
                .setSmallIcon(smallIcon)
                .setTicker(ticker)
                .setWhen(time)
                .setContentTitle(title)
                .setContentText(text)
                .setContentInfo(info)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIcon));
        notify.setHtmlLines(title, text, "")
                .addAction(actions).returnContext();
                //
        return notify;
    }
    public static Notify n(Context context, String text, int icon) {
        Notify notify = new Notify(context);
        notify.getBuilder()
                .setSmallIcon(icon)
                .setTicker("")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("")
                .setContentText(text);
        //
        return notify;
    }

    public static interface Click {
        void on();
    }

    private static class ListenerClick implements DialogInterface.OnClickListener {
        Click click;

        public ListenerClick(Click click) {
            this.click = click;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (this.click != null) click.on();
        }
    }

    @SuppressLint("ParcelCreator")
    public static class Notify extends Notification {
        Context context;
        NotificationManager manager;
        Builder builder;
        int id = 0;

        public Notify(Context context) {
            this.context = context;
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            builder = new Builder(context);
        }

        public Builder getBuilder() {
            return builder;
        }

        public Notify returnContext() {
            Intent resultIntent = new Intent(context, context.getClass());
            resultIntent.setAction(Intent.ACTION_MAIN);
            resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
            builder.setContentIntent(resultPendingIntent);
            return this;
        }

        public Notify setBigText(CharSequence text) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                builder.setStyle(new BigTextStyle().bigText(text));
            }
            return this;
        }

        public Notify setTextLines(CharSequence title, CharSequence sumary, CharSequence text) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            InboxStyle n = new InboxStyle(builder)
                    .setBigContentTitle(title)
                    .setSummaryText(sumary);
            String[] lines = text.toString().split("\n");
            for (String line : lines) {
                n.addLine(line);
            }
            builder.setStyle(n);
            }

            return this;
        }

        public Notify setTextLines(CharSequence text) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            String[] lines = text.toString().split("\n");
            InboxStyle n = new InboxStyle();
            n.setBigContentTitle("")
                    .setSummaryText("");
            for (int i = 0; i < lines.length; i++) {
                n.addLine(lines[i]);
            }
            builder.setStyle(n);
            }
            return this;
        }

        public Notify setHtmlLines(CharSequence title, CharSequence text, CharSequence sumary) {
            String[] lines = text.toString().split("\n");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                InboxStyle n = new InboxStyle(builder);
                n.setBigContentTitle("")
                        .setSummaryText("");
                for (int i = 0; i < lines.length; i++) {
                    n.addLine(Html.fromHtml(lines[i]));
                }
                builder.setStyle(n);
            }
            return this;
        }

        public Notify setProgress(int i) {
            builder.setProgress(100, i, false);
            show(id);
            return this;
        }

        public Notify addAction(Msg.Action[] actions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (actions != null)
                    for (Msg.Action action : actions)
                        builder.addAction(action.icon, action.text, action.intent);
            }
            return this;
        }

        public Notify show(int id) {
            this.id = id;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                manager.notify(id, builder.build());
            }
            return this;
        }
    }

    ;

    public static class Action {
        public int icon;
        public CharSequence text;
        public PendingIntent intent;

        public Action(int icon, CharSequence text, PendingIntent intent) {
            this.icon = icon;
            this.text = text;
            this.intent = intent;
        }

        public Action(int icon, CharSequence text, Context context) {
            this.icon = icon;
            this.text = text;
            Intent resultIntent = new Intent(context, context.getClass());
            resultIntent.setAction(Intent.ACTION_MAIN);
            resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            this.intent = PendingIntent.getActivity(context, 0, resultIntent, 0);
        }
    }


}
