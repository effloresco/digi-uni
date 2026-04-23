package university.service;

import university.domain.Student;
import university.domain.Teacher;
import university.exceptions.FacultyAlreadyExistsException;
import university.exceptions.FacultyNotFoundException;
import university.exceptions.PersonAlreadyExistsException;
import university.exceptions.PersonNotFoundException;
import university.repository.Repository;
import university.storage.TeacherStorageManager;

import java.util.Optional;

public class TeacherService {
    private final Repository<Teacher, String> personRepository;
    private final TeacherStorageManager teacherStorageManager = new TeacherStorageManager();

    public TeacherService(Repository<Teacher, String> repository) {
        this.personRepository = repository;
    }

    public Teacher getTeacher(String id) {
        Optional<Teacher> teacherOpt = personRepository.findById(id);
        return teacherOpt.orElseThrow(
                () -> new PersonNotFoundException("Не знайдено викладача з id: " + id)
        );
    }
    public void createTeacher(Teacher person){
        Optional<Teacher> testCopy = personRepository.findById(person.getID());
        testCopy.ifPresent(
                exists -> {throw new PersonAlreadyExistsException("Не вдалось додати викладача з id " + person.getID() + " причина: викладач вже існує");}
        );
        personRepository.add(person);
        teacherStorageManager.saveAllData();
    } 
    public void deleteTeacher(String person){
        Optional<Teacher> testCopy = personRepository.findById(person);
        testCopy.orElseThrow(
                () -> new PersonNotFoundException("Не вдалось видалити викладача з id " + person + " причина: не знайдено в репозиторії")
        );
        personRepository.deleteByID(person);
        teacherStorageManager.saveAllData();
    }
    public void updateTeacher(String currentId, Teacher person){
        Optional<Teacher> testCopy = personRepository.findById(currentId);
        testCopy.orElseThrow(
                () -> new PersonNotFoundException("Не вдалось оновити викладача з id " + currentId + " причина: не знайдено в репозиторії")
        );
        String newId = person.getID();
        if(!currentId.equals(newId)){
            personRepository.findById(newId).ifPresent(
                    exists -> {throw new PersonAlreadyExistsException("Не вдалось оновити викладача з id " + currentId + " причина: викладач з id " + newId + " вже існує");}
            );
        }
        personRepository.deleteByID(currentId);
        personRepository.add(person);
        teacherStorageManager.saveAllData();
    }
}
