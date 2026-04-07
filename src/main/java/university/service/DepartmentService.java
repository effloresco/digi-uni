package university.service;

import university.domain.Department;
import university.domain.Faculty;
import university.exceptions.DepartmentAlreadyExistsException;
import university.exceptions.DepartmentNotFoundException;
import university.exceptions.FacultyAlreadyExistsException;
import university.exceptions.FacultyNotFoundException;
import university.repository.Repository;
import university.storage.DepartmentStorageManager;
import university.storage.ServiceStorageManager;

import java.util.Optional;

public class DepartmentService {
    private final Repository<Department, String> departmentRepository;
    private final DepartmentStorageManager departmentStorageManager = new DepartmentStorageManager();
    private final ServiceStorageManager serviceStorageManager = new ServiceStorageManager();

    public DepartmentService(Repository<Department, String> repository) {
        this.departmentRepository = repository;
    }

    public void createDepartment(Department department){
        Optional<Department> testCopy = departmentRepository.findById(department.getID());
        testCopy.ifPresent(
                exists -> {throw new DepartmentAlreadyExistsException("Не вдалось додати кафедру з id " + department.getID() + " причина: кафедра вже існує");}
        );
        departmentRepository.add(department);
        saveAllData();
    }
    public void deleteDepartment(Department department){
        Optional<Department> testCopy = departmentRepository.findById(department.getID());
        testCopy.orElseThrow(
                () -> new DepartmentNotFoundException("Не вдалось видалити кафедру з id " + department.getID() + " причина: не знайдено в репозиторії")
        );
        departmentRepository.deleteByID(department.getID());
        saveAllData();
    }
    public void updateDepartment(String currentId, Department department){
        Optional<Department> testCopy = departmentRepository.findById(currentId);
        testCopy.orElseThrow(
                () -> new FacultyNotFoundException("Не вдалось оновити кафедру з id " + currentId + " причина: не знайдено в репозиторії")
        );
        String newId = department.getID();
        if(!currentId.equals(newId)){
            departmentRepository.findById(newId).ifPresent(
                    exists -> {throw new FacultyAlreadyExistsException("Не вдалось оновити кафедру з id " + currentId + " причина: кафедра з id " + newId + " вже існує");}

            );
        }
        departmentRepository.deleteByID(currentId);
        departmentRepository.add(department);
        saveAllData();
    }
    public void saveAllData(){
        departmentStorageManager.saveAllData();
        serviceStorageManager.saveAllData();
    }
}
