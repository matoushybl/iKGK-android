package com.mat.hyb.school.kgk.sas.intro;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;
import com.mat.hyb.school.kgk.sas.utility.ClassID;
import com.mat.hyb.school.kgk.sas.utility.TeacherID;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EFragment(R.layout.fragment_type)
public class AppTypeFragment extends BaseIntroFragment {

    @ViewById
    protected RadioGroup typeRadioGroup;

    @ViewById
    protected TextView selectHeader;

    @ViewById
    protected Spinner spinner;

    @Pref
    protected ISASPrefs_ prefs;

    @AfterViews
    protected void init() {
        List<String> names = ClassID.getNames();
        int selectedId = ClassID.getNames().indexOf(ClassID.getEnumById(prefs.id().get()).getName());
        if (prefs.teacherMode().get()) {
            typeRadioGroup.check(R.id.teacherRadio);
            selectHeader.setText(R.string.typeSelectTeacherText);
            names = TeacherID.getNames();
            selectedId = TeacherID.getNames().indexOf(TeacherID.getEnumById(prefs.id().get()).getName());
        }
        ArrayAdapter<String> data = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item, names);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(data);
        spinner.setSelection(selectedId);

        typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ArrayAdapter<String> data;
                if (checkedId == R.id.studentRadio) {
                    selectHeader.setText(R.string.typeSelectClassText);
                    data = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item,
                                              ClassID.getNames());
                    prefs.teacherMode().put(false);
                    prefs.id().put(ClassID.getEnumByName(ClassID.getNames().get(0)).getId());
                } else {
                    selectHeader.setText(R.string.typeSelectTeacherText);
                    data = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item,
                                              TeacherID.getNames());
                    prefs.teacherMode().put(true);
                    prefs.id().put(TeacherID.getEnumByName(TeacherID.getNames().get(0)).getId());
                }
                data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(data);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int myId = ClassID.getEnumByName(ClassID.getNames().get(position)).getId();
                if (prefs.teacherMode().get()) {
                    myId = TeacherID.getEnumByName(TeacherID.getNames().get(position)).getId();
                }
                prefs.id().put(myId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected int getMainColor() {
        return R.color.red;
    }

    @Override
    protected int getDarkColor() {
        return R.color.darkRed;
    }
}
