package com.mat.hyb.school.isas.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mat.hyb.school.isas.R;
import com.mat.hyb.school.isas.provider.ClassID;

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

    public ClassChooserDialog(Context context) {
        super(context);
        init();
    }

    void init() {
        setCancelable(false);
        ListView listView = new ListView(getContext());
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
        setContentView(listView);
        setTitle(getContext().getString(R.string.class_dialog_title));
    }

    public void setSelectedListener(ClassSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }

    public interface ClassSelectedListener {
        void selected(ClassID id);
    }
}
