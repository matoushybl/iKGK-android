package com.mat.hyb.school.kgk.sas.intro;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.mat.hyb.school.kgk.sas.R;
import com.mat.hyb.school.kgk.sas.model.ClassModel;
import com.mat.hyb.school.kgk.sas.model.ClassModel$;
import com.mat.hyb.school.kgk.sas.model.TeacherModel;
import com.mat.hyb.school.kgk.sas.model.TeacherModel$;
import com.mat.hyb.school.kgk.sas.settings.ISASPrefs_;
import com.mat.hyb.school.kgk.sas.utility.ModelUtils;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;
import java.util.NoSuchElementException;

import static org.brightify.torch.TorchService.torch;

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

    private final List<String> classNames = ModelUtils.getClassNames();
    private final List<String> teacherNames = ModelUtils.getTeacherNames();

    @AfterViews
    protected void init() {
        List<String> names = classNames;
        // FIXME we should not use this Id because it can cause conflicts create something like torchId
        int selectedId = 0;
        try {
            selectedId = names.indexOf(torch().load().type(ClassModel.class).id(prefs.torchId().get()).name);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        if (prefs.teacherMode().get()) {
            typeRadioGroup.check(R.id.teacherRadio);
            selectHeader.setText(R.string.typeSelectTeacherText);
            names = teacherNames;
            try {
                selectedId = names.indexOf(torch().load().type(TeacherModel.class).id(prefs.torchId().get()).name);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
        initSpinner(names, selectedId);
        initRadioGroup();
    }

    @Override
    protected int getMainColor() {
        return R.color.red;
    }

    @Override
    protected int getDarkColor() {
        return R.color.darkRed;
    }

    private void initRadioGroup() {
        typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ArrayAdapter<String> data;
                if (checkedId == R.id.studentRadio) {
                    selectHeader.setText(R.string.typeSelectClassText);
                    data = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item, classNames);
                    prefs.teacherMode().put(false);
                    prefs.torchId().put(torch().load().type(ClassModel.class).limit(1).single().id);
                } else {
                    selectHeader.setText(R.string.typeSelectTeacherText);
                    data = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item, teacherNames);
                    prefs.teacherMode().put(true);
                    prefs.torchId().put(torch().load().type(TeacherModel.class).limit(1).single().id);
                }
                data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(data);
            }
        });
    }

    private void initSpinner(List<String> names, int selected) {
        ArrayAdapter<String> data = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item, names);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(data);
        spinner.setSelection(selected);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long myId = torch().load()
                                   .type(ClassModel.class)
                                   .filter(ClassModel$.name.equalTo(classNames.get(position)))
                                   .limit(1)
                                   .single().id;
                if (prefs.teacherMode().get()) {
                    myId = torch().load()
                                  .type(TeacherModel.class)
                                  .filter(TeacherModel$.name.equalTo(teacherNames.get(position)))
                                  .limit(1)
                                  .single().id;
                }
                prefs.torchId().put(myId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
