package university.ui;

import university.domain.Person;
import university.domain.Student;
import university.repository.StudentRepository;
import university.service.StudentService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

import static university.domain.Student.StudentStatus.STUDYING;
import static university.domain.Student.StudyForm.BUDGET;
import static university.domain.Student.StudyForm.CONTRACT;
import static university.service.SearchService.*;
import static university.service.Utils.*;


public class StudentMenu {
    protected final StudentRepository studentRepository = StudentRepository.get();
    protected final StudentService studentService = new StudentService(new StudentRepository());

    protected void studentManaging() {
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
                        deleteStudent();
                        break;
                    case 3:
                        changeStudent();
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
        int min = 100000000;
        int max = 999999999;
        Random random = new Random();
        Integer randomNumber = random.nextInt(max - min + 1) + min;

        boolean resume;
        boolean exists;

        String studentId;
        String firstName;
        String lastName;
        String middleName;

        String email;
        String phone;
        int course;
        int enrollmentYear = 0;

        String id = randomNumber.toString();
        System.out.println("Введіть ідентифікатор студента");
        do {
            studentId = scanner.nextLine();
            if (containsNonDigit(studentId)) {
                System.out.println("ID може містити лише числа");
                resume = false;
            } else resume = true;
        } while (!resume);

        System.out.println("Введіть ім'я");
        do {
            firstName = scanner.nextLine();
            if (containsNonLetter(firstName)) {
                System.out.println("Ім'я може містити лише літери");
                resume = false;
            } else resume = true;

        } while (!resume);

        System.out.println("Введіть прізвище");
        do {

            lastName = scanner.nextLine();
            if (containsNonLetter(lastName)) {
                System.out.println("Прізвище може містити лише літери");
                resume = false;
            } else resume = true;
        } while (!resume);

        System.out.println("Введіть по батькові");
        do {
            middleName = scanner.nextLine();
            if (containsNonLetter(middleName)) {
                System.out.println("По батькові може містити лише літери");
                resume = false;

            } else resume = true;
        } while (!resume);

        System.out.println("Введіть дату народження");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine(), formatter);

        System.out.println("Введіть електронну пошту");
        do {
            email = scanner.nextLine();
            if (!email.contains("@") || !email.contains(".")) {
                System.out.println("Електронна пошта має містити '@' та '.'");
                resume = false;

            } else resume = true;
        } while (!resume);

        System.out.println("Введіть номер телефону");
        do {
            phone = scanner.nextLine();
            if (containsNonDigit(phone) || phone.length() > 12 || phone.length() < 10) {
                System.out.println("Потрібно вказати дійсний номер телефону");
                resume = false;

            } else resume = true;
        } while (!resume);

        System.out.println("Введіть курс студента");
        do {
            course = Integer.parseInt(scanner.nextLine());
            if (course > 6 || course < 1) {
                System.out.println("Курс можлививй лише від 1 до 6!");
                resume = false;

            } else resume = true;
        } while (!resume);

        System.out.println("Введіть групу студента");
        String group = scanner.nextLine();

        System.out.println("Введіть рік вступу студента");

        do {
            String year = scanner.nextLine();
            if (containsNonDigit(year)) {
                resume = false;
            } else {
                enrollmentYear = Integer.parseInt(year);

                if (enrollmentYear < 2020 || enrollmentYear > 2026) {
                    resume = false;
                } else
                    resume = true;
            }
            if (!resume) System.out.println("Вкажіть дійсний рік вступу");

        } while (!resume);

        System.out.println("Оберіть форму навчання студента");
        Student.StudyForm form = CONTRACT;

        do {
            exists = false;
            System.out.println("1. Бюджет");
            System.out.println("2. Контракт");
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        form = BUDGET;
                        exists = true;
                        break;
                    case 2:
                        form = CONTRACT;
                        exists = true;
                        break;
                    default:
                        System.out.println("Введіть коректне значення");
                        exists = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        } while (!exists);

        Student.StudentStatus status = STUDYING;

        System.out.println("Користувача " + lastName + " " + firstName + " Було створено");

        return new Student(id, lastName, firstName, middleName, birthDate, email, phone,
                studentId, course, group, enrollmentYear, status, form);
    }


    protected void changeStudent() {
        boolean found = false;
        String studentId = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор студента, інформацію пр якого треба замінити");
            studentId = scanner.nextLine();


            Optional<Person> optionalPerson = studentRepository.findById(studentId);

            if (optionalPerson.isPresent()) {
                found = true;
            } else
                System.out.println("Факультет з таким ID не знайдено.");
        }
        studentService.updateStudent(studentId, studentGenerator());
    }

    protected void deleteStudent() {
        boolean found = false;
        Student student = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор студента, якого треба видалити");
            String studentId = scanner.nextLine();

            Optional<Person> optionalPerson = studentRepository.findById(studentId);

            if (optionalPerson.isPresent()) {
                student = (Student) optionalPerson.get();
                found = true;
            } else
                System.out.println("Студента з таким ID не знайдено.");
        }
        studentService.deleteStudent(student);
    }

    protected void addStudent() {
        studentService.createStudent(studentGenerator());
    }
}
