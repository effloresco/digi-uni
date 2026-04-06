package university.service;

import university.domain.Teacher;
import university.exceptions.FacultyAlreadyExistsException;
import university.exceptions.FacultyNotFoundException;
import university.exceptions.PersonAlreadyExistsException;
import university.exceptions.PersonNotFoundException;
import university.repository.Repository;
import university.storage.ServiceStorageManager;
import university.storage.TeacherStorageManager;

import java.util.Optional;

public class TeacherService {
    private final Repository<Teacher, String> personRepository;
    private final TeacherStorageManager teacherStorageManager = new TeacherStorageManager();
    private final ServiceStorageManager serviceStorageManager = new ServiceStorageManager();

    public TeacherService(Repository<Teacher, String> repository) {
        this.personRepository = repository;
    }

    public void createTeacher(Teacher person){
        Optional<Teacher> testCopy = personRepository.findById(person.getID());
        testCopy.ifPresent(
                exists -> {throw new PersonAlreadyExistsException("Не вдалось додати викладача з id " + person.getID() + " причина: викладач вже існує");}
        );
        personRepository.add(person);
        teacherStorageManager.saveAllData();
        serviceStorageManager.saveAllData();
    }
    public void deleteTeacher(Teacher person){
        Optional<Teacher> testCopy = personRepository.findById(person.getID());
        testCopy.orElseThrow(
                () -> new PersonNotFoundException("Не вдалось видалити викладача з id " + person.getID() + " причина: не знайдено в репозиторії")
        );
        personRepository.deleteByID(person.getID());
        teacherStorageManager.saveAllData();
        serviceStorageManager.saveAllData();
    }
    public void updateTeacher(String currentId, Teacher person){
        Optional<Teacher> testCopy = personRepository.findById(currentId);
        testCopy.orElseThrow(
                () -> new FacultyNotFoundException("Не вдалось оновити викладача з id " + currentId + " причина: не знайдено в репозиторії")
        );
        String newId = person.getID();
        if(!currentId.equals(newId)){
            personRepository.findById(newId).ifPresent(
                    exists -> {throw new FacultyAlreadyExistsException("Не вдалось оновити викладача з id " + currentId + " причина: викладач з id " + newId + " вже існує");}
            );
        }
        personRepository.deleteByID(currentId);
        personRepository.add(person);
        teacherStorageManager.saveAllData();
        serviceStorageManager.saveAllData();
    }
}
