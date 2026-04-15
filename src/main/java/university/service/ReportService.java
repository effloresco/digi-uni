package university.service;
import university.domain.*;
import java.util.*;

import static university.service.SearchService.studentRepository;
import static university.service.SearchService.teacherRepository;

public class ReportService {public static void printAllStudentsAlphabetically() {
    List<Student> students = studentRepository.findAll().stream()
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

    public static void printAllStudentsByCourse() {
        List<Student> students = studentRepository.findAll().stream()
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

    public static void printStudentsByFacultyAlphabetically(String facultyId) {
        List<Student> students = studentRepository.findAll().stream()
                .filter(s -> s.getFaculty() != null && s.getFaculty().getID().equals(facultyId))
                .sorted(Comparator.comparing(Person::getFullName))
                .toList();

        if (students.isEmpty()) {
            System.out.println("На факультеті з ID '" + facultyId + "' студентів не знайдено.");
        } else {
            System.out.println("\n--- Студенти факультету (" + facultyId + ") за алфавітом ---");
            students.forEach(System.out::println);
        }
    }

    public static void printStudentsByDepartmentByCourse(String departmentId) {
        List<Student> students = studentRepository.findAll().stream()
                .filter(s -> s.getDepartment() != null && s.getDepartment().getID().equals(departmentId))
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

    public static void printStudentsByDepartmentAlphabetically(String departmentId) {
        List<Student> students = studentRepository.findAll().stream()
                .filter(s -> s.getDepartment() != null && s.getDepartment().getID().equals(departmentId))
                .sorted(Comparator.comparing(Person::getFullName))
                .toList();

        if (students.isEmpty()) {
            System.out.println("На кафедрі з ID '" + departmentId + "' студентів не знайдено.");
        } else {
            System.out.println("\n--- Студенти кафедри (" + departmentId + ") за алфавітом ---");
            students.forEach(System.out::println);
        }
    }

    public static void printStudentsByDeptAndCourse(String departmentId, int course) {
        if (course < 1 || course > 6) {
            System.out.println("Помилка: Курс має бути від 1 до 6.");
            return;
        }

        List<Student> students = studentRepository.findAll().stream()
                .filter(s -> s.getDepartment() != null && s.getDepartment().getID().equals(departmentId))
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



    public static void printAllTeachersAlphabetically() {
        List<Teacher> teachers = teacherRepository.findAll().stream()
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

    public static void printTeachersByFacultyAlphabetically(String facultyId) {
        List<Teacher> teachers = teacherRepository.findAll().stream()
                .filter(t -> t.getFaculty() != null && t.getFaculty().getID().equals(facultyId))
                .sorted(Comparator.comparing(Person::getFullName))
                .toList();

        if (teachers.isEmpty()) {
            System.out.println("На факультеті з ID '" + facultyId + "' викладачів не знайдено.");
        } else {
            System.out.println("\n--- Викладачі факультету (" + facultyId + ") за алфавітом ---");
            teachers.forEach(System.out::println);
        }
    }

    public static void printTeachersByDepartmentAlphabetically(String departmentId) {
        List<Teacher> teachers = teacherRepository.findAll().stream()
                .filter(t -> t.getDepartment() != null && t.getDepartment().getID().equals(departmentId))
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

