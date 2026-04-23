package university.ui;

import university.domain.*;
import university.exceptions.*;
import university.network.Client;
import university.repository.DepartmentRepository;
import university.service.RemoteDepartmentService;
import university.service.RemoteFacultyService;
import university.service.RemoteTeacherService;
import university.storage.DepartmentStorageManager;

import java.util.List;

import static university.service.SearchService.scanner;

public class DepartmentMenu {
    private final Client client;
    protected final RemoteDepartmentService departmentService;
    protected final RemoteTeacherService teacherService;
    protected final RemoteFacultyService facultyService;

    boolean resume;
    String exitOpt = null;

    private final String opt0 = "0 - Повернутись назад";
    private final List<String> menuOptions = List.of("1 - Додати кафедру", "2 - Змінити інформацію про кафедру", "3 - Видалити кафедру з бази даних", opt0);

    private final List<String> changeList = List.of("1 - Назва", "2 - Факультет", "3 - Голова кафедри", "4 - Локація(кабінет)", opt0);

    public DepartmentMenu(Client client) {
        this.client = client;
        departmentService = new RemoteDepartmentService(client);
        teacherService = new RemoteTeacherService(client);
        facultyService = new RemoteFacultyService(client);
    }

    protected void departmentManagement() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Управління кафедрами-*");
            menuOptions.forEach(System.out::println);

            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        addDepartment();
                        break;
                    case 2:
                        changeDepartment();
                        break;
                    case 3:
                        deleteDepartment();
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
        
        System.out.println("Ідентифікатор кафедри: " + department.getID());

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


        do {
            System.out.println("Введіть ідентифікатор факультету");
            try {
                String facultyId = scanner.nextLine();
                Faculty faculty = facultyService.getFaculty(facultyId);
                department.setFacultyId(facultyId);
                resume = true;

            } catch (InvalidValue | FacultyNotFoundException e) {
                System.out.println(e.getMessage());
                resume = false;
                System.out.println("0 - Пропустити");
                exitOpt = scanner.nextLine();
                if (exitOpt.equals("0")) break;
            }
        } while (!resume);


        do {
            System.out.println("Введіть ідентифікатор викладача");
            try {
                String teacherId = scanner.nextLine();
                Teacher dean = teacherService.getTeacher(teacherId);
                department.setHeadId(teacherId);
                resume = true;
            } catch (InvalidValue | PersonNotFoundException e) {
                System.out.println(e.getMessage());
                resume = false;
                System.out.println("0 - Пропустити");
                exitOpt = scanner.nextLine();
                if (exitOpt.equals("0")) break;
            }

        } while (!resume);

        System.out.println("Введіть кабінет");
        department.setLocation(scanner.nextLine());

        return department;
    }


    protected void deleteDepartment() {
        while (true) {
            System.out.println("Введіть ідентифікатор кафедри, що треба видалити (нуль, щоб вийти)");
            String departmentId = scanner.nextLine();
            if (departmentId.equals("0")) return;
            departmentService.deleteDepartment(departmentId);
        }

    }

    protected void changeDepartment() {
        boolean found = false;
        String departmentId;
        Department department;
        while (!found) {
            try {
                System.out.println("Введіть ідентифікатор кафедри, яку треба змінити");
                departmentId = scanner.nextLine();
                department = departmentService.getDepartment(departmentId);
                found = true;
                boolean status = true;
                while (status) {
                    System.out.println("\n*-Оберіть, що змінити-*");
                    changeList.forEach(System.out::println);
                    String inputLine = scanner.nextLine();
                    try {
                        int input = Integer.parseInt(inputLine);
                        switch (input) {
                            case 1:
                                System.out.println("Введіть назву кафедри");
                                do {
                                    try {
                                        department.setName(scanner.nextLine());
                                        departmentService.updateDepartment(department);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                        System.out.println("0 - Вихід");
                                        exitOpt = scanner.nextLine();
                                        if (exitOpt.equals("0")) break;
                                    }
                                } while (!resume);
                                break;
                            case 2:
                                do {
                                    System.out.println("Введіть факультет кафедри");
                                    try {
                                        String facultyId = scanner.nextLine();
                                        Faculty faculty = facultyService.getFaculty(facultyId);
                                        department.setFacultyId(facultyId);
                                        departmentService.updateDepartment(department);
                                        resume = true;
                                    } catch (InvalidValue | FacultyNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                        System.out.println("0 - Вихід");
                                        exitOpt = scanner.nextLine();
                                        if (exitOpt.equals("0")) break;
                                    }
                                } while (!resume);
                                break;
                            case 3:

                                do {
                                    System.out.println("Введіть голову кафедри");
                                    try {
                                        String teacherId = scanner.nextLine();
                                        Teacher dean = teacherService.getTeacher(teacherId);
                                        department.setHeadId(teacherId);
                                        departmentService.updateDepartment(department);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                        System.out.println("0 - Вихід");
                                        exitOpt = scanner.nextLine();
                                        if (exitOpt.equals("0")) break;
                                    }
                                } while (!resume);
                                break;
                            case 4:
                                System.out.println("Введіть головний кабінет кафедри");
                                do {
                                    try {
                                        department.setLocation(scanner.nextLine());
                                        departmentService.updateDepartment(department);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                        System.out.println("0 - Вихід");
                                        exitOpt = scanner.nextLine();
                                        if (exitOpt.equals("0")) break;
                                    }
                                } while (!resume);
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
            }catch (DepartmentNotFoundException e) {
                System.out.println("Кафедру з таким ID не знайдено.");
            }
        }


    }

    protected void addDepartment() {
        departmentService.createDepartment(departmentGenerator());
    }

}