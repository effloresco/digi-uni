package university.service;

import university.domain.*;
import university.network.Client;

import java.util.*;

public class ReportService {
    private final RemoteStudentService studentService;
    private final RemoteTeacherService teacherService;

    public ReportService(Client client) {
        studentService = new RemoteStudentService(client);
        teacherService = new RemoteTeacherService(client);
    }

    public void printAllStudentsAlphabetically() {
        List<Student> students = studentService.getAllStudents().stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .sorted(Comparator.comparing(Person::getFullName))
                .toList();

        if (students.isEmpty()) {
            System.out.println("Список студентів порожній.");
        } else {
            System.out.println("\n--- Список студентів за алфавітом ---");
            students.forEach(System.out::println);
        }
    }

    public void printAllStudentsByCourse() {
        List<Student> students = studentService.getAllStudents().stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .sorted(Comparator.comparing(Student::getCourse).thenComparing(Person::getFullName))
                .toList();

        if (students.isEmpty()) {
            System.out.println("Список студентів порожній.");
        } else {
            System.out.println("\n--- Список студентів за курсами ---");
            students.forEach(System.out::println);
        }
    }

    public void printStudentsByFacultyAlphabetically(String facultyId) {
        List<Student> students = studentService.getAllStudents().stream()
                .filter(s -> s.getFacultyId() != null && s.getFacultyId().equals(facultyId))
                .sorted(Comparator.comparing(Person::getFullName))
                .toList();

        if (students.isEmpty()) {
            System.out.println("На факультеті з ID '" + facultyId + "' студентів не знайдено.");
        } else {
            System.out.println("\n--- Студенти факультету (" + facultyId + ") за алфавітом ---");
            students.forEach(System.out::println);
        }
    }

    public void printStudentsByDepartmentByCourse(String departmentId) {
        List<Student> students = studentService.getAllStudents().stream()
                .filter(s -> s.getDepartmentId() != null && s.getDepartmentId().equals(departmentId))
                .sorted(Comparator.comparing(Student::getCourse)
                        .thenComparing(Person::getFullName))
                .toList();

        if (students.isEmpty()) {
            System.out.println("На кафедрі з ID '" + departmentId + "' студентів не знайдено.");
        } else {
            System.out.println("\n--- Студенти кафедри (ID: " + departmentId + "), впорядковані за курсами ---");
            students.forEach(s -> System.out.println(
                    "Курс " + s.getCourse() + " | " + s.getFullName() + " (ID: " + s.getStudentId() + ")"
            ));
        }
    }

    public void printStudentsByDepartmentAlphabetically(String departmentId) {
        List<Student> students = studentService.getAllStudents().stream()
                .filter(s -> s.getDepartmentId() != null && s.getDepartmentId().equals(departmentId))
                .sorted(Comparator.comparing(Person::getFullName))
                .toList();

        if (students.isEmpty()) {
            System.out.println("На кафедрі з ID '" + departmentId + "' студентів не знайдено.");
        } else {
            System.out.println("\n--- Студенти кафедри (" + departmentId + ") за алфавітом ---");
            students.forEach(System.out::println);
        }
    }

    public void printStudentsByDeptAndCourse(String departmentId, int course) {
        if (course < 1 || course > 6) {
            System.out.println("Помилка: Курс має бути від 1 до 6.");
            return;
        }

        List<Student> students = studentService.getAllStudents().stream()
                .filter(s -> s.getDepartmentId() != null && s.getDepartmentId().equals(departmentId))
                .filter(s -> s.getCourse() == course)
                .toList();

        if (students.isEmpty()) {
            System.out.println("На кафедрі '" + departmentId + "' студентів " + course + "-го курсу не знайдено.");
            return;
        }

        System.out.println("\n--- Студенти " + course + " курсу кафедри " + departmentId + " (звичайний список) ---");
        students.forEach(System.out::println);

        System.out.println("\n--- Студенти " + course + " курсу кафедри " + departmentId + " (за алфавітом) ---");
        students.stream()
                .sorted(Comparator.comparing(Person::getFullName))
                .forEach(System.out::println);
    }

// ==============================================================================================================


    public void printAllTeachersAlphabetically() {
        List<Teacher> teachers = teacherService.getAllTeachers().stream()
                .filter(t -> t instanceof Teacher)
                .map(t -> (Teacher) t)
                .sorted(Comparator.comparing(Person::getFullName))
                .toList();

        if (teachers.isEmpty()) {
            System.out.println("Список викладачів порожній.");
        } else {
            System.out.println("\n--- Список викладачів за алфавітом ---");
            teachers.forEach(System.out::println);
        }
    }

    public void printTeachersByFacultyAlphabetically(String facultyId) {
        List<Teacher> teachers = teacherService.getAllTeachers().stream()
                .filter(t -> t.getFacultyId() != null && t.getFacultyId().equals(facultyId))
                .sorted(Comparator.comparing(Person::getFullName))
                .toList();

        if (teachers.isEmpty()) {
            System.out.println("На факультеті з ID '" + facultyId + "' викладачів не знайдено.");
        } else {
            System.out.println("\n--- Викладачі факультету (" + facultyId + ") за алфавітом ---");
            teachers.forEach(System.out::println);
        }
    }

    public void printTeachersByDepartmentAlphabetically(String departmentId) {
        List<Teacher> teachers = teacherService.getAllTeachers().stream()
                .filter(t -> t.getDepartmentId() != null && t.getDepartmentId().equals(departmentId))
                .sorted(Comparator.comparing(Person::getFullName))
                .toList();

        if (teachers.isEmpty()) {
            System.out.println("На кафедрі з ID '" + departmentId + "' викладачів не знайдено.");
        } else {
            System.out.println("\n--- Викладачі кафедри (" + departmentId + ") за алфавітом ---");
            teachers.forEach(System.out::println);
        }
    }
}