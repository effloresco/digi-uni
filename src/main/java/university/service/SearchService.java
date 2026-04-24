package university.service;

import university.domain.*;
import university.network.Client;
import university.repository.StudentRepository;
import university.repository.TeacherRepository;

import java.util.*;

public class SearchService {
    private final RemoteStudentService studentService;
    private final RemoteTeacherService teacherService;
    private final Scanner scanner = new Scanner(System.in);

    public SearchService(Client client) {
        this.studentService = new RemoteStudentService(client);
        this.teacherService = new RemoteTeacherService(client);
    }

    public void searchStudentByFullName() {
        System.out.print("Введіть текст для пошуку (ПІБ): ");
        String query = scanner.nextLine().toLowerCase();

        List<Student> results = studentService.getAllStudents().stream()
                .filter(p -> p.getFullName().toLowerCase().contains(query))
                .toList();

        if (results.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            results.forEach(System.out::println);
        }
    }

    public void searchTeacherByFullName() {
        System.out.print("Введіть текст для пошуку (ПІБ): ");
        String query = scanner.nextLine().toLowerCase();

        List<Teacher> results = teacherService.getAllTeachers().stream()
                .filter(p -> p.getFullName().toLowerCase().contains(query))
                .toList();

        if (results.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            results.forEach(System.out::println);
        }
    }


    public void searchStudentByGroup() {
        System.out.print("Введіть текст для пошуку (Група): ");
        String query = scanner.nextLine().toLowerCase();

        List<Student> results = studentService.getAllStudents().stream()
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

    public void searchStudentByCourse() {
        System.out.print("Введіть текст для пошуку (Курс): ");
        try {
            int query = Integer.parseInt(scanner.nextLine());

            List<Student> results = studentService.getAllStudents().stream()
                    .filter(s -> s.getCourse() == query)
                    .toList();

            if (results.isEmpty()) {
                System.out.println("Нічого не знайдено.");
            } else {
                results.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Курс має бути цифрою!");
        }
    }

}