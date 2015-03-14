package com.mat.hyb.school.kgk.sas.utility;

import com.mat.hyb.school.kgk.sas.model.ClassModel;
import com.mat.hyb.school.kgk.sas.model.TeacherModel;
import org.brightify.torch.util.functional.EditFunction;

import java.util.ArrayList;
import java.util.List;

import static org.brightify.torch.TorchService.torch;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
public final class ModelUtils {

    public static List<String> getClassNames() {
        final List<String> names = new ArrayList<>();
        torch().load().type(ClassModel.class).process().each(new EditFunction<ClassModel>() {
            @Override
            public boolean apply(ClassModel classModel) {
                names.add(classModel.name);
                return false;
            }
        });
        return names;
    }

    public static List<String> getTeacherNames() {
        final List<String> names = new ArrayList<>();
        torch().load().type(TeacherModel.class).process().each(new EditFunction<TeacherModel>() {
            @Override
            public boolean apply(TeacherModel teacherModel) {
                names.add(teacherModel.name);
                return false;
            }
        });
        return names;
    }
}
