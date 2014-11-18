package com.mat.hyb.school.kgk.sas.utility;

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
    public static final String PLAY = "https://play.google.com/store/apps/details?id=com.mat.hyb.school.kgk.sas";
    private static final String SUBSTITUTION_TOMORROW_URL = "http://www.gymkyjov.cz/isas/suplovani.php?zobraz=tridy-1&suplovani=%d&rezim=den&datum=%s";
    private static final String SUBSTITUTION_URL =
            "http://www.gymkyjov.cz/isas/suplovani.php?zobraz=tridy-1&suplovani=%d";
    private static final String TIMETABLE =
            "http://www.gymkyjov.cz/isas/rozvrh-hodin.php?zobraz=tridy-1&rozvrh=%d";
    private static final String TEACHER_TIMETABLE
            = "http://www.gymkyjov.cz/isas/rozvrh-hodin.php?zobraz=ucitel&rozvrh=%d";
    private static final String TEACHER_SUBSTITUTION_URL
            = "http://www.gymkyjov.cz/isas/suplovani.php?zobraz=suplujici&suplovani=%d";
    private static final String TEACHER_SUBSTITUTION_TOMORROW_URL
            = "http://www.gymkyjov.cz/isas/suplovani.php?zobraz=suplujici&suplovani=%d&rezim=den&datum=%s";
    @Pref
    protected ISASPrefs_ prefs;

    public String getSubstitutionTodayUrl() {
        String url = SUBSTITUTION_URL;
        if (prefs.teacherMode().get()) {
            url = TEACHER_SUBSTITUTION_URL;
        }
        return String.format(url, prefs.id().get());
    }

    public String getSubstitutionTomorrowUrl() {
        String url = SUBSTITUTION_TOMORROW_URL;
        if (prefs.teacherMode().get()) {
            url = TEACHER_SUBSTITUTION_TOMORROW_URL;
        }
        return String.format(url, prefs.id().get(), getTomorrowDate());
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
        return String.format("%d-%d-%d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public String getSuggestedSubstitutionUrl() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 16 || calendar.get(Calendar.DAY_OF_WEEK) >= 6
                || calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            return getSubstitutionTomorrowUrl();
        } else {
            return getSubstitutionTodayUrl();
        }
    }

    public String getTimetableUrl(int id) {
        String url = TIMETABLE;
        if (prefs.teacherMode().get()) {
            url = TEACHER_TIMETABLE;
        }
        return String.format(url, id);
    }

    public String getOurTimetableUrl() {
        return getTimetableUrl(prefs.id().get());
    }

}