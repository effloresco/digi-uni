package university.ui;

import university.domain.User;
import university.exceptions.UserAlreadyExistsException;
import university.exceptions.UsernameAlreadyUsedException;
import university.network.Client;
import university.repository.UserRepository;
import university.service.UserService;
import university.storage.*;

import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ManagementMenu management;
    private final AuthMenu auth;
    private final ReportsMenu reportsMenu;
    private final SearchMenu searchMenu;
    private final Client client;
    public MainMenu(Client client) {
        this.client = client;
        management = new ManagementMenu(client);
        auth = new AuthMenu(client);
        reportsMenu = new ReportsMenu(client);
        searchMenu = new SearchMenu(client);
    }

    public void run() {
        while (true) {
            while (UserService.currentUser == null) {
                auth.auth();
            }
            System.out.println("\n*---DigiUni Registry---*");
            System.out.println("1 - Вихід з акаунта користувача");
            System.out.println("2 - Пошук");
            System.out.println("3 - Звіти");
            if ((UserService.currentUser & User.PERMISSION_EDIT) != 0)
                System.out.println("4 - Управління");
            System.out.println("0 - Вихід з програми");
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        UserService.currentUser = null;
                        continue;
                    case 2:
                        searchMenu.searchOptions();
                        break;
                    case 3:
                        reportsMenu.reports();
                        break;
                    case 4:
                        if ((UserService.currentUser & User.PERMISSION_EDIT) != 0)
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




}
