package university.ui;

import static university.service.SearchService.*;
import static university.service.SearchService.searchStudentByGroup;

public class SearchMenu {

    protected void searchOptions() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Пошук-*");
            System.out.println("1. Пошук студентів");
            System.out.println("2. Пошук викладачів");
            System.out.println("0. Вихід");
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
        while (status) {
            System.out.println("\n*-Пошук студентів-*");
            System.out.println("1. Пошук за ПІБ");
            System.out.println("2. Пошук за курсом");
            System.out.println("3. Пошук за групою");
            System.out.println("0. Вихід");
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
        while (status) {
            System.out.println("\n*-Пошук викладачів-*");
            System.out.println("1. Пошук за ПІБ");
            System.out.println("2. Пошук за курсом");
            System.out.println("3. Пошук за групою");
            System.out.println("0. Вихід");
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
