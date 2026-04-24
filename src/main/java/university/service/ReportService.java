package university.service;

import university.domain.*;
import java.time.format.DateTimeFormatter;
import university.network.Client;

import java.util.*;

public class ReportService {
    private final RemoteStudentService studentService;
    private final RemoteTeacherService teacherService;
    private final RemoteFacultyService facultyService;
    private final RemoteDepartmentService departmentService;

    public ReportService(Client client) {
        studentService = new RemoteStudentService(client);
        teacherService = new RemoteTeacherService(client);
        facultyService = new RemoteFacultyService(client);
        departmentService = new RemoteDepartmentService(client);
    }

    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final String TEACHER_TABLE_FORMAT = "| %-3s | %-40s | %-10s | %-35s | %-12s | %-25s | %-30s | %-30s | %-10s | %-10s | %-30s | %-30s |%n";
    private final String TEACHER_SEPARATOR = "+" + "-".repeat(5) + "+" + "-".repeat(42) + "+" + "-".repeat(12) + "+" +
            "-".repeat(37) + "+" + "-".repeat(14) + "+" + "-".repeat(27) + "+" +
            "-".repeat(32) + "+" + "-".repeat(32) + "+" + "-".repeat(12) + "+" +
            "-".repeat(12) + "+" + "-".repeat(32) + "+" + "-".repeat(32) + "+";

    private final String TABLE_FORMAT = "| %-3s | %-50s | %-10s | %-40s | %-12s | %-20s | %-4s | %-15s | %-5s | %-15s | %-15s | %-30s | %-30s | %-30s |%n";

    private final String SEPARATOR = "+" + "-".repeat(5) + "+" + "-".repeat(52) + "+" + "-".repeat(12) + "+" +
            "-".repeat(42) + "+" + "-".repeat(14) + "+" + "-".repeat(22) + "+" +
            "-".repeat(6) + "+" + "-".repeat(17) + "+" + "-".repeat(7) + "+" +
            "-".repeat(17) + "+" + "-".repeat(17) + "+" + "-".repeat(32) + "+" +
            "-".repeat(32) + "+" + "-".repeat(32) + "+";

    private void renderStudentTable(List<Student> students, String reportName) {
        if (students.isEmpty()) {
            System.out.println("\n[!] За вказаними критеріями студентів не знайдено.");
            return;
        }

        System.out.println("\n" + " ".repeat(60) + ">>> " + reportName.toUpperCase() + " <<<");
        printHeader();
        students.forEach(this::printStudentRow);
        System.out.println(SEPARATOR);
        System.out.println("Загальна кількість: " + students.size());
    }

    private void renderTeacherTable(List<Teacher> teachers, String reportName) {
        if (teachers.isEmpty()) {
            System.out.println("\n[!] За вказаними критеріями викладачів не знайдено.");
            return;
        }
        System.out.println("\n" + " ".repeat(60) + ">>> " + reportName.toUpperCase() + " <<<");
        printTeacherHeader();
        teachers.forEach(this::printTeacherRow);
        System.out.println(TEACHER_SEPARATOR);
        System.out.println("Загальна кількість викладачів: " + teachers.size());
    }

    public void printAllStudentsAlphabetically() {
        List<Student> students = studentService.getAllStudents().stream()
            .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
            .toList();

    if (students.isEmpty()) {
        System.out.println("Список студентів порожній.");
    } else {
        renderStudentTable(students, "Повний звіт студентів (за алфавітом)");
    }
}

