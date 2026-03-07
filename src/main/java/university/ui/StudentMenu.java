package university.ui;

import university.domain.Person;
import university.domain.Student;
import university.repository.StudentRepository;
import university.service.SearchService;
import university.service.StudentService;
import university.exceptions.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    int min = 100000000;
    int max = 999999999;
    Random random = new Random();
    Integer randomNumber = random.nextInt(max - min + 1) + min;

    protected void studentManagement() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Управління студентами-*");
            System.out.println("1 - додати студента");
            System.out.println("2 - змінити інформацію про студента");
            System.out.println("3 - видалити студента з бази даних");
            System.out.println("0 - повернутись назад");
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
                student.setId(String.valueOf(random.nextInt(max - min + 1) + min));
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


        do {
            status = true;
            System.out.println("\n*-Оберіть форму навчання: -*");
            System.out.println("1 - Бюджет");
            System.out.println("2 - Контракт");
            System.out.println("0 - Повернутись назад");
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
            System.out.println("1 - Вчиться");
            System.out.println("2 - У академічній відпустці");
            System.out.println("3 - Відрахований");
            System.out.println("0 - Повернутись назад");
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
                while (status) {
                    System.out.println("\n*-Оберіть, що змінити-*");
                    System.out.println("1 - ID");
                    System.out.println("2 - Ім'я");
                    System.out.println("3 - Прізвище");
                    System.out.println("4 - По батькові");
                    System.out.println("5 - Дату народження");
                    System.out.println("6 - Електронну пошту");
                    System.out.println("7 - Номер телефону");
                    System.out.println("8 - Ідентифікатор студента");
                    System.out.println("9 - Курс");
                    System.out.println("10 - Групу");
                    System.out.println("11 - Рік вступу");
                    System.out.println("12 - форму навчання");
                    System.out.println("13 - Статус студента");
                    System.out.println("0 - повернутись назад");
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
                                            System.out.println("1 - Бюджет");
                                            System.out.println("2 - Контракт");
                                            System.out.println("0 - Повернутись назад");
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
                                    System.out.println("1 - Вчиться");
                                    System.out.println("2 - У академічній відпустці");
                                    System.out.println("3 - Відрахований");
                                    System.out.println("0 - Повернутись назад");
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
