package com.mat.hyb.school.kgk.sas.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;
import com.mat.hyb.school.kgk.sas.utility.LunchReminderHelper;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Calendar;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EReceiver
public class DayReceiver extends BroadcastReceiver {

    @Bean
    protected LunchReminderHelper lunchReminderHelper;

    @Pref
    protected ISASPrefs_ prefs;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (prefs.lunchReminder().get()) {
            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.MONTH) != Calendar.JUNE
                    && calendar.get(Calendar.MONTH) != Calendar.JULY
                    && calendar.get(Calendar.DAY_OF_MONTH) == 25) {
                lunchReminderHelper.postNotification();
            }
        }
    }
}
