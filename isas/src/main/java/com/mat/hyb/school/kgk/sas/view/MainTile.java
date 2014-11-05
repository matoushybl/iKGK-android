package com.mat.hyb.school.kgk.sas.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mat.hyb.school.kgk.sas.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EViewGroup(R.layout.tile_main)
public class MainTile extends LinearLayout {

    @ViewById
    protected TextView text;

    @ViewById
    protected ImageView icon;

    private int iconId;
    private String text1;

    public MainTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MainTile);
        if (typedArray != null) {
            iconId = typedArray.getResourceId(R.styleable.MainTile_tile_icon, R.drawable.ic_marks);
            text1 = typedArray.getString(R.styleable.MainTile_text);
        }
        setOrientation(VERTICAL);
    }

    @AfterViews
    protected void init() {
        text.setText(text1);
        icon.setImageResource(iconId);
    }
}
