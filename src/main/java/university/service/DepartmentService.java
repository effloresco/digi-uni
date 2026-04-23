package university.service;

import university.domain.Department;
import university.domain.Student;
import university.exceptions.*;
import university.repository.Repository;
import university.storage.DepartmentStorageManager;

import java.util.Optional;

public class DepartmentService {
    private final Repository<Department, String> departmentRepository;
    private final DepartmentStorageManager departmentStorageManager = new DepartmentStorageManager();

    public DepartmentService(Repository<Department, String> repository) {
        this.departmentRepository = repository;
    }

    public Department getDepartment(String id) {
        Optional<Department> departmentOpt = departmentRepository.findById(id);
        return departmentOpt.orElseThrow(
                () -> new DepartmentNotFoundException("Не знайдено кафедру з id: " + id)
        );
    }
    public void createDepartment(Department department){
        Optional<Department> testCopy = departmentRepository.findById(department.getID());
        testCopy.ifPresent(
                exists -> {throw new DepartmentAlreadyExistsException("Не вдалось додати кафедру з id " + department.getID() + " причина: кафедра вже існує");}
        );
        departmentRepository.add(department);
        departmentStorageManager.saveAllData();
    }
    public void deleteDepartment(String department){
        Optional<Department> testCopy = departmentRepository.findById(department);
        testCopy.orElseThrow(
                () -> new DepartmentNotFoundException("Не вдалось видалити кафедру з id " + department + " причина: не знайдено в репозиторії")
        );
        departmentRepository.deleteByID(department);
        departmentStorageManager.saveAllData();
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
        departmentStorageManager.saveAllData();
    }
}
