package university.ui;

import university.domain.Student;
import university.network.Client;
import university.repository.StudentRepository;
import university.service.RemoteStudentService;
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
    private final Client client;
    protected final StudentRepository studentRepository = StudentRepository.get(StudentRepository.class);
    protected final RemoteStudentService studentService;
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
            "6 - Ідентифікатор студента",
            "8 - Курс",
            "9 - Групу",
            "10 - Рік вступу",
            "11 - форму навчання",
            "12 - Статус студента",
            "13 - Факультет",
            "14 - Спеціальність",
            "15 - Кафедру",
            opt0);

    private final List<String> studentStatuses = List.of(
            "1 - Вчиться",
            "2 - У академічній відпустці",
            "3 - Відрахований",
            opt0);

    private final List<String> studyForms = List.of(
            "1 - Бюджет",
            "2 - Контракт",
            opt0);

    private final List<String> menuOptions = List.of(
            "1 - Додати студента",
            "2 - Змінити інформацію про студента",
            "3 - Видалити студента з бази даних",
            opt0);

    public StudentMenu(Client client) {
        this.client = client;
        studentService = new RemoteStudentService(client);
    }

    protected void studentManagement() {
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

    protected Student studentGenerator() {
        boolean status;
        Student student = new Student();
        System.out.println("Ідентифікатор студента: " + student.getID());

        System.out.println("Введіть ім'я");
        do {
            try {
                student.setFirstName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
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
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть ідентифікатор студента");
        do {
            try {
                student.setStudentId(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть  курс");
        do {
            try {
                student.setCourse(Integer.parseInt(SearchService.scanner.nextLine()));
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть групу");
        student.setGroup(scanner.nextLine());
        resume = true;

        System.out.println("Введіть ID факультету");
        do {
            try {
                student.setFacultyId(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
                System.out.println("0 - Вихід");
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
                System.out.println(e.getMessage());
                resume = false;
                System.out.println("0 - Вихід");
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
                System.out.println("Введіть коректне значення");
            }
        } while (status);

        System.out.println("Студента " + student.getFullName() + " створено");
        return student;
    }

    protected void deleteStudent() {
        boolean found = false;
        while (!found) {
            System.out.println("Введіть ідентифікатор студента, якого треба видалити (нуль, щоб вийти)");
            String studentId = scanner.nextLine();
            if (studentId.equals("0")) {
                return;
            }

            studentService.deleteStudent(studentId);
        }

    }

    protected void changeStudent() {
        boolean found = false;
        String studentId;
        Student student;
        while (!found) {
            try {
                System.out.println("Введіть ідентифікатор студента, що треба замінити");
                studentId = scanner.nextLine();
                student = studentService.getStudent(studentId);
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
                                System.out.println("Введіть ім'я");
                                do {
                                    try {
                                        student.setFirstName(scanner.nextLine());
                                        studentService.updateStudent(student);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }

                                } while (!resume);
                                break;
                            case 2:
                                System.out.println("Введіть прізвище");
                                do {
                                    try {
                                        student.setLastName(scanner.nextLine());
                                        studentService.updateStudent(student);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 3:
                                System.out.println("Введіть по батькові");
                                do {
                                    try {
                                        student.setMiddleName(scanner.nextLine());
                                        studentService.updateStudent(student);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 4:
                                System.out.println("Введіть дату народження студента");
                                do {
                                    try {
                                        student.setBirthDate(LocalDate.parse(SearchService.scanner.nextLine(), formatter));
                                        studentService.updateStudent(student);
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
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 6:
                                System.out.println("Введіть номер телефону");
                                do {
                                    try {
                                        student.setPhone(scanner.nextLine());
                                        studentService.updateStudent(student);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 7:
                                System.out.println("Введіть ідентифікатор студента");
                                do {
                                    try {
                                        student.setStudentId(scanner.nextLine());
                                        studentService.updateStudent(student);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 8:
                                System.out.println("Введіть  курс");
                                do {
                                    try {
                                        student.setCourse(Integer.parseInt(SearchService.scanner.nextLine()));
                                        studentService.updateStudent(student);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 9:
                                System.out.println("Введіть групу");
                                try {
                                    student.setGroup(scanner.nextLine());
                                    studentService.updateStudent(student);
                                    resume = true;
                                } catch (PersonNotFoundException e) {
                                    System.out.println(e.getMessage());
                                    resume = false;
                                }
                                break;
                            case 10:
                                System.out.println("Введіть рік вступу");
                                do {
                                    try {
                                        student.setEnrollmentYear(Integer.parseInt(SearchService.scanner.nextLine()));
                                        studentService.updateStudent(student);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }

                                } while (!resume);
                                break;
                            case 11:
                                do {
                                    status = true;
                                    System.out.println("\n*-Оберіть форму навчання: -*");
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
                                    } catch (PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                    }
                                } while (status);
                                break;
                            case 12:
                                do {
                                    System.out.println("\n*-Оберіть статус студента: -*");
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
                                    } catch (PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                    }
                                } while (status);
                                break;

                            case 13:
                                System.out.println("Введіть ID факультету");
                                do {
                                    try {
                                        student.setFacultyId(scanner.nextLine());
                                        studentService.updateStudent(student);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 14:
                                System.out.println("Введіть назву спеціальності");
                                do {
                                    try {
                                        student.setSpecialty(scanner.nextLine());
                                        studentService.updateStudent(student);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 15:
                                System.out.println("Введіть ID кафедри");
                                do {
                                    try {
                                        student.setDepartmentId(scanner.nextLine());
                                        studentService.updateStudent(student);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
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
            } catch (PersonNotFoundException e) {
                System.out.println("Студента з таким ID не знайдено.");
            }
        }
    }

    protected void addStudent() {
        studentService.createStudent(studentGenerator());
    }

}
