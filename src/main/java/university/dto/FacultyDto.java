package university.dto;

import lombok.Getter;
import university.domain.Teacher;

@Getter
public class  FacultyDto {
    private final String code;
    private final String name;
    private final String shortName;
    private final String deanId;
    private final String contacts;
    public FacultyDto(String code, String name, String shortName, String deanId, String contacts) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.deanId = deanId;
        this.contacts = contacts;
    }
}
