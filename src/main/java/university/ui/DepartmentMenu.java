package university.ui;

import university.domain.*;
import university.exceptions.*;
import university.repository.DepartmentRepository;
import university.repository.TeacherRepository;
import university.service.DepartmentService;
import java.util.Optional;
import java.util.Random;
import static university.service.SearchService.scanner;

public class DepartmentMenu {
    protected final DepartmentRepository departmentRepository = new DepartmentRepository();
    protected final DepartmentService departmentService = new DepartmentService(departmentRepository);
    protected final TeacherRepository teacherRepository = TeacherRepository.get();
    protected Department department = new Department();
    boolean resume;
    Teacher dean = null;
    boolean found = false;
    int min = 100000000;
    int max = 999999999;
    Random random = new Random();
    Integer randomNumber = random.nextInt(max - min + 1) + min;

    protected void departmentManagement() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Управління кафедрами-*");
            System.out.println("1 - додати кафедру");
            System.out.println("2 - змінити інформацію про кафедру");
            System.out.println("3 - видалити кафедру з бази даних");
            System.out.println("0 - повернутись назад");
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        addDepartment();
                        break;
                    case 2:
                        deleteDepartment();
                        break;
                    case 3:
                        changeDepartment();
                        break;
                    case 0:
                        status = false;
                        break;
                    default:
                        System.out.println("Введіть коректне значення");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
    }

    protected Department departmentGenerator() {
        Department department = new Department();
        do {
            try {
                department.setId(String.valueOf(random.nextInt(max - min + 1) + min));
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);
        System.out.println("Ідентифікатор кафедри: "+ department.getID());


        System.out.println("Введіть назву кафедри");
        do {
            try {
                department.setName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }

        } while (!resume);

        System.out.println("Введіть ідентифікатор факультету");
        do {
            try {
                department.setFaculty(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);


        System.out.println("Введіть ідентифікатор викладача");
        do {
            try {
                department.setHead(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }

        } while (!resume);

        System.out.println("Введіть кабінет");
        String contacts = scanner.nextLine();

        return new Department();
    }


    protected void deleteDepartment() {
        boolean found = false;
        Department department = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор кафедри, що треба видалити");
            String departmentId = scanner.nextLine();

            Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);

            if (optionalDepartment.isPresent()) {
                department = optionalDepartment.get();
                found = true;
            } else
                System.out.println("Кафедри з таким ID не знайдено.");
        }
        departmentService.deleteDepartment(department);
    }

    protected void changeDepartment() {
        boolean found = false;
        String departmentId = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор кафедри, що треба замінити");
            departmentId = scanner.nextLine();

            Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);

            if (optionalDepartment.isPresent()) {
                found = true;
            } else
                System.out.println("Кафедри з таким ID не знайдено.");
        }
        departmentService.updateDepartment(departmentId, departmentGenerator());
    }

    protected void addDepartment() {
        departmentService.createDepartment(departmentGenerator());
    }

}