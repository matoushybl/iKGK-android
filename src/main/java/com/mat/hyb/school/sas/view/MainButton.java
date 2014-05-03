package com.mat.hyb.school.isas.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Button;

import com.mat.hyb.school.isas.R;

import org.androidannotations.annotations.EView;

/**
 * Created by matous on 30.4.14 for iSAS.
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
