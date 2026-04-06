package university.dto;

import lombok.Getter;
import university.domain.Faculty;
import university.domain.Person;

@Getter
public class DepartmentDto {
    private final String id;
    private final String name;
    private final Faculty faculty;
    private final Person head;
    private final String location;
    public DepartmentDto(String id, String name, Faculty faculty, Person head, String location) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.head = head;
        this.location = location;
    }
}
