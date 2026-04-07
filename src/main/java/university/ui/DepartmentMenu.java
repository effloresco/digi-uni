package university.ui;

import university.domain.*;
import university.exceptions.*;
import university.repository.DepartmentRepository;
import university.service.DepartmentService;

import java.util.List;
import java.util.Optional;

import static university.service.SearchService.scanner;

public class DepartmentMenu {
    protected final DepartmentRepository departmentRepository = DepartmentRepository.get(DepartmentRepository.class);
    protected final DepartmentService departmentService = new DepartmentService(departmentRepository);
    boolean resume;
    String exitOpt = null;


    private final String opt0 = "0 - Повернутись назад";
    private final List<String> menuOptions = List.of("1 - Додати кафедру", "2 - Змінити інформацію про кафедру", "3 - Видалити кафедру з бази даних", opt0);

    private final List<String> changeList = List.of("1 - Назва", "2 - Факультет", "3 - Голова кафедри", "4 - Локація(кабінет)", opt0);
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
                department.setFaculty(scanner.nextLine());
                resume = true;

            } catch (InvalidValue e) {
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
                department.setHead(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
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
        boolean found = false;
        Department department = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор кафедри, що треба видалити (нуль, щоб вийти)");
            String departmentId = scanner.nextLine();
            if (departmentId.equals("0")) {
                return;
            }
            Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);

            if (optionalDepartment.isPresent()) {
                department = optionalDepartment.get();
                found = true;
                departmentService.deleteDepartment(department);
            } else
                System.out.println("Кафедри з таким ID не знайдено.");
        }

    }

    protected void changeDepartment() {
        boolean found = false;
        String departmentId;
        Department department;
        while (!found) {
            System.out.println("Введіть ідентифікатор кафедри, яку треба змінити");
            departmentId = scanner.nextLine();

            Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);

            if (optionalDepartment.isPresent()) {
                found = true;
                department = optionalDepartment.get();
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
                                        resume = true;
                                        departmentService.saveAllData();
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 2:
                                do {
                                    System.out.println("Введіть факультет кафедри");
                                    try {
                                        department.setFaculty(scanner.nextLine());
                                        resume = true;
                                        departmentService.saveAllData();
                                    } catch (InvalidValue e) {
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
                                        department.setHead(scanner.nextLine());
                                        resume = true;
                                        departmentService.saveAllData();
                                    } catch (InvalidValue e) {
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
                                        resume = true;
                                        departmentService.saveAllData();
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
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
            } else System.out.println("Кафедри з таким ID не знайдено.");
        }


    }

    protected void addDepartment() {
        departmentService.createDepartment(departmentGenerator());
    }

}