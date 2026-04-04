package university.ui;

import java.util.List;

import static university.service.ReportService.*;
import static university.service.SearchService.*;

public class ReportsMenu {

    private final String opt0 = "0 - Вихід";

    private final List<String> menuOptions = List.of("1 - Список студентів", "2 - Список викладачів", opt0

    );

    private final List<String> studentOptions = List.of("1. За алфавітом", "2. За курсом", opt0);

    private final List<String> teacherOptions = List.of("1. За алфавітом", opt0);


    protected void reports() {
        boolean status = true;

        while (status) {
            System.out.println("\n*-Списки-*");
            menuOptions.forEach(System.out::println);

            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();

            int input = Integer.parseInt(choice);
            switch (input) {
                case 1:
                    reportStud();
                    break;
                case 2:
                    reportTeacher();
                    break;
                case 0:
                    status = false;
                    break;
                default:
                    System.out.println("Введіть коректне значення");
            }
        }
    }

    private void reportStud() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Вивести список студентів-*");
            studentOptions.forEach(System.out::println);

            System.out.print("Виберіть опцію: ");


            String choice = scanner.nextLine();
            int input = Integer.parseInt(choice);
            switch (input) {
                case 1:
                    printAllStudentsAlphabetically();
                    break;
                case 2:
                    printAllStudentsByCourse();
                    break;
                case 0:
                    status = false;
                    break;
                default:
                    System.out.println("Введіть коректне значення");
            }
        }
    }

    private void reportTeacher() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Вивести список викладачів-*");
            teacherOptions.forEach(System.out::println);
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();

            int input = Integer.parseInt(choice);
            switch (input) {
                case 1:
                    printAllTeachersAlphabetically();
                    break;
                case 0:
                    status = false;
                    break;
                default:
                    System.out.println("Введіть коректне значення");
            }
        }
    }
}
