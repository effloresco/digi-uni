package university.service;
import university.domain.*;
import java.util.*;

import static university.service.SearchService.studentDatabase;

public class ReportService {
    public static void printAllStudentsAlphabetically() {
        List<Student> students = studentDatabase.findAll().stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .sorted(Comparator.comparing(Person::getFullName))
                .toList();

        if (students.isEmpty()) {
            System.out.println("Список студентів порожній.");
        } else {
            System.out.println("\n--- Список студентів за алфавітом ---");
            students.forEach(s -> System.out.printf("%-30s | %-10s | %s%n",
                    s.getFullName(), s.getID(), s.getEmail()));
        }
    }

    public static void printAllStudentsByCourse() {
        List<Student> students = studentDatabase.findAll().stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .sorted(Comparator.comparing(Student::getCourse).thenComparing(Person::getFullName))
                .toList();

        if (students.isEmpty()) {
            System.out.println("Список студентів порожній.");
        } else {
            System.out.println("\n--- Список студентів за курсами ---");
            students.forEach(s -> System.out.printf("%s | %-30s | %-10s | %s%n",
                    s.getCourse() + " курс", s.getFullName(), s.getID(), s.getEmail()));
        }
    }


}

