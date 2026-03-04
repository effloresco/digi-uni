package university.domain;

import university.exceptions.InvalidValue;
import university.repository.FacultyRepository;
import university.repository.PersonRepository;

import java.util.Optional;

import static university.service.Utils.*;

public class Department implements Entity<String> {
    protected final FacultyRepository facultyRepository = FacultyRepository.get();
    protected final PersonRepository personRepository = PersonRepository.get();
    private String code;
    private String name;
    private Faculty faculty;
    private Teacher head;
    private String location;

    public Department() {
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidValue {
        if (containsNonLetter(name)) {
            throw new InvalidValue("Назва може містити лише літери");
        }

        this.name = name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(String facultyId) throws InvalidValue {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);

        if (!optionalFaculty.isPresent()) {
            throw new InvalidValue("Факультет з таким ID не знайдено.");
        }
        this.faculty = optionalFaculty.get();
    }

    public Person getHead() {
        return head;
    }

    public void setHead(String teacherId) throws InvalidValue {

        Optional<Person> optionalPerson = personRepository.findById(teacherId);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            if (!(person instanceof Teacher)) {
                throw new InvalidValue("Ця особа не є викладачем");
            }
        } else {
            throw new InvalidValue("Особу з таким ID не знайдено.");
        }
        this.head = (Teacher)optionalPerson.get();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) throws InvalidValue {

    }

    @Override
    public String getID() {
        return code;
    }
}
