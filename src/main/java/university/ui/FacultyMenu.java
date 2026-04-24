package university.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import university.domain.*;
import university.exceptions.FacultyNotFoundException;
import university.exceptions.InvalidValue;
import university.exceptions.PersonNotFoundException;
import university.network.Client;
import university.repository.FacultyRepository;
import university.repository.TeacherRepository;
import university.service.RemoteFacultyService;
import university.service.RemoteTeacherService;
import university.service.TeacherService;
import university.storage.FacultyStorageManager;

import static university.service.SearchService.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FacultyMenu {
    private static final Logger logger = LoggerFactory.getLogger(FacultyMenu.class);

    private final Client client;
    private final Scanner scanner = new Scanner(System.in);
    protected final FacultyRepository facultyRepository = FacultyRepository.get(FacultyRepository.class);
    protected final RemoteFacultyService facultyService;
    protected final RemoteTeacherService teacherService;
    protected final FacultyStorageManager facultyStorageManager = new FacultyStorageManager();

    boolean resume;

    private final String opt0 = "0 - Повернутись назад";
    private final List<String> menuOptions = List.of("1 - Додати факультет", "2 - Змінити інформацію про факультет", "3 - Видалити факультет з бази даних", opt0);

    private final List<String> changeList = List.of("1 - Назва", "2 - Коротка назва", "3 - Декан", "4 - Контакти", opt0);
    private String exitOpt = null;

    public FacultyMenu(Client client) {
        this.client = client;
        facultyService = new RemoteFacultyService(client);
        teacherService = new RemoteTeacherService(client);
    }

    protected void facultyManaging() {
        logger.info("Відкрито меню управління факультетами");
        boolean status = true;
        while (status) {
            System.out.println("\n*-Управління факультетами-*");
            menuOptions.forEach(System.out::println);

            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        addFaculty();
                        break;
                    case 2:
                        changeFaculty();
                        break;
                    case 3:
                        deleteFaculty();
                        break;
                    case 0:
                        logger.info("Користувач вийшов з меню управління факультетами");
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

    protected Faculty facultyGenerator() {
        logger.debug("Запущено генератор нового факультету");
        Faculty faculty = new Faculty();

        System.out.println("Ідентифікатор факультету: " + faculty.getID());

        do {
            System.out.println("Введіть назву факультету");
            try {
                faculty.setName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація назви факультету не пройдена: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        do {
            System.out.println("Введіть коротку назву факультету");
            try {
                faculty.setShortName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація короткої назви факультету не пройдена: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        do {
            System.out.println("Введіть id декана факультету");
            try {
                String teacherId = scanner.nextLine();
                Teacher dean = teacherService.getTeacher(teacherId);
                faculty.setDeanId(teacherId);
                resume = true;
            } catch (InvalidValue | PersonNotFoundException e) {
                logger.warn("Помилка при призначенні декана: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
                System.out.println("0 - Пропустити");
                exitOpt = scanner.nextLine();
                if (exitOpt.equals("0")) break;
            }
        } while (!resume);

        do {
            System.out.println("Введіть контакти");
            try {
                faculty.setContacts(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація контактів не пройдена: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        logger.info("Об'єкт факультету згенеровано (ID: {}, Назва: {})", faculty.getID(), faculty.getName());
        return faculty;
    }

    protected void addFaculty() {
        Faculty faculty = facultyGenerator();
        facultyService.createFaculty(faculty);
        logger.info("Новий факультет '{}' успішно додано в базу даних", faculty.getName());
    }

    protected void deleteFaculty() {
        while (true) {
            System.out.println("Введіть ідентифікатор факультету, що треба видалити (нуль, щоб вийти)");
            String facultyId = scanner.nextLine();
            if (facultyId.equals("0")) return;
            facultyService.deleteFaculty(facultyId);
        }
    }

    protected void changeFaculty() {
        boolean found = false;
        String facultyId;
        Faculty faculty;
        while (!found) {
            try {
                System.out.println("Введіть ідентифікатор факультету, який треба замінити (введіть 0 щоб повернутись назад)");
                facultyId = scanner.nextLine();
                if (facultyId.equals("0")) return;
                faculty = facultyService.getFaculty(facultyId);
                found = true;
                boolean status = true;
                while (status) {
                    logger.info("Початок редагування факультету: {} (ID: {})", faculty.getName(), facultyId);
                    System.out.println("\n*-Оберіть, що змінити-*");
                    changeList.forEach(System.out::println);
                    String inputLine = scanner.nextLine();
                    try {
                        int input = Integer.parseInt(inputLine);
                        switch (input) {
                            case 1:
                                System.out.println("Введіть назву факультету");
                                do {
                                    try {
                                        faculty.setName(scanner.nextLine());
                                        facultyService.updateFaculty(faculty);
                                        logger.info("Поле 'назва' факультету оновлено");
                                        resume = true;
                                    } catch (InvalidValue | FacultyNotFoundException e) {
                                        logger.warn("Помилка оновлення назви: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 2:
                                System.out.println("Введіть коротку назву факультету");
                                do {
                                    try {
                                        faculty.setShortName(scanner.nextLine());
                                        facultyService.updateFaculty(faculty);
                                        logger.info("Поле 'коротка назва' факультету оновлено");
                                        resume = true;
                                    } catch (InvalidValue | FacultyNotFoundException e) {
                                        logger.warn("Помилка оновлення короткої назви: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 3:
                                System.out.println("Введіть id декана факультету");
                                do {
                                    try {
                                        String teacherId = scanner.nextLine();
                                        Teacher dean = teacherService.getTeacher(teacherId);
                                        faculty.setDeanId(teacherId);
                                        facultyService.updateFaculty(faculty);
                                        logger.info("Поле 'декан' факультету оновлено");
                                        resume = true;
                                    } catch (InvalidValue  | FacultyNotFoundException | PersonNotFoundException e) {
                                        logger.warn("Помилка оновлення декана: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                        System.out.println("0 - Вихід");
                                        exitOpt = scanner.nextLine();
                                        if (exitOpt.equals("0")) break;
                                    }
                                } while (!resume);
                                break;
                            case 4:
                                System.out.println("Введіть контакти факультету");
                                do {
                                    try {
                                        faculty.setContacts(scanner.nextLine());
                                        facultyService.updateFaculty(faculty);
                                        logger.info("Поле 'контакти' факультету оновлено");
                                        resume = true;
                                    } catch (InvalidValue | FacultyNotFoundException e) {
                                        logger.warn("Помилка оновлення контактів: {}", e.getMessage());
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
                        logger.error("Помилка формату числа при редагуванні факультету: {}", inputLine);
                        System.out.println("Введіть коректне значення");
                    }
                }
            }catch (FacultyNotFoundException e) {
                System.out.println("Факультет з таким ID не знайдено.");
                logger.warn("Факультет для зміни не знайдено");
            }
        }
    }
}