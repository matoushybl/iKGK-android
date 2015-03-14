package com.mat.hyb.school.kgk.sas.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.model.ClassModel;
import com.mat.hyb.school.kgk.sas.model.ClassModel$;
import com.mat.hyb.school.kgk.sas.model.TeacherModel;
import com.mat.hyb.school.kgk.sas.model.TeacherModel$;

import java.util.List;

import static org.brightify.torch.TorchService.torch;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
public final class ChooserDialogCreator {

    public static void showDialog(final Activity activity, final OnChoseListener onChoseListener) {
        final List<String> classNames = ModelUtils.getClassNames();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.class_dialog_title);
        builder.setItems(classNames.toArray(new String[classNames.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long id = torch().load()
                                 .type(ClassModel.class)
                                 .filter(ClassModel$.name.equalTo(classNames.get(which)))
                                 .limit(1)
                                 .single().id;
                if (onChoseListener != null) {
                    onChoseListener.onChose(id, false);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.teacher_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showTeacherDialog(activity, onChoseListener, dialog);
            }
        });
        builder.show();
    }

    private static void showTeacherDialog(Activity activity, final OnChoseListener onChoseListener,
                                          final DialogInterface baseDialog) {
        final List<String> teacherNames = ModelUtils.getTeacherNames();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.teacher_dialog_title);
        builder.setItems(teacherNames.toArray(new String[teacherNames.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                baseDialog.dismiss();
                long id = torch().load()
                                 .type(TeacherModel.class)
                                 .filter(TeacherModel$.name.equalTo(teacherNames.get(which)))
                                 .limit(1)
                                 .single().id;
                if (onChoseListener != null) {
                    onChoseListener.onChose(id, true);
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public interface OnChoseListener {
        void onChose(long id, boolean isTeacher);
    }
}
