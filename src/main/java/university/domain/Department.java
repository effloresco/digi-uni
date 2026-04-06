package university.domain;

import lombok.Getter;
import university.exceptions.InvalidValue;
import university.repository.FacultyRepository;
import university.repository.TeacherRepository;

import java.util.Optional;

import static university.service.Utils.*;

@Getter
public class Department implements Entity<String> {
    private static int idCounter = 0;

    protected final FacultyRepository facultyRepository = FacultyRepository.get(FacultyRepository.class);
    protected final TeacherRepository teacherRepository = TeacherRepository.get(TeacherRepository.class);
    private String id;
    private String name;
    private Faculty faculty;
    private Person head;
    private String location;

    public Department() {
        id = String.valueOf(idCounter++);
    }

    public Department(String id, String name, Faculty faculty, Person head, String location) throws InvalidValue {
        this.id = id;
        setName(name);
        setFaculty(faculty.getID());
        setHead(head.getID());
        setLocation(location);
    }

    public void setName(String name) throws InvalidValue {
        if (containsNonLetter(name)) {
            throw new InvalidValue("Назва може містити лише літери");
        }

        this.name = name;
    }

    public void setFaculty(String facultyId) throws InvalidValue {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);

        if (!optionalFaculty.isPresent()) {
            throw new InvalidValue("Факультет з таким ID не знайдено.");
        }
        this.faculty = optionalFaculty.get();
    }

    public void setHead(String teacherId) throws InvalidValue {

        Optional<Teacher> optionalPerson = teacherRepository.findById(teacherId);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            if (!(person instanceof Teacher)) {
                throw new InvalidValue("Ця особа не є викладачем");
            }
        } else {
            throw new InvalidValue("Особу з таким ID не знайдено.");
        }
        this.head = optionalPerson.get();
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
