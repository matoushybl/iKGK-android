package com.mat.hyb.school.kgk.sas.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mat.hyb.school.kgk.sas.utility.LunchReminderHelper;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EReceiver;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EReceiver
public class BootReceiver extends BroadcastReceiver {

    @Bean
    protected LunchReminderHelper lunchReminderHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        lunchReminderHelper.enableTiming();
    }
}
