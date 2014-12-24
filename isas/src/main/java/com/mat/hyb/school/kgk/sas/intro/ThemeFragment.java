package com.mat.hyb.school.kgk.sas.intro;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerPalette;
import com.android.colorpicker.ColorPickerSwatch;
import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EFragment(R.layout.fragment_theme)
public class ThemeFragment extends BaseIntroFragment {

    @ColorRes
    protected int orange;

    @ColorRes
    protected int blue;

    @ColorRes
    protected int red;

    @ColorRes
    protected int pink;

    @ColorRes
    protected int green;

    @ColorRes
    protected int purple;

    @ViewById
    protected ColorPickerPalette palette;

    @Pref
    protected ISASPrefs_ prefs;

    @AfterViews
    protected void init() {
        final int[] colors = new int[] { orange, blue, red, pink, green, purple };
        palette.init(ColorPickerDialog.SIZE_LARGE, 3, new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                palette.drawPalette(colors, color);
                prefs.colorTheme().put(color);
            }
        });
        palette.drawPalette(colors, prefs.colorTheme().get());
    }

    @Override
    protected int getMainColor() {
        return android.R.color.white;
    }

    @Override
    protected int getDarkColor() {
        return R.color.grey;
    }
}
