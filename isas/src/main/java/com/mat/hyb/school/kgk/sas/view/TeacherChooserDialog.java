package com.mat.hyb.school.kgk.sas.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.utility.TeacherID;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
public class TeacherChooserDialog extends Dialog {

    private List<String> classes = new ArrayList<String>();
    private TeacherSelectedListener selectedListener = new TeacherSelectedListener() {
        @Override
        public void selected(TeacherID id) {

        }
    };

    public TeacherChooserDialog(Context context) {
        super(context);
        init();
    }


    private void init() {
        setCancelable(false);
        ListView listView = new ListView(getContext());
        classes = TeacherID.getNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, classes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedListener.selected(TeacherID.getEnumByName(classes.get(i)));
                dismiss();
            }
        });
        setContentView(listView);
        setTitle(getContext().getString(R.string.teacher_dialog_title));
    }

    public void setSelectedListener(TeacherSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }

    public interface TeacherSelectedListener {
        void selected(TeacherID id);
    }
}
