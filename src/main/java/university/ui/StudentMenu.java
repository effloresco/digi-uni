package university.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import university.domain.Student;
import university.repository.StudentRepository;
import university.service.SearchService;
import university.service.StudentService;
import university.exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static university.domain.Student.StudentStatus.*;
import static university.domain.Student.StudyForm.*;
import static university.service.SearchService.*;

public class StudentMenu {
    private static final Logger logger = LoggerFactory.getLogger(StudentMenu.class);

    protected final StudentRepository studentRepository = StudentRepository.get(StudentRepository.class);
    protected final StudentService studentService = new StudentService(studentRepository);
    boolean resume;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String exitOpt = null;

    private final String opt0 = "0 - Вихід";
    private List<String> changeList = List.of("1 - Ім'я", "2 - Прізвище", "3 - По батькові", "4 - Дату народження", "5 - Електронну пошту", "6 - Номер телефону", "6 - Ідентифікатор студента", "8 - Курс", "9 - Групу", "10 - Рік вступу", "11 - форму навчання", "12 - Статус студента", "13 - Факультет", "14 - Спеціальність", "15 - Кафедру", opt0);

    private final List<String> studentStatuses = List.of("1 - Вчиться", "2 - У академічній відпустці", "3 - Відрахований", opt0);

    private final List<String> studyForms = List.of("1 - Бюджет", "2 - Контракт", opt0);

    private final List<String> menuOptions = List.of("1 - Додати студента", "2 - Змінити інформацію про студента", "3 - Видалити студента з бази даних", opt0);

