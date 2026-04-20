package university.ui;

import university.domain.*;
import university.exceptions.*;
import university.repository.DepartmentRepository;
import university.service.DepartmentService;

import java.util.List;
import java.util.Optional;

import static university.service.SearchService.scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepartmentMenu {
    protected final DepartmentRepository departmentRepository = DepartmentRepository.get(DepartmentRepository.class);
    protected final DepartmentService departmentService = new DepartmentService(departmentRepository);
    private static final Logger logger = LoggerFactory.getLogger(DepartmentMenu.class);
    boolean resume;
    String exitOpt = null;


    private final String opt0 = "0 - Повернутись назад";
    private final List<String> menuOptions = List.of("1 - Додати кафедру", "2 - Змінити інформацію про кафедру", "3 - Видалити кафедру з бази даних", opt0);

    private final List<String> changeList = List.of("1 - Назва", "2 - Факультет", "3 - Голова кафедри", "4 - Локація(кабінет)", opt0);
    public void departmentManagement() {
        logger.info("Відкрито меню управління кафедрами");
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
                        logger.info("Користувач вийшов з меню управління кафедрами");
                        status = false;
                        break;
                    default:
                        logger.warn("Користувач ввів неіснуючий пункт меню: {}", input);
                        System.out.println("Введіть коректне значення");
                }
            } catch (NumberFormatException e) {
                logger.error("Помилка формату числа при виборі меню: {}", inputLine);
                System.out.println("Введіть коректне значення");
            }
        }
    }

    protected Department departmentGenerator() {
        logger.debug("Запущено генератор нової кафедри");
        Department department = new Department();
        
        System.out.println("Ідентифікатор кафедри: " + department.getID());

        System.out.println("Введіть назву кафедри");
        do {
            try {
                department.setName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація назви кафедри не пройдена: {}", e.getMessage());
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
                logger.warn("Некоректний ID факультету: {}", e.getMessage());
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
                logger.warn("Некоректний ID голови кафедри: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
                System.out.println("0 - Пропустити");
                exitOpt = scanner.nextLine();
                if (exitOpt.equals("0")) break;
            }

        } while (!resume);

        System.out.println("Введіть кабінет");
        department.setLocation(scanner.nextLine());

        logger.info("Об'єкт кафедри згенеровано (ID: {}, Назва: {})", department.getID(), department.getName());

        return department;
    }


    public void deleteDepartment() {
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
                logger.info("Кафедру з ID {} та назвою '{}' успішно видалено", departmentId, department.getName());
            } else
                System.out.println("Кафедри з таким ID не знайдено.");
                logger.warn("Спроба видалення: кафедру з ID {} не знайдено", departmentId);
        }

    }

    public void changeDepartment() {
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
                logger.info("Початок редагування кафедри: {} (ID: {})", department.getName(), departmentId);
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
                                        logger.info("Поле 'назва' успішно оновлено");
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка оновлення поля 'назва': {}", e.getMessage());
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
                                        logger.info("Поле 'факультет' успішно оновлено");
                                        departmentService.saveAllData();
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка оновлення поля 'факультет': {}", e.getMessage());
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
                                        logger.info("Поле 'голова кафедри' успішно оновлено");
                                        departmentService.saveAllData();
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка оновлення поля 'голова кафедри': {}", e.getMessage());
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
                                        logger.info("Поле 'головний кабінет кафедри' успішно оновлено");
                                        departmentService.saveAllData();
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка оновлення поля 'головний кабінет кафедри': {}", e.getMessage());
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
                        logger.error("Помилка введення при редагуванні: {}", inputLine);
                    }
                }
            } else System.out.println("Кафедри з таким ID не знайдено.");
            logger.warn("Кафедру для зміни з ID {} не знайдено", departmentId);
        }


    }

    public void addDepartment() {
        Department newDep = departmentGenerator();
        departmentService.createDepartment(newDep);
        logger.info("Нову кафедру '{}' успішно додано в базу даних", newDep.getName());
    }

}