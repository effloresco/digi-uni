package university.service;

import university.domain.*;
import university.domain.Student;
import university.repository.Repository;

public class StudentService extends PersonService {
    public StudentService(Repository<Person, String> repository) {
        super(repository);
    }

    public void createStudent(Student student) {
        createPerson(student);
    }

    public void deleteStudent(Student student) {
        deletePerson(student);
    }

    public void updateStudent(String studentID, Student student) {
        updatePerson(studentID, student);
    }

}
