package university.ui;

import university.domain.Person;
import university.domain.Student;
import university.repository.StudentRepository;
import university.service.SearchService;
import university.service.StudentService;
import university.exceptions.*;
import university.service.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import static university.domain.Student.StudentStatus.*;
import static university.domain.Student.StudyForm.*;
import static university.service.SearchService.*;

public class StudentMenu {
    protected final StudentRepository studentRepository = StudentRepository.get(StudentRepository.class);
    protected final StudentService studentService = new StudentService(studentRepository);
    boolean resume;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private List<String> changeList = new ArrayList<>();

    private final String opt0 = "0 - Вихід";
    private final String opt1change = "1 - ID";
    private final String opt2change = "2 - Ім'я";
    private final String opt3change = "3 - Прізвище";
    private final String opt4change = "4 - По батькові";
    private final String opt5change = "5 - Дату народження";
    private final String opt6change = "6 - Електронну пошту";
    private final String opt7change = "7 - Номер телефону";
    private final String opt8change = "8 - Ідентифікатор студента";
    private final String opt9change = "9 - Курс";
    private final String opt10change = "10 - Групу";
    private final String opt11change = "11 - Рік вступу";
    private final String opt12change = "12 - форму навчання";
    private final String opt13change = "13 - Статус студента";

    private List<String> studentStatuses = new ArrayList<>();
    private final String opt1status = "1 - Вчиться";
    private final String opt2status = "2 - У академічній відпустці";
    private final String opt3status = "3 - Відрахований";

    private List<String> studyForms = new ArrayList<>();
    private final String opt1form = "1 - Бюджет";
    private final String opt2form = "2 - Контракт";

    private List<String> menuOptions = new ArrayList<>();
    private final String opt1g = "1 - Додати студента";
    private final String opt2g = "2 - Змінити інформацію про студента";
    private final String opt3g = "3 - Видалити студента з бази даних";

    protected void studentManagement() {
        boolean status = true;
        menuOptions.add(opt1g);
        menuOptions.add(opt2g);
        menuOptions.add(opt3g);
        menuOptions.add(opt0);
        while (status) {
            System.out.println("\n*-Управління студентами-*");
            for (String option : menuOptions) {
                System.out.println(option);
            }
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
        do {
            try {
                student.setId(String.valueOf(Utils.getRandomNumber()));
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);
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

        studyForms.add(opt1form);
        studyForms.add(opt2form);
        studyForms.add(opt0);

        do {
            status = true;
            System.out.println("\n*-Оберіть форму навчання: -*");
            for (String option : studyForms) {
                System.out.println(option);
            }
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

        studentStatuses.add(opt1status);
        studentStatuses.add(opt2status);
        studentStatuses.add(opt3status);
        studentStatuses.add(opt0);
        do {
            status = true;
            System.out.println("\n*-Оберіть статус студента: -*");
            for (String option : studentStatuses) {
                System.out.println(option);
            }
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

        System.out.println("Студента "+student.getFullName()+" створено");
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
                student = (Student) optionalStudent.get();
                found = true;
            } else{ System.out.println("Студента з таким ID не знайдено.");
            break;}
        }
        studentService.deleteStudent(student);
    }

    protected void changeStudent() {
        boolean found = false;
        String studentId = null;
        Student student = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор студента, що треба замінити");
            studentId = scanner.nextLine();

            Optional<Student> optionalStudent = studentRepository.findById(studentId);

            if (optionalStudent.isPresent()) {
                found = true;
                student = (Student) optionalStudent.get();
                boolean status = true;
                changeList.add(opt1change);
                changeList.add(opt2change);
                changeList.add(opt3change);
                changeList.add(opt4change);
                changeList.add(opt5change);
                changeList.add(opt6change);
                changeList.add(opt7change);
                changeList.add(opt8change);
                changeList.add(opt9change);
                changeList.add(opt10change);
                changeList.add(opt11change);
                changeList.add(opt12change);
                changeList.add(opt13change);
                changeList.add(opt0);
                while (status) {
                    System.out.println("\n*-Оберіть, що змінити-*");
                    for (String option : changeList) {
                        System.out.println(option);
                    }
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
                                            for (String option : studyForms) {
                                                System.out.println(option);
                                            }
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
                                    for (String option : studentStatuses) {
                                        System.out.println(option);
                                    }
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
                        } catch(NumberFormatException e){
                            System.out.println("Введіть коректне значення");
                        }
                    }
                } else System.out.println("Кафедри з таким ID не знайдено.");
            }
        }

        protected void addStudent () {
            studentService.createStudent(studentGenerator());
        }

}
