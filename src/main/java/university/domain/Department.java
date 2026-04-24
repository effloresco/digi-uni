package university.domain;

import lombok.Getter;
import lombok.Setter;
import university.exceptions.InvalidValue;
import university.repository.FacultyRepository;
import university.repository.TeacherRepository;
import university.service.FacultyService;

import java.util.Optional;
import java.util.UUID;

import static university.service.Utils.*;

@Getter
public class Department implements Entity<String> {
    private String id;
    private String name;
    @Setter
    private String facultyId;
    @Setter
    private String headId;
    private String location;

    public Department() {
        id = UUID.randomUUID().toString();
    }

    public Department(String id, String name, String facultyId, String headId, String location) throws InvalidValue {
        this.id = id;
        setName(name);
        setFacultyId(facultyId);
        setHeadId(headId);
        setLocation(location);
    }

    public void setName(String name) throws InvalidValue {
        if (containsNonLetter(name)) {
            throw new InvalidValue("Назва може містити лише літери");
        }

        this.name = name;
    }

    public void setLocation(String location) throws InvalidValue {
        if (location.isEmpty()) throw new InvalidValue("Поле не може бути порожнім");
        this.location = location;
    }

    @Override
    public String getID() {
        return id;
    }
}
