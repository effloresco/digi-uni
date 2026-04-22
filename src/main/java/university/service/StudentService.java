package university.service;

import university.domain.Student;
import university.exceptions.FacultyAlreadyExistsException;
import university.exceptions.FacultyNotFoundException;
import university.exceptions.PersonAlreadyExistsException;
import university.exceptions.PersonNotFoundException;
import university.repository.Repository;
import university.storage.StudentStorageManager;

import java.util.Optional;

public class StudentService {
    private final Repository<Student, String> personRepository;
    private final StudentStorageManager studentStorageManager = new StudentStorageManager();

    public StudentService(Repository<Student, String> repository) {
        this.personRepository = repository;
    }
    public Student getStudent(String id) {
        Optional<Student> studentOpt = personRepository.findById(id);
        return studentOpt.orElseThrow(
                () -> new PersonNotFoundException("Не знайдено студента з id: " + id)
        );
    }
    public void createStudent(Student person){
        Optional<Student> testCopy = personRepository.findById(person.getID());
        testCopy.ifPresent(
                exists -> {throw new PersonAlreadyExistsException("Не вдалось додати студента з id " + person.getID() + " причина: студент вже існує");}
        );
        personRepository.add(person);
        studentStorageManager.saveAllData();
    }
    public void deleteStudent(String studentId){
        Optional<Student> testCopy = personRepository.findById(studentId);
        testCopy.orElseThrow(
                () -> new PersonNotFoundException("Не вдалось видалити студента з id " + studentId + " причина: не знайдено в репозиторії")
        );
        personRepository.deleteByID(studentId);
        studentStorageManager.saveAllData();
    }
    public void updateStudent(String currentId, Student person){
        Optional<Student> testCopy = personRepository.findById(currentId);
        testCopy.orElseThrow(
                () -> new FacultyNotFoundException("Не вдалось оновити студента з id " + currentId + " причина: не знайдено в репозиторії")
        );
        String newId = person.getID();
        if(!currentId.equals(newId)){
            personRepository.findById(newId).ifPresent(
                    exists -> {throw new FacultyAlreadyExistsException("Не вдалось оновити студента з id " + currentId + " причина: студент з id " + newId + " вже існує");}

            );
        }
        personRepository.deleteByID(currentId);
        personRepository.add(person);
        studentStorageManager.saveAllData();
    }
}
