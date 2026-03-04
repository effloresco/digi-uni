package university.repository;

import university.domain.Department;

public class DepartmentRepository extends Repository<Department, String>{
    private static DepartmentRepository departmentRepository;

    public static DepartmentRepository get(){
        if (departmentRepository == null){
            departmentRepository = new DepartmentRepository();
        }
        return departmentRepository;
    }
}
