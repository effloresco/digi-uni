package university.service;
import university.domain.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static university.service.SearchService.studentRepository;
import static university.service.SearchService.teacherRepository;

public class ReportService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String TEACHER_TABLE_FORMAT = "| %-3s | %-40s | %-10s | %-35s | %-12s | %-25s | %-30s | %-30s | %-10s | %-10s | %-30s | %-30s |%n";
    private static final String TEACHER_SEPARATOR = "+" + "-".repeat(5) + "+" + "-".repeat(42) + "+" + "-".repeat(12) + "+" +
            "-".repeat(37) + "+" + "-".repeat(14) + "+" + "-".repeat(27) + "+" +
            "-".repeat(32) + "+" + "-".repeat(32) + "+" + "-".repeat(12) + "+" +
            "-".repeat(12) + "+" + "-".repeat(32) + "+" + "-".repeat(32) + "+";

    private static final String TABLE_FORMAT = "| %-3s | %-50s | %-10s | %-40s | %-12s | %-20s | %-4s | %-15s | %-5s | %-15s | %-15s | %-30s | %-30s | %-30s |%n";

    private static final String SEPARATOR = "+" + "-".repeat(5) + "+" + "-".repeat(52) + "+" + "-".repeat(12) + "+" +
            "-".repeat(42) + "+" + "-".repeat(14) + "+" + "-".repeat(22) + "+" +
            "-".repeat(6) + "+" + "-".repeat(17) + "+" + "-".repeat(7) + "+" +
            "-".repeat(17) + "+" + "-".repeat(17) + "+" + "-".repeat(32) + "+" +
            "-".repeat(32) + "+" + "-".repeat(32) + "+";

    private static void renderStudentTable(List<Student> students, String reportName) {
        if (students.isEmpty()) {
            System.out.println("\n[!] За вказаними критеріями студентів не знайдено.");
            return;
        }

        System.out.println("\n" + " ".repeat(60) + ">>> " + reportName.toUpperCase() + " <<<");
        printHeader();
        students.forEach(ReportService::printStudentRow);
        System.out.println(SEPARATOR);
        System.out.println("Загальна кількість: " + students.size());
    }

    private static void renderTeacherTable(List<Teacher> teachers, String reportName) {
        if (teachers.isEmpty()) {
            System.out.println("\n[!] За вказаними критеріями викладачів не знайдено.");
            return;
        }
        System.out.println("\n" + " ".repeat(60) + ">>> " + reportName.toUpperCase() + " <<<");
        printTeacherHeader();
        teachers.forEach(ReportService::printTeacherRow);
        System.out.println(TEACHER_SEPARATOR);
        System.out.println("Загальна кількість викладачів: " + teachers.size());
    }

    public static void printAllStudentsAlphabetically() {
        List<Student> students = studentRepository.findAll().stream()
                .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();
        renderStudentTable(students, "Повний звіт студентів (за алфавітом)");
    }

    public static void printAllStudentsByCourse() {
        List<Student> students = studentRepository.findAll().stream()
                .sorted(Comparator.comparing(Student::getCourse).thenComparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();
        renderStudentTable(students, "Список студентів за курсами");
    }

    public static void printStudentsByFacultyAlphabetically(String facultyId) {
        List<Student> students = studentRepository.findAll().stream()
                .filter(s -> s.getFacultyId() != null && s.getFacultyId().equals(facultyId))
                .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();
        renderStudentTable(students, "Студенти факультету: " + facultyId);
    }

    public static void printStudentsByDepartmentByCourse(String departmentId) {
        List<Student> students = studentRepository.findAll().stream()
                .filter(s -> s.getDepartmentId() != null && s.getDepartmentId().equals(departmentId))
                .sorted(Comparator.comparing(Student::getCourse).thenComparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();
        renderStudentTable(students, "Студенти кафедри " + departmentId + " (за курсами)");
    }

    public static void printStudentsByDepartmentAlphabetically(String departmentId) {
        List<Student> students = studentRepository.findAll().stream()
                .filter(s -> s.getDepartmentId() != null && s.getDepartmentId().equals(departmentId))
                .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();
        renderStudentTable(students, "Студенти кафедри " + departmentId + " (алфавіт)");
    }

    public static void printStudentsByDeptAndCourse(String departmentId, int course) {
        if (course < 1 || course > 6) {
            System.out.println("Помилка: Курс має бути від 1 до 6.");
            return;
        }

        List<Student> filteredStudents = studentRepository.findAll().stream()
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

    public static void printAllTeachersAlphabetically() {
        List<Teacher> teachers = teacherRepository.findAll().stream()
                .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();
        renderTeacherTable(teachers, "Повний звіт викладачів (за алфавітом)");
    }

    public static void printTeachersByFacultyAlphabetically(String facultyId) {
        List<Teacher> teachers = teacherRepository.findAll().stream()
                .filter(t -> t.getFacultyId() != null && t.getFacultyId().equals(facultyId))
                .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();
        renderTeacherTable(teachers, "Викладачі факультету: " + facultyId);
    }

    public static void printTeachersByDepartmentAlphabetically(String departmentId) {
        List<Teacher> teachers = teacherRepository.findAll().stream()
                .filter(t -> t.getDepartmentId() != null && t.getDepartmentId().equals(departmentId))
                .sorted(Comparator.comparing(Person::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList();
        renderTeacherTable(teachers, "Викладачі кафедри: " + departmentId);
    }

    private static String truncate(String text, int length) {
        if (text == null) return "---";
        return (text.length() > length) ? text.substring(0, length - 3) + ".." : text;
    }

    private static String translateForm(Student.StudyForm form) {
        if (form == null) return "---";
        return form == Student.StudyForm.BUDGET ? "Бюджет" : "Контракт";
    }

    private static String translateStatus(Object status) {
        if (status == null) return "---";
        return switch (status.toString()) {
            case "STUDYING" -> "Навчається";
            case "ACADEMIC_LEAVE" -> "Акадумічна відпустка";
            case "EXPELLED" -> "Відрахований";
            default -> status.toString();
        };
    }

    private static void printHeader() {
        System.out.println(SEPARATOR);
        System.out.printf(TABLE_FORMAT,
                "ID", "ПІБ", "Дата Нар.", "Email", "Телефон", "Квиток", "Курс", "Група", "Вступ", "Форма", "Статус", "Факультет", "Спеціальність", "Кафедра");
        System.out.println(SEPARATOR);
    }

    private static void printStudentRow(Student s) {
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
                truncate(s.getFacultyId() != null ? s.getFacultyName() : "---", 30),
                truncate(s.getSpecialty() != null ? s.getSpecialty() : "---", 30),
                truncate(s.getDepartmentId() != null ? s.getDepartmentName(): "---", 30)
        );
    }

    private static void printTeacherHeader() {
        System.out.println(TEACHER_SEPARATOR);
        System.out.printf(TEACHER_TABLE_FORMAT,
                "ID", "ПІБ", "Дата Нар.", "Email", "Телефон", "Посада", "Ступінь", "Вчене звання", "Прийом", "Ставка", "Факультет", "Кафедра");
        System.out.println(TEACHER_SEPARATOR);
    }

    private static void printTeacherRow(Teacher t) {
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
                truncate(t.getFacultyId() != null ? t.getFacultyName() : "---", 25),
                truncate(t.getDepartmentId() != null ? t.getDepartmentName() : "---", 25)
        );
    }

}

