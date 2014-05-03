package com.mat.hyb.school.isas.provider;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Calendar;

/**
 * Created by matous on 5.12.13.
 */
@EBean
public class UrlProvider {

    public static final String WEBSITE = "http://www.gymkyjov.cz/";
    private static final String MOODLE_URL = "http://www.gymkyjov.cz/moodle";
    private static final String CANTEEN_URL = "http://www.gymkyjov.cz:8082";
    private static final String SUBSTITUTION_URL =
            "http://www.gymkyjov.cz/isas/suplovani.php?zobraz=tridy-1&suplovani=";
    private static final String DATE = "&rezim=den&datum=";
    private static final String MARKS = "http://www.gymkyjov.cz/isas/prubezna-klasifikace.php";
    private static final String TIMETABLE =
            "http://www.gymkyjov.cz/isas/rozvrh-hodin.php?zobraz=tridy-1&rozvrh=";
    @Bean
    PreferenceProvider preferencesProvider;

    public String getMoodleUrl() {
        return MOODLE_URL;
    }

    public String getCanteenUrl() {
        return CANTEEN_URL;
    }

    public String getSubstitutionTodayUrl() {
        return SUBSTITUTION_URL + getSavedClass();
    }

    public String getSubstitutionTomorrowUrl() {
        return SUBSTITUTION_URL + getSavedClass() + DATE + getTomorrowDate();
    }

    public String getMarksUrl() {
        return MARKS;
    }

    private String getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 6 && calendar.get(Calendar.HOUR_OF_DAY) > 16) {
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
        if (calendar.get(Calendar.HOUR_OF_DAY) > 16) {
            return getSubstitutionTomorrowUrl();
        } else {
            return getSubstitutionTodayUrl();
        }
    }

    private String getSavedClass() {
        return String.valueOf(preferencesProvider.getDefaultClass().getId());
    }

    private int getSavedClassId() {
        return preferencesProvider.getDefaultClass().getId();
    }

    public String getTimetableUrl(int id) {
        return TIMETABLE + Integer.valueOf(id);
    }

    public String getOurTimetableUrl() {
        return getTimetableUrl(getSavedClassId());
    }

}