package university.service;

import university.domain.Faculty;
import university.exceptions.FacultyAlreadyExistsException;
import university.exceptions.FacultyNotFoundException;
import university.repository.Repository;
import university.storage.FacultyStorageManager;

import java.util.Optional;

public class FacultyService{

    private final Repository<Faculty, String> facultyRepository;
    private final FacultyStorageManager facultyStorageManager = new FacultyStorageManager();

    public FacultyService(Repository<Faculty, String> repository) {
        this.facultyRepository = repository;
    }

    public void createFaculty(Faculty faculty){
        Optional<Faculty> testCopy = facultyRepository.findById(faculty.getID());
        testCopy.ifPresent(
                exists -> {throw new FacultyAlreadyExistsException("Не вдалось додати факультет з id " + faculty.getID() + " причина: факультет вже існує"
        );});
        facultyRepository.add(faculty);
        facultyStorageManager.saveAllData();
    }
    public void deleteFaculty(Faculty faculty){
        Optional<Faculty> testCopy = facultyRepository.findById(faculty.getID());
        testCopy.orElseThrow(
                () -> new FacultyNotFoundException("Не вдалось видалити факультет з id " + faculty.getID() + " причина: не знайдено в репозиторії")
        );
        facultyRepository.deleteByID(faculty.getID());
        facultyStorageManager.saveAllData();
    }
    public void updateFaculty(String currentId, Faculty faculty){
        Optional<Faculty> testCopy = facultyRepository.findById(currentId);
        testCopy.orElseThrow(
                () -> new FacultyNotFoundException("Не вдалось оновити факультет з id " + currentId + " причина: не знайдено в репозиторії")
        );
        String newId = faculty.getID();
        if(!currentId.equals(newId)){
            facultyRepository.findById(newId).ifPresent(
                    exists -> {throw new FacultyAlreadyExistsException("Не вдалось оновити факультет з id " + currentId + " причина: факультет з id " + newId + " вже існує");}

            );
        }
        facultyRepository.deleteByID(currentId);
        facultyRepository.add(faculty);
        facultyStorageManager.saveAllData();
    }
}
