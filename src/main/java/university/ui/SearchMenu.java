package university.ui;

import university.service.Utils;
import java.util.List;
import static university.service.SearchService.*;
import static university.service.SearchService.searchStudentByGroup;
import static university.service.Utils.*;

public class SearchMenu {
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

    protected void searchOptions() {
        boolean status = true;
        while (status) {
            printMenu("Пошук", menuOptions);
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
                    printMessage(Utils.Mt.Error, "Некоректне значення");
            }
        }
    }

    private void searchTeacher() {
        boolean status = true;
        while (status) {
            printMenu("Пошук викладачів", teacherOptions);
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
                    printMessage(Utils.Mt.Error, "Некоректне значення");
            }
        }
    }
}
