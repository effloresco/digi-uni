package university.service;

import university.domain.Department;
import university.exceptions.DepartmentAlreadyExistsException;
import university.exceptions.DepartmentNotFoundException;
import university.repository.Repository;

import java.util.Optional;

public class DepartmentService {
    private final Repository<Department, String> departmentRepository;

    public DepartmentService(Repository<Department, String> repository) {
        this.departmentRepository = repository;
    }

    public void createDepartment(Department department){
        Optional<Department> testCopy = departmentRepository.findById(department.getID());
        testCopy.ifPresent(
                exists -> {throw new DepartmentAlreadyExistsException("Не вдалось додати кафедру з id " + department.getID() + " причина: кафедра вже існує");}
        );
        departmentRepository.add(department);
    }
    public void deleteDepartment(Department department){
        Optional<Department> testCopy = departmentRepository.findById(department.getID());
        testCopy.orElseThrow(
                () -> new DepartmentNotFoundException("Не вдалось видалити кафедру з id " + department.getID() + " причина: не знайдено в репозиторії")
        );
        departmentRepository.deleteByID(department.getID());
    }
    public void updateDepartment(String id, Department department){
        Optional<Department> testCopy = departmentRepository.findById(id);
        testCopy.ifPresentOrElse(
                exists -> {departmentRepository.deleteByID(id); departmentRepository.add(department);},
                () -> { throw new DepartmentNotFoundException("Не вдалось оновити кафедру з id " + id + " причина: не знайдено в репозиторії"); }
        );
    }
}
