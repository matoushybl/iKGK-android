package com.mat.hyb.school.kgk.sas.utility;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.support.v4.app.NotificationCompat;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.activity.CanteenActivity_;
import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Calendar;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EBean
public class LunchReminderHelper {

    public static final String ACTION = "com.mat.hyb.school.kgk.isas.dayUpdate";
    private static final int REQUEST_CODE = 69;
    private static final int NOTIFICATION_ID = 122;
    private static final int ACTIVITY_REQUEST_CODE = 123;
    @RootContext
    protected Context context;

    @SystemService
    protected AlarmManager alarmManager;

    @SystemService
    protected NotificationManager notificationManager;

    @Pref
    protected ISASPrefs_ prefs;

    public void enableTiming() {
        Intent intent = new Intent(ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, getStartTime(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void postNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_notification_canteen);
        builder.setContentText(context.getString(R.string.notification_lunch_content));
        builder.setContentTitle(context.getString(R.string.notification_lunch_title));
        builder.setColor(context.getResources().getColor(R.color.primary));
        builder.setContentIntent(getCanteenActivityIntent());
        builder.setAutoCancel(true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private PendingIntent getCanteenActivityIntent() {
        Intent intent;
        if (prefs.externalBrowserMode().get()) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
            intent.setData(Uri.parse(UrlProvider.CANTEEN));
        } else {
            intent = new Intent(context, CanteenActivity_.class);
        }
        return PendingIntent.getActivity(context, ACTIVITY_REQUEST_CODE, intent, 0);
    }

    private long getStartTime() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 17) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTimeInMillis();
    }

}
