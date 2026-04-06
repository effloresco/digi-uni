package university.ui;

import university.domain.Student;
import university.repository.StudentRepository;
import university.service.SearchService;
import university.service.StudentService;
import university.exceptions.*;
import university.service.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static university.domain.Student.StudentStatus.*;
import static university.domain.Student.StudyForm.*;
import static university.service.SearchService.*;

public class StudentMenu {
    protected final StudentRepository studentRepository = StudentRepository.get(StudentRepository.class);
    protected final StudentService studentService = new StudentService(studentRepository);
    boolean resume;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final String opt0 = "0 - Вихід";
    private List<String> changeList = List.of("1 - ID", "2 - Ім'я", "3 - Прізвище", "4 - По батькові", "5 - Дату народження", "6 - Електронну пошту", "7 - Номер телефону", "8 - Ідентифікатор студента", "9 - Курс", "10 - Групу", "11 - Рік вступу", "12 - форму навчання", "13 - Статус студента", opt0);

    private final List<String> studentStatuses = List.of("1 - Вчиться", "2 - У академічній відпустці", "3 - Відрахований", opt0);

    private final List<String> studyForms = List.of("1 - Бюджет", "2 - Контракт", opt0);

    private final List<String> menuOptions = List.of("1 - Додати студента", "2 - Змінити інформацію про студента", "3 - Видалити студента з бази даних", opt0);

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
        Student student = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор студента, якого треба видалити");
            String studentId = scanner.nextLine();

            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isPresent()) {
                student = optionalStudent.get();
                found = true;
            } else {
                System.out.println("Студента з таким ID не знайдено.");
                break;
            }
        }
        studentService.deleteStudent(student);
    }

    protected void changeStudent() {
        boolean found = false;
        String studentId;
        Student student;
        while (!found) {
            System.out.println("Введіть ідентифікатор студента, що треба замінити");
            studentId = scanner.nextLine();

            Optional<Student> optionalStudent = studentRepository.findById(studentId);

            if (optionalStudent.isPresent()) {
                found = true;
                student = optionalStudent.get();
                boolean status = true;
                while (status) {
                    System.out.println("\n*-Оберіть, що змінити-*");
                    changeList.forEach(System.out::println);
                    String inputLine = scanner.nextLine();
                    try {
                        int input = Integer.parseInt(inputLine);
                        switch (input) {
                            case 1:
                                System.out.println("Введіть ідентифікатор викладача");
                                do {
                                    try {
                                        student.setId(scanner.nextLine());
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 2:
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
                                break;
                            case 3:
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
                                break;
                            case 4:
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
                                break;
                            case 5:
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
                                break;
                            case 6:
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
                                break;
                            case 7:
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
                                break;
                            case 8:
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
                                break;
                            case 9:
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
                                break;
                            case 10:
                                System.out.println("Введіть групу");
                                student.setGroup(scanner.nextLine());
                                resume = true;
                                break;
                            case 11:
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
                                break;
                            case 12:
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
                                                break;
                                            case 2:
                                                student.setStudyForm(CONTRACT);
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
                                break;
                            case 13:
                                do {
                                    System.out.println("\n*-Оберіть статус студента: -*");
                                    studentStatuses.forEach(System.out::println);
                                    String inputLocalLine = scanner.nextLine();
                                    try {
                                        int inputLocal = Integer.parseInt(inputLocalLine);
                                        switch (inputLocal) {
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

    protected void addStudent() {
        studentService.createStudent(studentGenerator());
    }

}
