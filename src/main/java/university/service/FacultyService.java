package university.service;

import university.domain.Faculty;
import university.exceptions.FacultyAlreadyExistsException;
import university.exceptions.FacultyNotFoundException;
import university.repository.Repository;

import java.util.Optional;

public class FacultyService{

    private final Repository<Faculty, String> facultyRepository;

    public FacultyService(Repository<Faculty, String> repository) {
        this.facultyRepository = repository;
    }

    public void createFaculty(Faculty faculty){
        Optional<Faculty> testCopy = facultyRepository.findById(faculty.getID());
        testCopy.ifPresent(
                exists -> {throw new FacultyAlreadyExistsException("Не вдалось додати факультет з id " + faculty.getID() + " причина: факультет вже існує");}
        );
        facultyRepository.add(faculty);
    }
    public void deleteFaculty(Faculty faculty){
        Optional<Faculty> testCopy = facultyRepository.findById(faculty.getID());
        testCopy.orElseThrow(
                () -> new FacultyNotFoundException("Не вдалось видалити факультет з id " + faculty.getID() + " причина: не знайдено в репозиторії")
        );
        facultyRepository.deleteByID(faculty.getID());
    }
    public void updateFaculty(String id, Faculty faculty){
        Optional<Faculty> testCopy = facultyRepository.findById(id);
        testCopy.ifPresentOrElse(
                exists -> {facultyRepository.deleteByID(id); facultyRepository.add(faculty);},
                () -> { throw new FacultyNotFoundException("Не вдалось оновити факультет з id " + id + " причина: не знайдено в репозиторії"); }
        );
    }
}
