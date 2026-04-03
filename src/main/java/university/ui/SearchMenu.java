package university.ui;

import java.util.ArrayList;
import java.util.List;

import static university.service.SearchService.*;
import static university.service.SearchService.searchStudentByGroup;

public class SearchMenu {

    private List<String> menuOptions = new ArrayList<>();
    private final String opt1g = "1 - Пошук студентів";
    private final String opt2g = "2 - Пошук викладачів";
    private final String opt0 = "0 - Вихід";

    private List<String> studentOptions = new ArrayList<>();
    private final String opt1s = "1. Пошук за ПІБ";
    private final String opt2s = "2. Пошук за курсом";
    private final String opt3s = "3. Пошук за групою";


    private List<String> teacherOptions = new ArrayList<>();
    private final String opt1t = "1. Пошук за ПІБ";
    private final String opt2t = "2. Пошук за курсом";
    private final String opt3t = "3. Пошук за групою";

    protected void searchOptions() {
        boolean status = true;
        menuOptions.add(opt1g);
        menuOptions.add(opt2g);
        menuOptions.add(opt0);
        while (status) {
            System.out.println("\n*-Пошук-*");
            for (String option : menuOptions) {
                System.out.println(option);
            }
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();
            int input = Integer.parseInt(choice);
            switch (input) {
                case 1:
                    searchStud();
                    break;
                case 2:
                    searchTeacher();
                    break;
                case 0:
                    status = false;
                    break;
                default:
                    System.out.println("Введіть коректне значення");
            }

        }
    }


    private void searchStud() {
        boolean status = true;
        studentOptions.add(opt1s);
        studentOptions.add(opt2s);
        studentOptions.add(opt3s);
        studentOptions.add(opt0);
        while (status) {
            System.out.println("\n*-Пошук студентів-*");
            for (String option : studentOptions) {
                System.out.println(option);
            }

            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();

            int input = Integer.parseInt(choice);
            switch (input) {
                case 1:
                    searchStudentByFullName();
                    break;
                case 2:
                    searchStudentByCourse();
                    break;
                case 3:
                    searchStudentByGroup();
                    break;
                case 0:
                    status = false;
                    break;
                default:
                    System.out.println("Введіть коректне значення");
            }
        }
    }

    private void searchTeacher() {
        boolean status = true;
        teacherOptions.add(opt1t);
        teacherOptions.add(opt2t);
        teacherOptions.add(opt3t);
        teacherOptions.add(opt0);
        while (status) {
            System.out.println("\n*-Пошук викладачів-*");
            for (String option : teacherOptions) {
                System.out.println(option);
            }
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();

            int input = Integer.parseInt(choice);
            switch (input) {
                case 1:
                    searchTeacherByFullName();
                    break;
                case 2:
                    searchStudentByCourse();
                    break;
                case 3:
                    searchStudentByGroup();
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
