package university.ui;

import java.util.ArrayList;
import java.util.List;

import static university.service.ReportService.*;
import static university.service.SearchService.*;

public class ReportsMenu {

    private List<String> menuOptions = new ArrayList<>();
    private final String opt1g = "1 - Список студентів";
    private final String opt2g = "2 - Список викладачів";
    private final String opt0 = "0 - Вихід";

    private List<String> studentOptions = new ArrayList<>();
    private final String opt1s = "1. За алфавітом";
    private final String opt2s = "2. За курсом";


    private List<String> teacherOptions = new ArrayList<>();
    private final String opt1t = "1. За алфавітом";

    protected void reports() {
        boolean status = true;
        menuOptions.add(opt1g);
        menuOptions.add(opt2g);
        menuOptions.add(opt0);

        while (status) {
            System.out.println("\n*-Списки-*");
            for (String option : menuOptions) {
                System.out.println(option);
            }

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
        studentOptions.add(opt1s);
        studentOptions.add(opt2s);
        studentOptions.add(opt0);
        while (status) {
            System.out.println("\n*-Вивести список студентів-*");
            for (String option : studentOptions) {
                System.out.println(option);
            }

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
        teacherOptions.add(opt1t);
        teacherOptions.add(opt0);
        while (status) {
            System.out.println("\n*-Вивести список викладачів-*");
            for (String option : teacherOptions) {
                System.out.println(option);
            }
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
