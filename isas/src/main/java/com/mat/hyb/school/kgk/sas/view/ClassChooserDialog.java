package com.mat.hyb.school.kgk.sas.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.activity.BaseActivity;
import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;
import com.mat.hyb.school.kgk.sas.utility.ClassID;
import com.mat.hyb.school.kgk.sas.utility.TeacherID;

import java.util.List;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
public class ClassChooserDialog extends Dialog {

    private ClassSelectedListener selectedListener = new ClassSelectedListener() {
        @Override
        public void selected(ClassID id) {

        }
    };

    public ClassChooserDialog(final Context context) {
        super(context);
        final BaseActivity baseActivity = (BaseActivity) context;
        final ISASPrefs_ prefs = new ISASPrefs_(context);
        setContentView(R.layout.dialog_classchooser);
        setCancelable(false);
        ListView listView = (ListView) findViewById(R.id.classes);
        final List<String> classes = ClassID.getNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, classes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedListener.selected(ClassID.getEnumByName(classes.get(i)));
                prefs.teacherMode().put(false);
                dismiss();
            }
        });
        Button teacher = (Button) findViewById(R.id.teacherButton);
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TeacherChooserDialog dialog = new TeacherChooserDialog(context);
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        ClassChooserDialog.this.dismiss();
                    }
                });
                dialog.setSelectedListener(new TeacherChooserDialog.TeacherSelectedListener() {
                    @Override
                    public void selected(TeacherID id) {
                        prefs.teacherMode().put(true);
                        prefs.id().put(id.getId());
                        prefs.firstRun().put(false);
                        baseActivity.sendEvent(BaseActivity.CATEGORY_TEACHER, "selected");
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        setTitle(getContext().getString(R.string.class_dialog_title));
    }

    public void setSelectedListener(ClassSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }

    public interface ClassSelectedListener {
        void selected(ClassID id);
    }
}
