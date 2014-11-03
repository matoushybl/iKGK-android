package com.mat.hyb.school.kgk.sas.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Button;

import com.mat.hyb.school.kgk.sas.R;

import org.androidannotations.annotations.EView;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EView
public class MainButton extends Button {
    public MainButton(Context context) {
        super(context);
        init();
    }

    public MainButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("NewApi")
    private void init() {
        if (Build.VERSION.SDK_INT >= 14) {
            setAllCaps(true);
        }
        if (Build.VERSION.SDK_INT < 11 && getResources() != null) {
            int padding = getResources().getDimensionPixelSize(R.dimen.button_vertical_padding);
            setPadding(0, padding, 0, padding);
        }
        setTextColor(getResources().getColor(R.color.inverseColor));
        setBackgroundResource(R.drawable.button_background);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
    }
}
