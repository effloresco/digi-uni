package university.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import university.domain.Department;
import university.domain.Faculty;
import university.domain.Student;
import university.network.Client;
import university.repository.StudentRepository;
import university.service.RemoteDepartmentService;
import university.service.RemoteFacultyService;
import university.service.RemoteStudentService;
import university.service.SearchService;
import university.service.StudentService;
import university.exceptions.*;
import university.service.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static university.domain.Student.StudentStatus.*;
import static university.domain.Student.StudyForm.*;
import static university.service.SearchService.*;
import static university.service.Utils.*;

public class StudentMenu {
    private static final Logger logger = LoggerFactory.getLogger(StudentMenu.class);

    private final Client client;
    private final Scanner scanner = new Scanner(System.in);
    protected final RemoteStudentService studentService;
    protected final RemoteFacultyService facultyService;
    protected final RemoteDepartmentService departmentService;
    boolean resume;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String exitOpt = null;
    private final List<String> menuOptions = List.of(
            "[1] Додати студента",
            "[2] Змінити інформацію про студента",
            "[3] Видалити студента з бази даних",
            OPT0
    );
    private final List<String> changeList = List.of(
            "[1] Ім'я",
            "[2] Прізвище",
            "[3] По батькові",
            "[4] Дата народження",
            "[5] Електронна пошта",
            "[6] Номер телефону",
            "[7] Ідентифікатор студента",
            "[8] Курс",
            "[9] Група",
            "[10] Рік вступу",
            "[11] Форма навчання",
            "[12] Статус студента",
            "[13] Факультет",
            "[14] Спеціальність",
            "[15] Кафедра",
            OPT0
    );

    private final List<String> studentStatuses = List.of(
            "[1] Навчається",
            "[2] В академічній відпустці",
            "[3] Відрахований",
            OPT0
    );

    private final List<String> studyForms = List.of(
            "[1] Бюджет",
            "[2] Контракт",
            OPT0
    );

    public StudentMenu(Client client) {
        this.client = client;
        studentService = new RemoteStudentService(client);
        facultyService = new RemoteFacultyService(client);
        departmentService = new RemoteDepartmentService(client);
    }

