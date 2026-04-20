package university.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import university.domain.*;
import university.repository.TeacherRepository;
import university.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import university.exceptions.*;

import static university.service.SearchService.*;

public class TeacherMenu {

    private static final Logger logger = LoggerFactory.getLogger(TeacherMenu.class);

    protected final TeacherRepository teacherRepository = TeacherRepository.get(TeacherRepository.class);
    protected final TeacherService teacherService = new TeacherService(teacherRepository);
    
    boolean resume;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String exitOpt = null;
    
    private final String opt0 = "0 - Вихід";
    private List<String> changeList = List.of(
            "1 - Ім'я",
            "2 - Прізвище",
            "3 - По батькові",
            "4 - Дату народження",
            "5 - Електронну пошту",
            "6 - Номер телефону",
            "7 - Посаду",
            "8 - Науковий ступінь",
            "9 - Вчене звання",
            "10 - Дату влаштування на роботу",
            "11 - Ставку",
            "12 - Факультет", "13 - Кафедра",
            opt0);

    private List<String> menuOptions = List.of(
            "1 - Додати викладача",
            "2 - Змінити інформацію про викладача",
            "3 - Видалити викладача з бази даних",
            opt0);

    protected void teacherManagement() {
        logger.info("Відкрито меню управління викладачами");
        boolean status = true;
        while (status) {
            System.out.println("\n*-Управління викладачами-*");
            menuOptions.forEach(System.out::println);

            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        addTeacher();
                        break;
                    case 2:
                        changeTeacher();
                        break;
                    case 3:
                        deleteTeacher();
                        break;
                    case 0:
                        logger.info("Користувач вийшов з меню управління викладачами");
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

    protected Teacher teacherGenerator() {
        logger.debug("Запущено генератор нового викладача");
        Teacher teacher = new Teacher();

        System.out.println("Ідентифікатор викладача: " + teacher.getID());

        System.out.println("Введіть ім'я");
        do {
            try {
                teacher.setFirstName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація імені не пройдена: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть прізвище");
        do {
            try {
                teacher.setLastName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація прізвища не пройдена: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть по батькові");
        do {
            try {
                teacher.setMiddleName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація по батькові не пройдена: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть дату народження викладача");
        do {
            try {
                teacher.setBirthDate(LocalDate.parse(SearchService.scanner.nextLine(), formatter));
                resume = true;
            } catch (DateTimeParseException e) {
                logger.warn("Помилка парсингу дати народження");
                System.out.println("Введіть коректну дату");
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть електронну пошту");
        do {
            try {
                teacher.setEmail(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректний email викладача: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть номер телефону");
        do {
            try {
                teacher.setPhone(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректний номер телефону викладача: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть посаду");
        do {
            try {
                teacher.setPosition(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректна посада: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть науковий ступінь");
        do {
            try {
                teacher.setDegree(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректний науковий ступінь: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть вчене звання");
        do {
            try {
                teacher.setTitle(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректне вчене звання: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть ID факультету");
        do {
            try {
                teacher.setFaculty(scanner.nextLine());
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

        System.out.println("Введіть ID кафедри");
        do {
            try {
                teacher.setDepartment(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректний ID кафедри: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
                System.out.println("0 - Пропустити");
                exitOpt = scanner.nextLine();
                if (exitOpt.equals("0")) break;
            }
        } while (!resume);

        System.out.println("Введіть дату влаштування на роботу");
        do {
            try {
                teacher.setHireDate(LocalDate.parse(scanner.nextLine(), formatter));
                resume = true;
            } catch (DateTimeParseException e) {
                logger.warn("Помилка парсингу дати влаштування");
                System.out.println("Введіть коректну дату");
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть ставку");
        do {
            try {
                double inputRate = Double.parseDouble(scanner.nextLine());
                teacher.setRate(inputRate);
                resume = true;
            } catch (NumberFormatException e) {
                logger.error("Помилка введення ставки (не число)");
                System.out.println("Помилка: введіть коректне число");
                resume = false;
            } catch (InvalidValue e) {
                logger.warn("Некоректна ставка: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);
        System.out.println("Викладача " + teacher.getFullName() + " створено");
        logger.info("Об'єкт викладача згенеровано: {} (ID: {})", teacher.getFullName(), teacher.getID());
        return teacher;
    }

    protected void deleteTeacher() {
        boolean found = false;
        while (!found) {
            System.out.println("Введіть ідентифікатор вчителя, якого треба видалити (нуль, щоб вийти)");
            String teacherId = scanner.nextLine();
            if (teacherId.equals("0")) {
                return;
            }

            Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
            if (optionalTeacher.isPresent()) {
                Teacher teacher = optionalTeacher.get();
                teacherService.deleteTeacher(teacher);
                logger.info("Викладача {} (ID: {}) успішно видалено", teacher.getFullName(), teacherId);
                found = true;
            } else {
                logger.warn("Спроба видалення: викладача з ID {} не знайдено", teacherId);
                System.out.println("Викладача з таким ID не знайдено.");
                break;
            }
        }
    }

    protected void changeTeacher() {
        boolean found = false;
        while (!found) {
            System.out.println("Введіть ідентифікатор викладача, що треба замінити (0 для виходу)");
            String teachertId = scanner.nextLine();
            if ("0".equals(teachertId)) return;

            Optional<Teacher> optionalTeacher = teacherRepository.findById(teachertId);

            if (optionalTeacher.isPresent()) {
                found = true;
                Teacher teacher = optionalTeacher.get();
                logger.info("Початок редагування викладача: {} (ID: {})", teacher.getFullName(), teachertId);
                boolean status = true;

                while (status) {
                    System.out.println("\n*-Оберіть, що змінити-*");
                    changeList.forEach(System.out::println);

                    String inputLine = scanner.nextLine();
                    try {
                        int input = Integer.parseInt(inputLine);
                        switch (input) {
                            case 1:
                                System.out.println("Введіть ім'я");
                                do {
                                    try {
                                        teacher.setFirstName(scanner.nextLine());
                                        teacherService.saveAllData();
                                        logger.info("Поле 'ім'я' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка зміни імені: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 2:
                                System.out.println("Введіть прізвище");
                                do {
                                    try {
                                        teacher.setLastName(scanner.nextLine());
                                        teacherService.saveAllData();
                                        logger.info("Поле 'прізвище' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка зміни прізвища: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 3:
                                System.out.println("Введіть по батькові");
                                do {
                                    try {
                                        teacher.setMiddleName(scanner.nextLine());
                                        teacherService.saveAllData();
                                        logger.info("Поле 'по батькові' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка зміни по батькові: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 4:
                                do {
                                    try {
                                        teacher.setBirthDate(LocalDate.parse(SearchService.scanner.nextLine(), formatter));
                                        teacherService.saveAllData();
                                        logger.info("Поле 'дата народження' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (DateTimeParseException e) {
                                        logger.warn("Помилка дати");
                                        System.out.println("Введіть коректну дату");
                                        resume = false;
                                    }

                                } while (!resume);
                                break;
                            case 5:
                                System.out.println("Введіть електронну пошту");
                                do {
                                    try {
                                        teacher.setEmail(scanner.nextLine());
                                        teacherService.saveAllData();
                                        logger.info("Поле 'електронну пошту' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка зміни електронної пошти: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 6:
                                System.out.println("Введіть номер телефону");
                                do {
                                    try {
                                        teacher.setPhone(scanner.nextLine());
                                        teacherService.saveAllData();
                                        logger.info("Поле 'номер телефону' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка зміни номеру телефону: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 7:
                                System.out.println("Введіть посаду");
                                do {
                                    try {
                                        teacher.setPosition(scanner.nextLine());
                                        teacherService.saveAllData();
                                        logger.info("Поле 'посаду' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        logger.warn("Помилка зміни посади: {}", e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 8:
                                System.out.println("Введіть  науковий ступінь");
                                do {
                                    try {
                                        teacher.setDegree(scanner.nextLine());
                                        teacherService.saveAllData();
                                        logger.info("Поле 'науковий ступінь' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка зміни наукового ступеню: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 9:
                                System.out.println("Введіть вчене звання");
                                do {
                                    try {
                                        teacher.setTitle(scanner.nextLine());
                                        teacherService.saveAllData();
                                        logger.info("Поле 'вчене звання' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка зміни вченого звання: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 10:
                                System.out.println("Введіть дату влаштування на роботу");
                                do {
                                    try {
                                        teacher.setHireDate(LocalDate.parse(SearchService.scanner.nextLine(), formatter));
                                        teacherService.saveAllData();
                                        logger.info("Поле 'дата влаштування на роботу' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (DateTimeParseException e) {
                                        logger.warn("Помилка дати");
                                        System.out.println("Введіть коректну дату");
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 11:
                                System.out.println("Введіть ставку");
                                do {
                                    try {
                                        teacher.setRate(Double.parseDouble(SearchService.scanner.nextLine()));
                                        teacherService.saveAllData();
                                        logger.info("Поле 'ставка' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка зміни ставки: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;

                            case 12:
                                System.out.println("Введіть ID нового факультету");
                                do {
                                    try {
                                        teacher.setFaculty(scanner.nextLine());
                                        teacherService.saveAllData();
                                        logger.info("Поле 'ID факультету' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка зміни ID факультету: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;

                            case 13:
                                System.out.println("Введіть ID нової кафедри");
                                do {
                                    try {
                                        teacher.setDepartment(scanner.nextLine());
                                        teacherService.saveAllData();
                                        logger.info("Поле 'ID кафедри' викладача з ID {} оновлено", teachertId);
                                        resume = true;
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
                        logger.error("Помилка введення при редагуванні викладача: {}", inputLine);
                        System.out.println("Введіть коректне значення");
                    }
                }
            } else {
                logger.warn("Викладача з ID {} не знайдено для редагування", teachertId);
                System.out.println("Викладача з таким ID не знайдено.");
                break;
            }
        }
    }

    protected void addTeacher() {
        Teacher teacher = teacherGenerator();
        teacherService.createTeacher(teacher);
        logger.info("Викладача '{}' успішно збережено в базу", teacher.getFullName());
    }
}
