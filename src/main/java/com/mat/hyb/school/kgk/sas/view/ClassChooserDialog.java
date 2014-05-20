package com.mat.hyb.school.kgk.sas.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.provider.ClassID;
import com.mat.hyb.school.kgk.sas.provider.PreferenceProvider;
import com.mat.hyb.school.kgk.sas.provider.PreferenceProvider_;
import com.mat.hyb.school.kgk.sas.provider.TeacherID;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matous on 1.5.14 for iSAS.
 */
public class ClassChooserDialog extends Dialog {

    private List<String> classes = new ArrayList<String>();
    private ClassSelectedListener selectedListener = new ClassSelectedListener() {
        @Override
        public void selected(ClassID id) {

        }
    };

    private Activity activity;
    private PreferenceProvider provider;

    public ClassChooserDialog(Context context) {
        super(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        provider = PreferenceProvider_.getInstance_(context);
        init();
    }

    void init() {
        setContentView(R.layout.dialog_classchooser);
        setCancelable(false);
        ListView listView = (ListView) findViewById(R.id.classes);
        classes = ClassID.getNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, classes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedListener.selected(ClassID.getEnumByName(classes.get(i)));
                dismiss();
            }
        });
        final ClassChooserDialog classDialog = this;
        Button teacher = (Button) findViewById(R.id.teacherButton);
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TeacherChooserDialog dialog = new TeacherChooserDialog(activity);
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        classDialog.dismiss();
                    }
                });
                dialog.setSelectedListener(new TeacherChooserDialog.TeacherSelectedListener() {
                    @Override
                    public void selected(TeacherID id) {
                        provider.setTeacher();
                        provider.setTeacherId(id);
                        provider.setFirstRun();
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