    protected void studentManagement() {
        logger.info("Відкрито меню управління студентами");
        boolean status = true;
        while (status) {
            printMenu("Управління студентами", menuOptions);
            printPrompt("Виберіть опцію >");
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
                        printMessage(Utils.Mt.Error, "Некоректний вибір");
                }
            } catch (NumberFormatException e) {
                logger.error("Помилка формату числа при виборі меню: {}", inputLine);
                printMessage(Utils.Mt.Error, "Некоректне значення");
            }
        }
    }

    protected Student studentGenerator() {
        logger.debug("Запущено генератор нового студента");
        boolean status;
        Student student = new Student();
        printHeader ("Додавання студента");
        printMessage(Mt.Info, "ID студента: " + student.getID());

        do {
            printPrompt("Ім'я >");
            try {
                student.setFirstName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація імені не пройдена: {}", e.getMessage());
                printMessage(Utils.Mt.Error, "Некоректне значення");
                resume = false;
            }
        } while (!resume);

        do {
            printPrompt("Прізвище >");
            try {
                student.setLastName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація прізвища не пройдена: {}", e.getMessage());
                printMessage(Utils.Mt.Error, "Некоректне значення");
                resume = false;
            }
        } while (!resume);

        do {
            printPrompt("По батькові >");
            try {
                student.setMiddleName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Валідація по батькові не пройдена: {}", e.getMessage());
                printMessage(Utils.Mt.Error, "Некоректне значення");
                resume = false;
            }
        } while (!resume);

        do {
            printPrompt("Дата народження (дд.мм.рррр) >");
            try {
                student.setBirthDate(LocalDate.parse(scanner.nextLine(), formatter));
                resume = true;
            } catch (DateTimeParseException e) {
                logger.warn("Помилка парсингу дати народження");
                printMessage(Mt.Error, "Некоректний формат дати");
                resume = false;
            }
        } while (!resume);

        printPrompt("Електронна пошта >");
        do {
            try {
                student.setEmail(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректний email: {}", e.getMessage());
                printMessage(Utils.Mt.Error, "Некоректне значення");
                resume = false;
            }
        } while (!resume);

        do {
            printPrompt("Номер телефону >");
            try {
                student.setPhone(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректний номер телефону: {}", e.getMessage());
                printMessage(Utils.Mt.Error, "Некоректне значення");
                resume = false;
            }
        } while (!resume);

        do {
            printPrompt("Номер залікової книжки >");
            try {
                student.setStudentId(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректний ID студента: {}", e.getMessage());
                printMessage(Utils.Mt.Error, "Некоректне значення");
                resume = false;
            }
        } while (!resume);

        do {
            printPrompt("Курс >");
            try {
                student.setCourse(Integer.parseInt(scanner.nextLine()));
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Помилка введення курсу");
                printMessage(Utils.Mt.Error, "Введіть число від 1 до 6");
                resume = false;
            }
        } while (!resume);

        printPrompt("Група >");
        student.setGroup(scanner.nextLine());

        do {
            printPrompt("ID факультету >");
            try {
                String facultyId = scanner.nextLine();
                Faculty faculty = facultyService.getFaculty(facultyId);
                student.setFacultyId(facultyId);
                resume = true;
            } catch (InvalidValue | FacultyNotFoundException e) {
                logger.warn("Некоректний ID факультету: {}", e.getMessage());
                printMessage(Utils.Mt.Error, "Некоректне значення");
                resume = false;
                System.out.println("0 - Пропустити");
                exitOpt = scanner.nextLine();
                if (exitOpt.equals("0")) break;
            }
        } while (!resume);

        do {
            printPrompt("Назва спеціальності >");
            try {
                student.setSpecialty(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Некоректна назва спеціальності: {}", e.getMessage());
                printMessage(Utils.Mt.Error, "Некоректне значення");
                resume = false;
            }
        } while (!resume);

        do {
            printPrompt("ID кафедри >");
            try {
                String departmentId = scanner.nextLine();
                Department department = departmentService.getDepartment(departmentId);
                student.setDepartmentId(departmentId);
                resume = true;
            } catch (InvalidValue | DepartmentNotFoundException e) {
                logger.warn("Некоректний ID кафедри: {}", e.getMessage());
                System.out.println(e.getMessage());
                resume = false;
                System.out.println("0 - Пропустити");
                exitOpt = scanner.nextLine();
                if (exitOpt.equals("0")) break;
                resume = false;
            }
        } while (!resume);

        do {
            printPrompt("Рік вступу >");
            try {
                student.setEnrollmentYear(Integer.parseInt(scanner.nextLine()));
                resume = true;
            } catch (InvalidValue e) {
                logger.warn("Помилка введення року вступу");
                printMessage(Utils.Mt.Error, "Введіть коректний рік");
                resume = false;
            }
        } while (!resume);

        do {
            status = true;
            printMessage(Mt.Prompt, "Оберіть форму навчання:");
            printMenu(studyForms);
            printPrompt(">");
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
                        printMessage(Utils.Mt.Error, "Некоректний вибір");
                }
            } catch (NumberFormatException e) {
                printMessage(Utils.Mt.Error, "Введіть число");
            }
        } while (status);

        do {
            status = true;
            printMessage(Mt.Prompt, "Оберіть статус студента:");
            printMenu(studentStatuses);
            printPrompt(">");
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
                        printMessage(Utils.Mt.Error, "Некоректний вибір");
                }
            } catch (NumberFormatException e) {
                logger.error("Помилка вибору статусу студента");
                printMessage(Mt.Error, "Введіть число");
            }
        } while (status);
        logger.info("Об'єкт студента згенеровано: {} (ID: {})", student.getFullName(), student.getID());
        return student;
    }

    protected void deleteStudent() {
        printHeader("Видалення студента");
        boolean found = false;
        while (!found) {
            printMessage(Mt.Prompt, "Введіть ID студента, якого треба видалити", "[0] Вихід");
            printPrompt(">");
            String studentId = scanner.nextLine();
            if (studentId.equals("0")) return;
            studentService.deleteStudent(studentId);
        }
    }

    protected void changeStudent() {
        boolean found = false;
        String studentId;
        Student student;
        printHeader("Редагування студента");
        while (!found) {
            try {
            printMessage(Mt.Prompt, "ID студента для редагування", "[0] Вихід");
            printPrompt(">");
             studentId = scanner.nextLine();
                if (studentId.equals("0")) return;
                student = studentService.getStudent(studentId);
                found = true;
                logger.info("Початок редагування студента: {} (ID: {})", student.getFullName(), studentId);
                printMessage(Mt.Info, "Редагування: " + student.getFullName());

                boolean status = true;
                while (status) {
                    printMenu("Оберіть, що змінити?", changeList);
                    String inputLine = scanner.nextLine();
                    try {
                        int input = Integer.parseInt(inputLine);
                        switch (input) {
                            case 1:
                                System.out.println("Введіть нове ім'я");
                                do {
                                    try {
                                        student.setFirstName(scanner.nextLine());
                                        studentService.updateStudent(student);
                                        logger.info("Поле 'ім'я' студента ID {} змінено на {}", studentId, student.getFirstName());
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
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
                                        studentService.updateStudent(student);
                                        logger.info("Поле 'прізвище' студента ID {} змінено на {}", studentId, student.getLastName());
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
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
                                        studentService.updateStudent(student);
                                        logger.info("Поле 'по батькові' студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
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
                                        studentService.updateStudent(student);
                                        logger.info("Поле 'дата народження' студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Введіть коректну дату");
                                        resume = false;
                                    } catch (PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;

                            case 5:
                                System.out.println("Введіть електронну пошту");
                                do {
                                    try {
                                        student.setEmail(scanner.nextLine());
                                        studentService.updateStudent(student);
                                        logger.info("Поле 'email' студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
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
                                        studentService.updateStudent(student);
                                        logger.info("Поле 'телефон' студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
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
                                        studentService.updateStudent(student);
                                        logger.info("Поле 'ID студента' (квиток) студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
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
                                        studentService.updateStudent(student);
                                        logger.info("Поле 'курс' студента ID {} змінено на {}", studentId, student.getCourse());
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        logger.warn("Помилка зміни курсу: {}", e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 9:
                                System.out.println("Введіть нову групу");
                                try{
                                student.setGroup(scanner.nextLine());
                                studentService.updateStudent(student);
                                logger.info("Поле 'група' студента ID {} змінено на {}", studentId, student.getGroup());
                                    resume = true;
                                } catch (PersonNotFoundException e) {
                                    System.out.println(e.getMessage());
                                    resume = false;
                                }
                                break;

                            case 10:
                                System.out.println("Введіть новий рік вступу");
                                do {
                                    try {
                                        student.setEnrollmentYear(Integer.parseInt(scanner.nextLine()));
                                        studentService.updateStudent(student);
                                        logger.info("Поле 'рік вступу' студента ID {} змінено на {}", studentId, student.getEnrollmentYear());
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
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
                                                studentService.updateStudent(student);
                                                break;
                                            case 2:
                                                student.setStudyForm(CONTRACT);
                                                studentService.updateStudent(student);
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
                                    } catch (PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
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
                                                studentService.updateStudent(student);
                                                status = false;
                                                break;
                                            case 2:
                                                student.setStudentStatus(ACADEMIC_LEAVE);
                                                studentService.updateStudent(student);
                                                status = false;
                                                break;
                                            case 3:
                                                student.setStudentStatus(EXPELLED);
                                                studentService.updateStudent(student);
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
                                    } catch (PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                    }
                                } while (status);
                                break;

                            case 13:
                                System.out.println("Введіть новий ID факультету");
                                do {
                                    try {
                                        String facultyId = scanner.nextLine();
                                        Faculty faculty = facultyService.getFaculty(facultyId);
                                        student.setFacultyId(facultyId);
                                        studentService.updateStudent(student);
                                        logger.info("Факультет студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue | FacultyNotFoundException e) {
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
                                        studentService.updateStudent(student);
                                        logger.info("Спеціальність студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
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
                                        String departmentId = scanner.nextLine();
                                        Department department = departmentService.getDepartment(departmentId);
                                        student.setDepartmentId(departmentId);
                                        studentService.updateStudent(student);
                                        logger.info("Кафедру студента ID {} змінено", studentId);
                                        resume = true;
                                    } catch (InvalidValue | DepartmentNotFoundException e) {
                                        printMessage(Utils.Mt.Error, "Некорректне значення");
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
                                printMessage(Utils.Mt.Error, "Некоректний вибір");
                        }
                    } catch (NumberFormatException e) {
                        logger.error("Помилка вводу в меню редагування студента: {}", e.getMessage());
                        printMessage(Utils.Mt.Error, "Введено не число");
                    }
                }
            } catch (PersonNotFoundException e) {
                logger.warn("Студента з цим ID  не знайдено для редагування");
                printMessage(Utils.Mt.Error, "Студента з таким ID не знайдено");
            }
        }
    }

    protected void addStudent() {
        Student student = studentGenerator();
        studentService.createStudent(student);
        logger.info("Студента '{}' успішно збережено в базу", student.getFullName());
        printMessage(Mt.Success, "Cтудента додано");
    }
}