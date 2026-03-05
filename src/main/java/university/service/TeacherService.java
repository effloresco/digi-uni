package university.service;

import university.domain.*;
import university.repository.Repository;

public class TeacherService extends PersonService{
    public TeacherService(Repository<Person, String> repository) {
        super(repository);
    }

    public void createTeacher(Teacher teacher) {
        createPerson(teacher);
    }

    public void deleteTeacher(Teacher teacher) {
        deletePerson(teacher);
    }
}