    protected void studentManagement() {
        logger.info("Відкрито меню управління студентами");
        boolean status = true;
        while (status) {
            System.out.println("\n*-Управління студентами-*");
            menuOptions.forEach(System.out::println);
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        changeStudent();
                        break;
                    case 3:
                        deleteStudent();
                        break;
                    case 0:
                        logger.info("Користувач вийшов з меню управління студентами");
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

    protected Student studentGenerator() {
        logger.debug("Запущено генератор нового студента");
        boolean status;
        Student student = new Student();
        System.out.println("Ідентифікатор студента: " + student.getID());

        System.out.println("Введіть ім'я");
        do {
            try {
                student.setFirstName(scanner.nextLine());
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
                student.setLastName(scanner.nextLine());
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
                student.setMiddleName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація по батькові не пройдена: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть дату народження студента");
        do {
            try {
                student.setBirthDate(LocalDate.parse(SearchService.scanner.nextLine(), formatter));
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
                student.setEmail(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректний email: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть номер телефону");
        do {
            try {
                student.setPhone(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректний номер телефону: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть ідентифікатор студента (номер залікової книжки)");
        do {
            try {
                student.setStudentId(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректний ID студента: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть курс");
        do {
            try {
                student.setCourse(Integer.parseInt(SearchService.scanner.nextLine()));
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Помилка введення курсу");
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть групу");
        student.setGroup(scanner.nextLine());

        System.out.println("Введіть ID факультету");
        do {
            try {
                student.setFacultyId(scanner.nextLine());
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

        System.out.println("Введіть назву спеціальності");
        do {
            try {
                student.setSpecialty(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректна назва спеціальності: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть ID кафедри");
        do {
            try {
                student.setDepartmentId(scanner.nextLine());
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

        System.out.println("Введіть рік вступу");
        do {
            try {
                student.setEnrollmentYear(Integer.parseInt(SearchService.scanner.nextLine()));
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Помилка введення року вступу");
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        do {
            status = true;
            System.out.println("\n*-Оберіть форму навчання: -*");
            studyForms.forEach(System.out::println);
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        student.setStudyForm(BUDGET);
                        status = false;
                        break;
                    case 2:
                        student.setStudyForm(CONTRACT);
                        status = false;
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
        } while (status);

        do {
            status = true;
            System.out.println("\n*-Оберіть статус студента: -*");
            studentStatuses.forEach(System.out::println);
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        student.setStudentStatus(STUDYING);
                        status = false;
                        break;
                    case 2:
                        student.setStudentStatus(ACADEMIC_LEAVE);
                        status = false;
                        break;
                    case 3:
                        student.setStudentStatus(EXPELLED);
                        status = false;
                    case 0:
                        status = false;
                        break;
                    default:
                        System.out.println("Введіть коректне значення");
                }
            } catch (NumberFormatException e) {
                logger.error("Помилка вибору статусу студента");
                System.out.println("Введіть коректне значення");
            }
        } while (status);
        System.out.println("Студента " + student.getFullName() + " створено");
        logger.info("Об'єкт студента згенеровано: {} (ID: {})", student.getFullName(), student.getID());
        return student;
    }

    protected void deleteStudent() {
        boolean found = false;
        while (!found) {
            System.out.println("Введіть ідентифікатор студента, якого треба видалити (нуль, щоб вийти)");
            String studentId = scanner.nextLine();
            if (studentId.equals("0")) return;

            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();
                studentService.deleteStudent(student);
                logger.info("Студента {} (ID: {}) успішно видалено", student.getFullName(), studentId);
                found = true;
            } else {
                logger.warn("Спроба видалення: студента з ID {} не знайдено", studentId);
                System.out.println("Студента з таким ID не знайдено.");
            }
        }
    }

    protected void changeStudent() {
        boolean found = false;
        while (!found) {
            System.out.println("Введіть ідентифікатор студента, що треба замінити (0 для виходу)");
            String studentId = scanner.nextLine();
            if ("0".equals(studentId)) return;

            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isPresent()) {
                found = true;
                Student student = optionalStudent.get();
                logger.info("Початок редагування студента: {} (ID: {})", student.getFullName(), studentId);

                boolean status = true;
                while (status) {
                    System.out.println("\n*-Оберіть, що змінити-*");
                    changeList.forEach(System.out::println);
                    String inputLine = scanner.nextLine();
                    try {
                        int input = Integer.parseInt(inputLine);
                        switch (input) {
                            case 1:
                                System.out.println("Введіть нове ім'я");
                                do {
                                    try {
                                        student.setFirstName(scanner.nextLine());
                                        studentService.saveAllData();
                                        logger.info("Поле 'ім'я' студента ID {} змінено на {}", studentId, student.getFirstName());
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        logger.warn("Помилка зміни імені: {}", e.getMessage());
                                        resume = false;
                                    }

                                } while (!resume);
                                break;
                            case 2:
                                System.out.println("Введіть нове прізвище");
                                do {
                                    try {
                                        student.setLastName(scanner.nextLine());
                                        studentService.saveAllData();
                                        logger.info("Поле 'прізвище' студента ID {} змінено на {}", studentId, student.getLastName());
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        logger.warn("Помилка зміни прізвища: {}", e.getMessage());
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;

                            case 3:
                                System.out.println("Введіть нове по батькові");
                                do {
                                    try {
                                        student.setMiddleName(scanner.nextLine());
                                        studentService.saveAllData();
                                        logger.info("Поле 'по батькові' студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        logger.warn("Помилка зміни по батькові: {}", e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;

                            case 4:
                                System.out.println("Введіть нову дату народження студента");
                                do {
                                    try {
                                        student.setBirthDate(LocalDate.parse(scanner.nextLine(), formatter));
                                        studentService.saveAllData();
                                        logger.info("Поле 'дата народження' студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Введіть коректну дату");
                                        resume = false;
                                    }
                                } while (!resume);
                                break;

                            case 5:
                                System.out.println("Введіть електронну пошту");
                                do {
                                    try {
                                        student.setEmail(scanner.nextLine());
                                        studentService.saveAllData();
                                        logger.info("Поле 'email' студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        logger.warn("Помилка зміни email: {}", e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 6:
                                System.out.println("Введіть новий номер телефону");
                                do {
                                    try {
                                        student.setPhone(scanner.nextLine());
                                        studentService.saveAllData();
                                        logger.info("Поле 'телефон' студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        logger.warn("Помилка зміни телефону: {}", e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;

                            case 7:
                                System.out.println("Введіть новий номер студентського квитка");
                                do {
                                    try {
                                        student.setStudentId(scanner.nextLine());
                                        studentService.saveAllData();
                                        logger.info("Поле 'ID студента' (квиток) студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        logger.warn("Помилка зміни номера квитка: {}", e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 8:
                                System.out.println("Введіть новий курс");
                                do {
                                    try {
                                        student.setCourse(Integer.parseInt(scanner.nextLine()));
                                        studentService.saveAllData();
                                        logger.info("Поле 'курс' студента ID {} змінено на {}", studentId, student.getCourse());
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        logger.warn("Помилка зміни курсу: {}", e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 9:
                                System.out.println("Введіть нову групу");
                                student.setGroup(scanner.nextLine());
                                studentService.saveAllData();
                                logger.info("Поле 'група' студента ID {} змінено на {}", studentId, student.getGroup());
                                break;

                            case 10:
                                System.out.println("Введіть новий рік вступу");
                                do {
                                    try {
                                        student.setEnrollmentYear(Integer.parseInt(scanner.nextLine()));
                                        studentService.saveAllData();
                                        logger.info("Поле 'рік вступу' студента ID {} змінено на {}", studentId, student.getEnrollmentYear());
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        logger.warn("Помилка зміни року вступу: {}", e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;

                            case 11:
                                do {
                                    status = true;
                                    System.out.println("\n*-Оберіть нову форму навчання: -*");
                                    studyForms.forEach(System.out::println);
                                    String inputLocalLine = scanner.nextLine();
                                    try {
                                        int inputLocal = Integer.parseInt(inputLocalLine);
                                        switch (inputLocal) {
                                            case 1:
                                                student.setStudyForm(BUDGET);
                                                studentService.saveAllData();
                                                break;
                                            case 2:
                                                student.setStudyForm(CONTRACT);
                                                studentService.saveAllData();
                                                break;
                                            case 0:
                                                status = false;
                                                break;
                                            default:
                                                System.out.println("Введіть коректне значення");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Введіть коректне значення");
                                        logger.error("Помилка вибору форми навчання");
                                    }
                                } while (status);
                                break;

                            case 12:
                                do {
                                    System.out.println("\nОберіть новий статус:");
                                    studentStatuses.forEach(System.out::println);
                                    String inputLocalLine = scanner.nextLine();
                                    try {
                                        int inputLocal = Integer.parseInt(inputLocalLine);
                                        switch (inputLocal) {
                                            case 1:
                                                student.setStudentStatus(STUDYING);
                                                studentService.saveAllData();
                                                status = false;
                                                break;
                                            case 2:
                                                student.setStudentStatus(ACADEMIC_LEAVE);
                                                studentService.saveAllData();
                                                status = false;
                                                break;
                                            case 3:
                                                student.setStudentStatus(EXPELLED);
                                                studentService.saveAllData();
                                                status = false;
                                            case 0:
                                                status = false;
                                                break;
                                            default:
                                                System.out.println("Введіть коректне значення");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Введіть коректне значення");
                                            logger.error("Помилка зміни статусу");System.out.println("Введіть коректне значення");
                                        }
                                } while (status);
                                break;

                            case 13:
                                System.out.println("Введіть новий ID факультету");
                                do {
                                    try {
                                        student.setFacultyId(scanner.nextLine());
                                        studentService.saveAllData();
                                        logger.info("Факультет студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        logger.warn("Помилка зміни факультету: {}", e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;

                            case 14:
                                System.out.println("Введіть нову спеціальність");
                                do {
                                    try {
                                        student.setSpecialty(scanner.nextLine());
                                        studentService.saveAllData();
                                        logger.info("Спеціальність студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        logger.warn("Помилка зміни спеціальності: {}", e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;

                            case 15:
                                System.out.println("Введіть новий ID кафедри");
                                do {
                                    try {
                                        student.setDepartmentId(scanner.nextLine());
                                        studentService.saveAllData();
                                        logger.info("Кафедру студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        logger.warn("Помилка зміни кафедри: {}", e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;

                            case 0:
                                status = false;
                                break;

                            default:
                                logger.warn("Невірний пункт підменю редагування: {}", input);
                                System.out.println("Введіть коректне значення");
                        }
                    } catch (NumberFormatException e) {
                        logger.error("Помилка вводу в меню редагування студента");
                        System.out.println("Введіть коректне значення");
                    }
                }
            } else {
                logger.warn("Студента з ID {} не знайдено для редагування", studentId);
                System.out.println("Студента з таким ID не знайдено.");
            }
        }
    }

    protected void addStudent() {
        Student student = studentGenerator();
        studentService.createStudent(student);
        logger.info("Студента '{}' успішно збережено в базу", student.getFullName());
    }
}