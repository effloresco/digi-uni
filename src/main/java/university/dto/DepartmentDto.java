package university.dto;

import lombok.Getter;
import university.domain.Faculty;
import university.domain.Person;

@Getter
public class DepartmentDto {
    private final String id;
    private final String name;
    private final String facultyId;
    private final String headId;
    private final String location;
    public DepartmentDto(String id, String name, String facultyId, String headId, String location) {
        this.id = id;
        this.name = name;
        this.facultyId = facultyId;
        this.headId = headId;
        this.location = location;
    }
}