    public void printAllStudentsByCourse() {
        List<Student> students = studentService.getAllStudents().stream()
                .sorted(Comparator.comparing(Student::getCourse).thenComparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();


        if (students.isEmpty()) {
            System.out.println("Список студентів порожній.");
        } else {
            renderStudentTable(students, "Список студентів за курсами");
        }
    }

    public void printStudentsByFacultyAlphabetically(String facultyId) {
        List<Student> students = studentService.getAllStudents().stream()
                .filter(s -> s.getFacultyId() != null && s.getFacultyId().equals(facultyId))
                .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();

        if (students.isEmpty()) {
            System.out.println("На факультеті з ID '" + facultyId + "' студентів не знайдено.");
        } else {
            renderStudentTable(students, "Студенти факультету: " + facultyId);
        }
    }

    public void printStudentsByDepartmentByCourse(String departmentId) {
        List<Student> students = studentService.getAllStudents().stream()
                .filter(s -> s.getDepartmentId() != null && s.getDepartmentId().equals(departmentId))
                .sorted(Comparator.comparing(Student::getCourse).thenComparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();

        if (students.isEmpty()) {
            System.out.println("На кафедрі з ID '" + departmentId + "' студентів не знайдено.");
        } else {
            renderStudentTable(students, "Студенти кафедри " + departmentId + " (за курсами)");
        }
    }

    public void printStudentsByDepartmentAlphabetically(String departmentId) {
        List<Student> students = studentService.getAllStudents().stream()
                .filter(s -> s.getDepartmentId() != null && s.getDepartmentId().equals(departmentId))
                .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();

        if (students.isEmpty()) {
            System.out.println("На кафедрі з ID '" + departmentId + "' студентів не знайдено.");
        } else {
            renderStudentTable(students, "Студенти кафедри " + departmentId + " (алфавіт)");
        }
    }

    public void printStudentsByDeptAndCourse(String departmentId, int course) {
        if (course < 1 || course > 6) {
            System.out.println("Помилка: Курс має бути від 1 до 6.");
            return;
        }

        List<Student> filteredStudents = studentService.getAllStudents().stream()
                .filter(s -> s.getDepartmentId() != null && s.getDepartmentId().equals(departmentId))
                .filter(s -> s.getCourse() == course)
                .toList();

        if (filteredStudents.isEmpty()) {
            System.out.println("\n[!] На кафедрі '" + departmentId + "' студентів " + course + "-го курсу не знайдено.");
            return;
        }

        List<Student> sortedById = filteredStudents.stream()
                .sorted(Comparator.comparing(s -> Integer.parseInt(s.getID())))
                .toList();
        renderStudentTable(sortedById, "Студенти " + course + " курсу кафедри " + departmentId + " (СОРТУВАННЯ ЗА ID)");

        List<Student> sortedByName = filteredStudents.stream()
                .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();
        renderStudentTable(sortedByName, "Студенти " + course + " курсу кафедри " + departmentId + " (ЗА АЛФАВІТОМ)");
    }

    public void printAllTeachersAlphabetically() {
        List<Teacher> teachers = teacherService.getAllTeachers().stream()
                .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();

        if (teachers.isEmpty()) {
            System.out.println("Список викладачів порожній.");
        } else {
            renderTeacherTable(teachers, "Повний звіт викладачів (за алфавітом)");
        }
    }

    public void printTeachersByFacultyAlphabetically(String facultyId) {
        List<Teacher> teachers = teacherService.getAllTeachers().stream()
                .filter(t -> t.getFacultyId() != null && t.getFacultyId().equals(facultyId))
                .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();

        if (teachers.isEmpty()) {
            System.out.println("На факультеті з ID '" + facultyId + "' викладачів не знайдено.");
        } else {
            renderTeacherTable(teachers, "Викладачі факультету: " + facultyId);
        }
    }

    public void printTeachersByDepartmentAlphabetically(String departmentId) {
        List<Teacher> teachers = teacherService.getAllTeachers().stream()
                .filter(t -> t.getDepartmentId() != null && t.getDepartmentId().equals(departmentId))
                .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();

        if (teachers.isEmpty()) {
            System.out.println("На кафедрі з ID '" + departmentId + "' викладачів не знайдено.");
        } else {
            renderTeacherTable(teachers, "Викладачі кафедри: " + departmentId);
        }
    }
    private String truncate(String text, int length) {
        if (text == null) return "---";
        return (text.length() > length) ? text.substring(0, length - 3) + ".." : text;
    }

    private String translateForm(Student.StudyForm form) {
        if (form == null) return "---";
        return form == Student.StudyForm.BUDGET ? "Бюджет" : "Контракт";
    }

    private String translateStatus(Object status) {
        if (status == null) return "---";
        return switch (status.toString()) {
            case "STUDYING" -> "Навчається";
            case "ACADEMIC_LEAVE" -> "Акадумічна відпустка";
            case "EXPELLED" -> "Відрахований";
            default -> status.toString();
        };
    }

    private void printHeader() {
        System.out.println(SEPARATOR);
        System.out.printf(TABLE_FORMAT,
                "ID", "ПІБ", "Дата Нар.", "Email", "Телефон", "Квиток", "Курс", "Група", "Вступ", "Форма", "Статус", "Факультет", "Спеціальність", "Кафедра");
        System.out.println(SEPARATOR);
    }

    private void printStudentRow(Student s) {
        System.out.printf(TABLE_FORMAT,
                s.getID(),
                s.getFullName(),
                s.getBirthDate().format(DATE_FORMATTER),
                s.getEmail(),
                s.getPhone(),
                s.getStudentId(),
                s.getCourse(),
                s.getGroup(),
                s.getEnrollmentYear(),
                translateForm(s.getForm()),
                translateStatus(s.getStudentStatus()),
                truncate(s.getFacultyId() != null ? facultyService.getFaculty(s.getFacultyId()).getName() : "---", 30),
                truncate(s.getSpecialty() != null ? s.getSpecialty() : "---", 30),
                truncate(s.getDepartmentId() != null ? departmentService.getDepartment(s.getDepartmentId()).getName(): "---", 30)
        );
    }

    private void printTeacherHeader() {
        System.out.println(TEACHER_SEPARATOR);
        System.out.printf(TEACHER_TABLE_FORMAT,
                "ID", "ПІБ", "Дата Нар.", "Email", "Телефон", "Посада", "Ступінь", "Вчене звання", "Прийом", "Ставка", "Факультет", "Кафедра");
        System.out.println(TEACHER_SEPARATOR);
    }

    private void printTeacherRow(Teacher t) {
        System.out.printf(TEACHER_TABLE_FORMAT,
                t.getID(),
                truncate(t.getFullName(), 40),
                t.getBirthDate().format(DATE_FORMATTER),
                truncate(t.getEmail(), 35),
                t.getPhone(),
                truncate(t.getPosition(), 20),
                truncate(t.getDegree(), 15),
                truncate(t.getTitle(), 15),
                t.getHireDate().format(DATE_FORMATTER),
                String.format("%.2f", t.getRate()),
                truncate(t.getFacultyId() != null ? facultyService.getFaculty(t.getFacultyId()).getName() : "---", 25),
                truncate(t.getDepartmentId() != null ? departmentService.getDepartment(t.getDepartmentId()).getName() : "---", 25)
        );
    }
}

