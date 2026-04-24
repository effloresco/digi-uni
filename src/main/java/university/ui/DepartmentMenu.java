package university.ui;

import university.domain.*;
import university.exceptions.*;
import university.network.Client;
import university.service.RemoteDepartmentService;
import university.service.RemoteFacultyService;
import university.service.RemoteTeacherService;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static university.service.Utils.*;

public class DepartmentMenu {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentMenu.class);
    private final Client client;
    protected final RemoteDepartmentService departmentService;
    protected final RemoteTeacherService teacherService;
    protected final RemoteFacultyService facultyService;
    private final Scanner scanner = new Scanner(System.in);

    boolean resume;
    String exitOpt = null;
    private final List<String> menuOptions = List.of(
            "[1] Додати кафедру",
            "[2] Змінити інформацію про кафедру",
            "[3] Видалити кафедру з бази даних",
            OPT0);
    private final List<String> changeList = List.of(
            "[1] Назва",
            "[2] Факультет",
            "[3] Голова кафедри",
            "[4] Локація(кабінет)",
            OPT0);

    public DepartmentMenu(Client client) {
        this.client = client;
        departmentService = new RemoteDepartmentService(client);
        teacherService = new RemoteTeacherService(client);
        facultyService = new RemoteFacultyService(client);
    }

    public void departmentManagement() {
        logger.info("Відкрито меню управління кафедрами");
        boolean status = true;
        while (status) {
            printMenu("Управління кафедрами", menuOptions);

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
                        printMessage(Mt.Error, "Некоректне значення");
                }
            } catch (NumberFormatException e) {
                logger.error("Помилка формату числа при виборі меню: {}", inputLine);
                printMessage(Mt.Error, "Некоректне значення");
            }
        }
    }

    protected Department departmentGenerator() {
        logger.debug("Запущено генератор нової кафедри");
        Department department = new Department();
        printMessage(Mt.Prompt, "Ідентифікатор кафедри: " + department.getID());
        printPrompt("Назва кафедри >");
        do {
            try {
                department.setName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація назви кафедри не пройдена: {}", e.getMessage());
                printMessage(Mt.Error, "Некоректне значення");
                resume = false;
            }

        } while (!resume);


        do {
            printPrompt("Ідентифікатор факультету >");
            try {
                String facultyId = scanner.nextLine();
                Faculty faculty = facultyService.getFaculty(facultyId);
                department.setFacultyId(facultyId);
                resume = true;

            } catch (InvalidValue | FacultyNotFoundException e) {
                logger.warn("Некоректний ID факультету: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
                printMessage(Mt.Error, "Некоректне значення", "[0] Пропустити");
                printPrompt(">");
                exitOpt = scanner.nextLine();
                if (exitOpt.equals("0")) break;
            }
        } while (!resume);


        do {
            printPrompt("Ідентифікатор викладача >");
            try {
                String teacherId = scanner.nextLine();
                Teacher dean = teacherService.getTeacher(teacherId);
                department.setHeadId(teacherId);
                resume = true;
            } catch (InvalidValue | PersonNotFoundException e) {
                logger.warn("Некоректний ID голови кафедри: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
                printMessage(Mt.Error, "Некоректне значення", "[0] Пропустити");
                printPrompt(">");
                exitOpt = scanner.nextLine();
                if (exitOpt.equals("0")) break;
            }

        } while (!resume);

        printPrompt("Кабінет >");
        department.setLocation(scanner.nextLine());

        logger.info("Об'єкт кафедри згенеровано (ID: {}, Назва: {})", department.getID(), department.getName());

        return department;
    }

    public void deleteDepartment() {
        boolean found = false;
        Department department = null;
        while (!found) {
            printHeader("Видалення кафедри");
            printMessage(Mt.Warning, "Введіть ідентифікатор кафедри, що треба видалити", "[0] Вихід", true);
            printPrompt(">");
            String departmentId = scanner.nextLine();
            if (departmentId.equals("0")) return;
            departmentService.deleteDepartment(departmentId);
        }

    }

    public void changeDepartment() {
        boolean found = false;
        String departmentId;
        Department department;
        while (!found) {
            try {
            printMessage(Mt.Prompt, "Введіть ідентифікатор кафедри, яку треба змінити", "[0] Вихід");
            printPrompt(">");
            departmentId = scanner.nextLine();
                if (departmentId.equals("0")) return;
                department = departmentService.getDepartment(departmentId);
                found = true;
                logger.info("Початок редагування кафедри: {} (ID: {})", department.getName(), departmentId);
                boolean status = true;
                while (status) {
                    printMessage(Mt.Prompt, "Оберіть, що змінити");
                    printMenu(changeList);
                    String inputLine = scanner.nextLine();
                    try {
                        int input = Integer.parseInt(inputLine);
                        switch (input) {
                            case 1:
                                printPrompt("Назва кафедри >");
                                do {
                                    try {
                                        department.setName(scanner.nextLine());
                                        departmentService.updateDepartment(department);
                                        resume = true;
                                        logger.info("Поле 'назва' успішно оновлено");
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        logger.warn("Помилка оновлення поля 'назва': {}", e.getMessage());
                                        printMessage(Mt.Error, "Некоректне значення");
                                        resume = false;
                                        System.out.println("0 - Вихід");
                                        exitOpt = scanner.nextLine();
                                        if (exitOpt.equals("0")) break;
                                    }
                                } while (!resume);
                                break;
                            case 2:
                                do {
                                    printPrompt("Факультет >");
                                    try {
                                        String facultyId = scanner.nextLine();
                                        Faculty faculty = facultyService.getFaculty(facultyId);
                                        department.setFacultyId(facultyId);
                                        departmentService.updateDepartment(department);
                                        resume = true;
                                        logger.info("Поле 'факультет' успішно оновлено");
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка оновлення поля 'факультет': {}", e.getMessage());
                                        resume = false;
                                        printMessage(Mt.Error, "Некоректне значення", "[0] Вихід", true);
                                        printPrompt(">");
                                        exitOpt = scanner.nextLine();
                                        if (exitOpt.equals("0")) break;
                                    }
                                } while (!resume);
                                break;
                            case 3:

                                do {
                                    printPrompt("Голова кафедри >");
                                    try {
                                        String teacherId = scanner.nextLine();
                                        Teacher dean = teacherService.getTeacher(teacherId);
                                        department.setHeadId(teacherId);
                                        departmentService.updateDepartment(department);
                                        resume = true;
                                        logger.info("Поле 'голова кафедри' успішно оновлено");
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка оновлення поля 'голова кафедри': {}", e.getMessage());
                                        printMessage(Mt.Error, "Некоректне значення", "[0] Вихід");
                                        resume = false;
                                        exitOpt = scanner.nextLine();
                                        if (exitOpt.equals("0")) break;
                                    }
                                } while (!resume);
                                break;
                            case 4:
                                printPrompt("Головний кабінет кафедри >");
                                do {
                                    try {
                                        department.setLocation(scanner.nextLine());
                                        departmentService.updateDepartment(department);
                                        resume = true;
                                        logger.info("Поле 'головний кабінет кафедри' успішно оновлено");
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка оновлення поля 'головний кабінет кафедри': {}", e.getMessage());
                                        printMessage(Mt.Error, "Некоректне значення");
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
                                printMessage(Mt.Error, "Некоректне значення");
                        }
                    } catch (NumberFormatException e) {
                        printMessage(Mt.Error, "Некоректне значення");
                        logger.error("Помилка введення при редагуванні: {}", inputLine);
                    }
                }
            } catch (DepartmentNotFoundException e) {
                printMessage(Mt.Error, "Кафедри з таким ID не знайдено");
                logger.warn("Кафедру для зміни не знайдено");
            }
        }


    }

    public void addDepartment() {
        Department newDep = departmentGenerator();
        departmentService.createDepartment(newDep);
        logger.info("Нову кафедру '{}' успішно додано в базу даних", newDep.getName());
        printMessage(Mt.Success, "Кафедру додано");
    }

}