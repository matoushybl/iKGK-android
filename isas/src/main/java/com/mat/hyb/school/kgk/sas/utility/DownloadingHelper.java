package com.mat.hyb.school.kgk.sas.utility;

import com.mat.hyb.school.kgk.sas.model.ClassModel;
import com.mat.hyb.school.kgk.sas.model.TeacherModel;
import org.androidannotations.annotations.EBean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.brightify.torch.TorchService.torch;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@EBean
public class DownloadingHelper {

    // FIXME add real urls
    public void loadClasses() throws IOException, JSONException {
        URL url = new URL("http://matoushybl.github.io/KGK-biology/classes.json");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        String rawJson = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            rawJson += line;
        }
        bufferedReader.close();
        JSONObject classesJson = new JSONObject(rawJson);
        JSONArray classesArray = classesJson.getJSONArray("classes");
        JSONObject classObject;
        List<ClassModel> classModels = new ArrayList<>();
        ClassModel classModel;
        for (int i = 0; i < classesArray.length(); i++) {
            classObject = classesArray.getJSONObject(i);
            classModel = new ClassModel();
            classModel.id = classObject.getLong("id");
            classModel.name = classObject.getString("name");
            classModels.add(classModel);
        }
        torch().delete().entities(torch().load().type(ClassModel.class));
        torch().save().entities(classModels);
    }

    public void loadTeachers() throws JSONException, IOException {
        URL url = new URL("http://matoushybl.github.io/KGK-biology/teachers.json");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        String rawJson = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            rawJson += line;
        }
        bufferedReader.close();
        JSONObject teachersJson = new JSONObject(rawJson);
        JSONArray teachersArray = teachersJson.getJSONArray("teachers");
        JSONObject teacherObject;
        List<TeacherModel> teacherModels = new ArrayList<>();
        TeacherModel teacherModel;
        for (int i = 0; i < teachersArray.length(); i++) {
            teacherObject = teachersArray.getJSONObject(i);
            teacherModel = new TeacherModel();
            teacherModel.id = teacherObject.getLong("id");
            teacherModel.name = teacherObject.getString("name");
            teacherModels.add(teacherModel);
        }
        torch().delete().entities(torch().load().type(TeacherModel.class));
        torch().save().entities(teacherModels);
    }
}
