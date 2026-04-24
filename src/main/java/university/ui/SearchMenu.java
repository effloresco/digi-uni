package university.ui;

import university.service.Utils;
import university.network.Client;
import university.service.SearchService;

import java.util.List;
import static university.service.Utils.*;
import java.util.Scanner;

public class SearchMenu {
    private final SearchService searchService;

    private final List<String> menuOptions = List.of(
            "[1] Пошук студентів",
            "[2] Пошук викладачів",
            OPT0);
    private final List<String> studentOptions = List.of(
            "[1] Пошук за ПІБ",
            "[2] Пошук за курсом",
            "[3] Пошук за групою",
            OPT0);
    private final List<String> teacherOptions = List.of(
            "[1] Пошук за ПІБ",
            "[2] Пошук за курсом",
            "[3] Пошук за групою",
            OPT0);

    private final Scanner scanner = new Scanner(System.in);

    public SearchMenu(Client client) {
        searchService = new SearchService(client);
    }
    protected void searchOptions() {
        boolean status = true;
        while (status) {
            printMenu("Пошук", menuOptions);
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
                    printMessage(Utils.Mt.Error, "Некоректне значення");
            }
        }
    }

    private void searchStud() {
        boolean status = true;
        while (status) {
            printMenu("Пошук студентів", studentOptions);
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();
            int input = Integer.parseInt(choice);
            switch (input) {
                case 1:
                    searchService.searchStudentByFullName();
                    break;
                case 2:
                    searchService.searchStudentByCourse();
                    break;
                case 3:
                    searchService.searchStudentByGroup();
                    break;
                case 0:
                    status = false;
                    break;
                default:
                    printMessage(Utils.Mt.Error, "Некоректне значення");
            }
        }
    }

    private void searchTeacher() {
        boolean status = true;
        while (status) {
            printMenu("Пошук викладачів", teacherOptions);
            System.out.print("Виберіть опцію: ");
            String choice = scanner.nextLine();
            int input = Integer.parseInt(choice);
            switch (input) {
                case 1:
                    searchService.searchTeacherByFullName();
                    break;
                case 2:
                    searchService.searchStudentByCourse();
                    break;
                case 3:
                    searchService.searchStudentByGroup();
                    break;
                case 0:
                    status = false;
                    break;
                default:
                    printMessage(Utils.Mt.Error, "Некоректне значення");
            }
        }
    }
}
