package university.service;

import university.domain.Department;
import university.repository.Repository;

import java.util.Optional;

public class DepartmentService {
    private final Repository<Department, String> departmentRepository;

    public DepartmentService(Repository<Department, String> repository) {
        this.departmentRepository = repository;
    }

    public void createDepartment(Department department){
        Optional<Department> testCopy = departmentRepository.findById(department.getID());
        testCopy.orElseThrow(
                () -> new IllegalArgumentException("Не можна створити новий факультет з істуючим id")
        );
        departmentRepository.add(department);
    }
    public void deleteDepartment(Department department){
        Optional<Department> testCopy = departmentRepository.findById(department.getID());
        testCopy.orElseThrow(
                () -> new IllegalArgumentException("Не існує такого факультету")
        );
        departmentRepository.deleteByID(department.getID());
    }
    public void updateDepartment(String id, Department department){
        Optional<Department> testCopy = departmentRepository.findById(id);
        testCopy.ifPresentOrElse(
                exists -> {departmentRepository.deleteByID(id); departmentRepository.add(department);},
                () -> { throw new IllegalArgumentException("Не існує такого факультету"); }
        );
    }
}
