package university.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import university.domain.*;
import university.network.Client;
import university.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import university.exceptions.*;

public class TeacherMenu {
    private static final Logger logger = LoggerFactory.getLogger(TeacherMenu.class);

    private final Client client;
    private final Scanner scanner = new Scanner(System.in);
    protected final RemoteTeacherService teacherService;
    protected final RemoteDepartmentService departmentService;
    protected final RemoteFacultyService facultyService;
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

    public TeacherMenu(Client client) {
        this.client = client;
        teacherService = new RemoteTeacherService(client);
        facultyService = new RemoteFacultyService(client);
        departmentService = new RemoteDepartmentService(client);
    }

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
                teacher.setBirthDate(LocalDate.parse(scanner.nextLine(), formatter));
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
                String facultyId = scanner.nextLine();
                Faculty faculty = facultyService.getFaculty(facultyId);
                teacher.setFacultyId(facultyId);
                resume = true;
            } catch (InvalidValue | FacultyNotFoundException e) {
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
                String departmentId = scanner.nextLine();
                Department department = departmentService.getDepartment(departmentId);
                teacher.setDepartmentId(departmentId);
                resume = true;
            } catch (InvalidValue | DepartmentNotFoundException e) {
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
        while (true) {
            System.out.println("Введіть ідентифікатор вчителя, якого треба видалити (нуль, щоб вийти)");
            String teacherId = scanner.nextLine();
            if (teacherId.equals("0")) return;
            teacherService.deleteTeacher(teacherId);
        }
    }

    protected void changeTeacher() {
        boolean found = false;
        String teachertId;
        Teacher teacher;
        while (!found) {
            try{
                System.out.println("Введіть ідентифікатор викладача, якого треба змінити");
                teachertId = scanner.nextLine();
                if (teachertId.equals("0")) return;
                found = true;
                teacher = teacherService.getTeacher(teachertId);
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
                                        teacherService.updateTeacher(teacher);
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
                                        teacherService.updateTeacher(teacher);
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
                                        teacherService.updateTeacher(teacher);
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
                                        teacher.setBirthDate(LocalDate.parse(scanner.nextLine(), formatter));
                                        teacherService.updateTeacher(teacher);
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
                                        teacherService.updateTeacher(teacher);
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
                                        teacherService.updateTeacher(teacher);
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
                                        teacherService.updateTeacher(teacher);
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
                                        teacherService.updateTeacher(teacher);
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
                                        teacherService.updateTeacher(teacher);
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
                                        teacher.setHireDate(LocalDate.parse(scanner.nextLine(), formatter));
                                        teacherService.updateTeacher(teacher);
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
                                        teacher.setRate(Double.parseDouble(scanner.nextLine()));
                                        teacherService.updateTeacher(teacher);
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
                                        String facultyId = scanner.nextLine();
                                        Faculty faculty = facultyService.getFaculty(facultyId);
                                        teacher.setFacultyId(facultyId);
                                        teacherService.updateTeacher(teacher);
                                        logger.info("Поле 'ID факультету' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (InvalidValue | FacultyNotFoundException e) {
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
                                        String departmentId = scanner.nextLine();
                                        Department department = departmentService.getDepartment(departmentId);
                                        teacher.setDepartmentId(departmentId);
                                        teacherService.updateTeacher(teacher);
                                        logger.info("Поле 'ID кафедри' викладача з ID {} оновлено", teachertId);
                                        resume = true;
                                    } catch (InvalidValue | DepartmentNotFoundException e) {
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
            }catch (PersonNotFoundException e) {
                logger.warn("Викладача з не знайдено для редагування");
                System.out.println("Викладача з таким ID не знайдено.");
            }
        }
    }

    protected void addTeacher() {
        Teacher teacher = teacherGenerator();
        teacherService.createTeacher(teacher);
        logger.info("Викладача '{}' успішно збережено в базу", teacher.getFullName());
    }
}
