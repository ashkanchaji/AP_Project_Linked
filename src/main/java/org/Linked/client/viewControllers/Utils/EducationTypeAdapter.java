package org.Linked.client.viewControllers.Utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Controller.Exeptions.CharacterNumberLimitException;
import org.Linked.server.Model.Education;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class EducationTypeAdapter extends TypeAdapter<Education> {

    @Override
    public void write(JsonWriter out, Education education) throws IOException {
        out.beginObject();
        out.name("email").value(education.getEmail());
        out.name("collegeName").value(education.getCollegeName());
        out.name("major").value(education.getMajor());
        out.name("enterYear").value(education.getEnterYear() != null ? education.getEnterYear().toString() : null);
        out.name("exitYear").value(education.getExitYear() != null ? education.getExitYear().toString() : null);
        out.name("grade").value(education.getGrade());
        out.name("activitiesInfo").value(education.getActivitiesInfo());
        out.name("skills");
        out.beginArray();
        if (education.getSkills() != null) {
            for (String skill : education.getSkills()) {
                out.value(skill);
            }
        }
        out.endArray();
        out.name("additionalInfo").value(education.getAdditionalInfo());
        out.endObject();
    }

    @Override
    public Education read(JsonReader in) throws IOException {
        String email = null;
        String collegeName = null;
        String major = null;
        Date enterYear = null;
        Date exitYear = null;
        String grade = null;
        String activitiesInfo = null;
        ArrayList<String> skills = new ArrayList<>();
        String additionalInfo = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "email":
                    email = in.nextString();
                    break;
                case "collegeName":
                    collegeName = in.nextString();
                    break;
                case "major":
                    major = in.nextString();
                    break;
                case "enterYear":
                    enterYear = Date.valueOf(in.nextString());
                    break;
                case "exitYear":
                    exitYear = Date.valueOf(in.nextString());
                    break;
                case "grade":
                    grade = in.nextString();
                    break;
                case "activitiesInfo":
                    activitiesInfo = in.nextString();
                    break;
                case "skills":
                    in.beginArray();
                    while (in.hasNext()) {
                        skills.add(in.nextString());
                    }
                    in.endArray();
                    break;
                case "additionalInfo":
                    additionalInfo = in.nextString();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new Education(email, collegeName, major, enterYear, exitYear, grade, activitiesInfo, skills, additionalInfo);
    }
}
