package university.ui;

import university.repository.DepartmentRepository;
import university.repository.TeacherRepository;
import university.service.DepartmentService;

public class DepartmentMenu {
    protected final DepartmentRepository departmentRepository = new DepartmentRepository();
    protected final DepartmentService departmentService = new DepartmentService(new DepartmentRepository());
    protected final TeacherRepository teacherRepository = TeacherRepository.get();


}