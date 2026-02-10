package university.service;

import java.time.LocalDate;

import university.domain.*;
import university.repository.PersonRepository;
import university.repository.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class SearchService {
    //    public static List<Person> database = new ArrayList<>();
//    public static List<Student> studentDatabase = new ArrayList<>();
    public static PersonRepository studentDatabase = new PersonRepository();

    public SearchService(PersonRepository repository) {
        this.studentDatabase = repository;
    }

    public static Scanner scanner = new Scanner(System.in);

    public static void searchStudentByFullName() {
        System.out.print("Введіть текст для пошуку (ПІБ): ");
        String query = scanner.nextLine().toLowerCase();

        List<Person> results = studentDatabase.findAll().stream()
                .filter(p -> p.getFullName().toLowerCase().contains(query))
                .toList();

        if (results.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            results.forEach(p -> System.out.println(String.format("%-30s", p.getFullName()) + " | " + p.getID() +
                    " | " + String.format("%-30s", p.getEmail()) + " | " + p.getBirthDate()));
            ;
        }
    }


    public static void searchByGroup() {
        System.out.print("Введіть текст для пошуку (Група): ");
        String query = scanner.nextLine().toLowerCase();

        List<Student> results = studentDatabase.findAll().stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .filter(s -> s.getGroup().toLowerCase().contains(query))
                .toList();

        if (results.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            results.forEach(p -> System.out.println("Група "+String.format("%-30s", p.getGroup()) + " | " +
                    String.format("%-30s", p.getFullName()) + " | " + p.getID() +
                    " | " + String.format("%-30s", p.getEmail()) + " | " + p.getBirthDate()));
            ;
        }
    }


    public static void searchByCourse() {
        System.out.print("Введіть текст для пошуку (Курс): ");
        int query = scanner.nextInt();

        List<Student> results = studentDatabase.findAll().stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student) p)
                .filter(s -> s.getCourse() == query)
                .toList();

        if (results.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            results.forEach(p -> System.out.println(p.getCourse()+" курс" + " | " + String.format("%-30s", p.getFullName()) + " | " + p.getID() +
                    " | " + String.format("%-30s", p.getEmail()) + " | " + p.getBirthDate()));
            ;
        }
    }

}
