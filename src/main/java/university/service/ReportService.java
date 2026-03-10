package university.service;
import university.domain.*;
import java.util.*;

import static university.service.SearchService.studentRepository;
import static university.service.SearchService.teacherRepository;

public class ReportService {
    public static void printAllStudentsAlphabetically() {
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

}

