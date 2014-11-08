package com.mat.hyb.school.kgk.sas.utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.DrawableRes;

import com.mat.hyb.school.kgk.sas.activity.ShortcutActivity_;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
public class ShortcutHelper {

    public static void createShortcut(Context context, String title, String type, @DrawableRes int icon) {
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, getShortcutBitmap(context, icon));
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, ShortcutActivity_.intent(context)
                .type(type).flags(Intent.FLAG_ACTIVITY_NEW_TASK).get());
        context.sendBroadcast(intent);
    }


    private static Bitmap getShortcutBitmap(Context context, @DrawableRes int id) {
        return ((BitmapDrawable) context.getResources().getDrawable(id)).getBitmap();
    }
}
