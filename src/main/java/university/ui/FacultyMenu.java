package university.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import university.domain.*;
import university.exceptions.InvalidValue;
import university.repository.FacultyRepository;
import university.repository.TeacherRepository;
import university.service.FacultyService;

import static university.service.SearchService.*;

import java.util.List;
import java.util.Optional;

public class FacultyMenu {
    private static final Logger logger = LoggerFactory.getLogger(FacultyMenu.class);

    protected final FacultyRepository facultyRepository = FacultyRepository.get(FacultyRepository.class);
    protected final FacultyService facultyService = new FacultyService(facultyRepository);
    protected final TeacherRepository teacherRepository = TeacherRepository.get(TeacherRepository.class);

    boolean resume;

    private final String opt0 = "0 - Повернутись назад";
    private final List<String> menuOptions = List.of("1 - Додати факультет", "2 - Змінити інформацію про факультет", "3 - Видалити факультет з бази даних", opt0);

    private final List<String> changeList = List.of("1 - Назва", "2 - Коротка назва", "3 - Декан", "4 - Контакти", opt0);
    private String exitOpt = null;

    public void facultyManaging() {
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
        
        System.out.println("Введіть назву факультету");
        do {
            try {
                faculty.setName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація назви факультету не пройдена: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть коротку назву факультету");
        do {
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
            try {
                faculty.setDean(receiveDean());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Помилка при призначенні декана: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
                System.out.println("0 - Пропустити");
                exitOpt = scanner.nextLine();
                if (exitOpt.equals("0")) break;
            }
        } while (!resume);

        System.out.println("Введіть контакти");
        do {
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

    public Teacher receiveDean() {
        Teacher dean = null;
        boolean found = false;
        while (!found) {
            System.out.println("Введіть ідентифікатор декана(залиште поле пустим, щоб додати пізніше)");
            String teacherId = scanner.nextLine();

            if (!teacherId.isEmpty()) {
                Optional<Teacher> optionalPerson = teacherRepository.findById(teacherId);

                if (optionalPerson.isPresent()) {
                    dean = optionalPerson.get();
                    found = true;
                } else {
                    logger.warn("Спроба пошуку декана: особу з ID {} не знайдено", teacherId);
                    System.out.println("Особу з таким ID не знайдено.");
                }
            } else found = true;
        }
        return dean;
    }

    public void addFaculty() {
        Faculty faculty = facultyGenerator();
        facultyService.createFaculty(faculty);
        logger.info("Новий факультет '{}' успішно додано в базу даних", faculty.getName());
    }

    public void deleteFaculty() {
        boolean found = false;
        boolean exit = false;
        Faculty faculty = null;
        while (!found && !exit) {
            System.out.println("Введіть ідентифікатор факультету, що треба видалити (нуль, щоб вийти)");
            String facultyId = scanner.nextLine();

            if (facultyId.equals("0")) exit = true;
            else {
                Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);

                if (optionalFaculty.isPresent()) {
                    faculty = optionalFaculty.get();
                    found = true;
                    facultyService.deleteFaculty(faculty);
                    logger.info("Факультет з ID {} та назвою '{}' успішно видалено", facultyId, faculty.getName());
                } else {
                    logger.warn("Спроба видалення: факультет з ID {} не знайдено", facultyId);
                    System.out.println("Факультет з таким ID не знайдено.");
                }
            }
        }
    }

    public void changeFaculty() {
        boolean found = false;
        boolean exit = false;
        String facultyId;
        Faculty faculty;
        while (!found && !exit) {
            System.out.println("Введіть ідентифікатор факультету, що треба замінити (введіть 0 щоб повернутись назад)");
            facultyId = scanner.nextLine();

            if (facultyId.equals("0")) exit = true;
            else {
                while (!found) {
                    System.out.println("Введіть ідентифікатор факультету, що треба замінити");
                    facultyId = scanner.nextLine();

                    Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);

                    if (optionalFaculty.isPresent()) {
                        found = true;
                        faculty = optionalFaculty.get();
                        logger.info("Початок редагування факультету: {} (ID: {})", faculty.getName(), facultyId);
                        boolean status = true;
                        while (status) {
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
                                                facultyService.saveAllData();
                                                logger.info("Поле 'назва' факультету оновлено");
                                                resume = true;
                                            } catch (InvalidValue e) {
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
                                                facultyService.saveAllData();
                                                logger.info("Поле 'коротка назва' факультету оновлено");
                                                resume = true;
                                            } catch (InvalidValue e) {
                                                logger.warn("Помилка оновлення короткої назви: {}", e.getMessage());
                                                System.out.println(e.getMessage());
                                                resume = false;
                                            }
                                        } while (!resume);
                                        break;
                                    case 3:
                                        System.out.println("Введіть ID декана факультету");
                                        do {
                                            try {
                                                faculty.setDean(receiveDean());
                                                facultyService.saveAllData();
                                                logger.info("Поле 'декан' факультету оновлено");
                                                resume = true;
                                            } catch (InvalidValue e) {
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
                                                facultyService.saveAllData();
                                                logger.info("Поле 'контакти' факультету оновлено");
                                                resume = true;
                                            } catch (InvalidValue e) {
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
                    } else {
                        logger.warn("Факультет для зміни з ID {} не знайдено", facultyId);
                        System.out.println("Факультет з таким ID не знайдено.");
                    }
                }
            }
        }
    }
}