package com.mat.hyb.school.kgk.sas;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mat.hyb.school.kgk.sas.model.ClassModel$;
import com.mat.hyb.school.kgk.sas.model.TeacherModel$;
import org.androidannotations.annotations.EApplication;
import org.brightify.torch.EntityDescription;
import org.brightify.torch.android.TorchApplication;

import java.util.HashMap;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EApplication
public class BaseApplication extends TorchApplication {

    // FIXME do not do this shit, use only app tracker
    private HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(R.xml.app_tracker)
                                                               :
                        (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                                                                  : analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }

    @Override
    protected EntityDescription<?>[] getMetadataForRegistration() {
        return new EntityDescription[] { ClassModel$.create(), TeacherModel$.create() };
    }

    public enum TrackerName {
        APP_TRACKER, GLOBAL_TRACKER, ECOMMERCE_TRACKER
    }
}
