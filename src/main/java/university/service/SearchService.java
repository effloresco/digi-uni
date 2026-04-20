package university.service;

import university.domain.*;
import university.repository.StudentRepository;
import university.repository.TeacherRepository;

import java.util.*;

public class SearchService {
    public static StudentRepository studentRepository = StudentRepository.get(StudentRepository.class);
    public static TeacherRepository teacherRepository = TeacherRepository.get(TeacherRepository.class);
    public static Scanner scanner = new Scanner(System.in);

    public static void searchStudentByFullName() {
        System.out.print("Введіть текст для пошуку (ПІБ): ");
        String query = scanner.nextLine().toLowerCase();

        List<Student> results = studentRepository.findAll().stream()
                .filter(p -> p.getFullName().toLowerCase().contains(query))
                .toList();

        if (results.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            results.forEach(System.out::println);
        }
    }

    public static void searchTeacherByFullName() {
        System.out.print("Введіть текст для пошуку (ПІБ): ");
        String query = scanner.nextLine().toLowerCase();

        List<Teacher> results = teacherRepository.findAll().stream()
                .filter(p -> p.getFullName().toLowerCase().contains(query))
                .toList();

        if (results.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            results.forEach(System.out::println);
        }
    }




    public static void searchStudentByGroup() {
        System.out.print("Введіть текст для пошуку (Група): ");
        String query = scanner.nextLine().toLowerCase();

        List<Student> results = studentRepository.findAll().stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .filter(s -> s.getGroup().toLowerCase().contains(query))
                .toList();

        if (results.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            results.forEach(System.out::println);
        }
    }

    public static void searchStudentByCourse() {
        System.out.print("Введіть текст для пошуку (Курс): ");
        int query = scanner.nextInt();
        scanner.nextLine();

        List<Student> results = studentRepository.findAll().stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .filter(s -> s.getCourse() == query)
                .toList();

        if (results.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            results.forEach(System.out::println);
        }
    }

}
