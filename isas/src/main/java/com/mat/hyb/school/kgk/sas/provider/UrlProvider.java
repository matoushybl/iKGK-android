package com.mat.hyb.school.kgk.sas.provider;

import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Calendar;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EBean
public class UrlProvider {

    public static final String WEBSITE = "http://www.gymkyjov.cz/";
    public static final String MOODLE = "http://www.gymkyjov.cz/moodle";
    public static final String CANTEEN = "http://www.gymkyjov.cz:8082";
    public static final String MARKS = "http://www.gymkyjov.cz/isas/prubezna-klasifikace.php";
    private static final String SUBSTITUTION_URL =
            "http://www.gymkyjov.cz/isas/suplovani.php?zobraz=tridy-1&suplovani=";
    private static final String DATE = "&rezim=den&datum=";
    private static final String TIMETABLE =
            "http://www.gymkyjov.cz/isas/rozvrh-hodin.php?zobraz=tridy-1&rozvrh=";
    private static final String TEACHER_TIMETABLE
            = "http://www.gymkyjov.cz/isas/rozvrh-hodin.php?zobraz=ucitel&rozvrh=";

    private static final String TEACHER_SUBSTITUTION_URL
            = "http://www.gymkyjov.cz/isas/suplovani.php?zobraz=suplujici&suplovani=";
    @Pref
    protected ISASPrefs_ prefs;

    public String getSubstitutionTodayUrl() {
        String url = SUBSTITUTION_URL;
        if (prefs.teacherMode().get()) {
            url = TEACHER_SUBSTITUTION_URL;
        }
        return url + getSavedId();
    }

    public String getSubstitutionTomorrowUrl() {
        String url = SUBSTITUTION_URL;
        if (prefs.teacherMode().get()) {
            url = TEACHER_SUBSTITUTION_URL;
        }
        return url + getSavedId() + DATE + getTomorrowDate();
    }

    private String getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 6 && calendar.get(Calendar.HOUR_OF_DAY) >= 16) {
            calendar.add(Calendar.DAY_OF_YEAR, 3);
        } else if (day == 7) {
            calendar.add(Calendar.DAY_OF_YEAR, 2);
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return String.valueOf(calendar.get(Calendar.YEAR)) + "-"
                + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-"
                + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public String getSuggestedDateUrl() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 16 || calendar.get(Calendar.DAY_OF_WEEK) >= 6
                || calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            return getSubstitutionTomorrowUrl();
        } else {
            return getSubstitutionTodayUrl();
        }
    }

    private String getSavedId() {
        return String.valueOf(prefs.id().get());
    }

    private int getSavedClassId() {
        return prefs.id().get();
    }

    public String getTimetableUrl(int id) {
        String url = TIMETABLE;
        if (prefs.teacherMode().get()) {
            url = TEACHER_TIMETABLE;
        }
        return url + Integer.valueOf(id);
    }

    public String getOurTimetableUrl() {
        if (prefs.teacherMode().get()) {
            return getTimetableUrl(prefs.id().get());
        }
        return getTimetableUrl(getSavedClassId());
    }

}