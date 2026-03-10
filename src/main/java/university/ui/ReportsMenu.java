package university.ui;

import static university.service.ReportService.*;
import static university.service.SearchService.*;

public class ReportsMenu {

    protected void reports() {
        while (true) {
            System.out.println("\n*-Списки-*");
            System.out.println("1. Список студентів");
            System.out.println("2. Список викладачів");
            System.out.println("0. Вихід");
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
                    break;
                default:
                    System.out.println("Введіть коректне значення");
            }
        }
    }

    private void reportStud() {
        while (true) {
            System.out.println("\n*-Вивести список студентів-*");
            System.out.println("1. За алфавітом");
            System.out.println("2. За курсом");
            System.out.println("0. Вихід");
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
                    break;
                default:
                    System.out.println("Введіть коректне значення");
            }
        }
    }

    private void reportTeacher() {
        while (true) {
            System.out.println("\n*-Вивести список викладачів-*");
            System.out.println("1. За алфавітом");
            System.out.println("0. Вихід");
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();

            int input = Integer.parseInt(choice);
            switch (input) {
                case 1:
                    printAllTeachersAlphabetically();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Введіть коректне значення");
            }
        }
    }
}
