package university.ui;
import university.domain.User;
import university.service.UserService;

import static university.service.ReportService.*;
import static university.service.SearchService.*;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ManagementMenu management = new ManagementMenu();
    private final AuthMenu auth = new AuthMenu();

    public static void main(String[] args) {
        MainMenu ui = new MainMenu();
        ui.run();
    }

    public void run() {
        while (true) {
            while(UserService.currentUser == null){
                auth.auth();
            }
            System.out.println("\n*---DigiUni Registry---*");
            System.out.println("1 - Пошук");
            System.out.println("2 - Звіти");
            if (UserService.currentUser == User.UserRole.MANAGER || UserService.currentUser == User.UserRole.ADMIN)
                System.out.println("3 - Управління");
            System.out.println("0 - Вихід з програми");
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {

                    case 1:
                        search();
                        break;
                    case 2:
                        reports();
                        break;
                    case 3:
                        if (UserService.currentUser == User.UserRole.MANAGER || UserService.currentUser == User.UserRole.ADMIN)
                            management.management();
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Введіть коректне значення");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
    }

    private void search() {
        while (true) {
            System.out.println("\n*-Пошук студентів-*");
            System.out.println("1. Пошук за ПІБ");
            System.out.println("2. Пошук за курсом");
            System.out.println("3. Пошук за групою");
            System.out.println("0. Вихід");
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> searchStudentByFullName();
                case "2" -> searchByCourse();
                case "3" -> searchByGroup();
                case "0" -> System.exit(0);
                default -> System.out.println("Невірний вибір!");
            }
        }
    }

    private void reports() {
        while (true) {
            System.out.println("\n*-Списки студентів-*");
            System.out.println("1. За алфавітом");
            System.out.println("2. За курсом");
            System.out.println("0. Вихід");
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> printAllStudentsAlphabetically();
                case "2" -> printAllStudentsByCourse();
                case "0" -> System.exit(0);
                default -> System.out.println("Невірний вибір!");
            }
        }
    }

}
