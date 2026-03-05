package university.ui;
import static university.service.ReportService.*;
import static university.service.SearchService.*;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ManagementMenu management = new ManagementMenu();

    public static void main(String[] args) {
        MainMenu ui = new MainMenu();
        ui.run();
    }

    public void run() {
        while (true) {
            System.out.println("\n*---DigiUni Registry---*");
            System.out.println("1 - Управління");
            System.out.println("2 - Пошук");
            System.out.println("3 - Звіти");
            System.out.println("0 - Вихід з програми");
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        management.management();
                        break;
                    case 2:
                        search();
                        break;
                    case 3:
                        reports();
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
