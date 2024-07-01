package org.Linked.server.Controller.Util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.Linked.server.Model.EducationSkills;

import java.io.IOException;

public class EducationSkillsTypeAdapter extends TypeAdapter<EducationSkills> {

    @Override
    public void write(JsonWriter out, EducationSkills educationSkills) throws IOException {
        out.beginObject();
        out.name("email").value(educationSkills.getEmail());
        out.name("skill1").value(educationSkills.getSkill1());
        out.name("skill2").value(educationSkills.getSkill2());
        out.name("skill3").value(educationSkills.getSkill3());
        out.name("skill4").value(educationSkills.getSkill4());
        out.name("skill5").value(educationSkills.getSkill5());
        out.endObject();
    }

    @Override
    public EducationSkills read(JsonReader in) throws IOException {
        String email = null;
        String skill1 = null;
        String skill2 = null;
        String skill3 = null;
        String skill4 = null;
        String skill5 = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "email":
                    email = in.nextString();
                    break;
                case "skill1":
                    skill1 = in.nextString();
                    break;
                case "skill2":
                    skill2 = in.nextString();
                    break;
                case "skill3":
                    skill3 = in.nextString();
                    break;
                case "skill4":
                    skill4 = in.nextString();
                    break;
                case "skill5":
                    skill5 = in.nextString();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new EducationSkills(email, skill1, skill2, skill3, skill4, skill5);
    }
}
