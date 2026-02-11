package university.service;

import university.domain.Faculty;
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
                exists -> new IllegalArgumentException("Не можна створити новий факультет з істуючим id")
        );
        facultyRepository.add(faculty);
    }
    public void deleteFaculty(Faculty faculty){
        Optional<Faculty> testCopy = facultyRepository.findById(faculty.getID());
        testCopy.orElseThrow(
                () -> new IllegalArgumentException("Не існує такого факультету")
        );
        facultyRepository.deleteByID(faculty.getID());
    }
    public void updateFaculty(String id, Faculty faculty){
        Optional<Faculty> testCopy = facultyRepository.findById(id);
        testCopy.ifPresentOrElse(
                exists -> {facultyRepository.deleteByID(id); facultyRepository.add(faculty);},
                () -> { throw new IllegalArgumentException("Не існує такого факультету"); }
        );
    }
}
