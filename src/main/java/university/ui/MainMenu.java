package university.ui;

import university.domain.User;
import university.network.Client;
import university.service.UserService;
import university.storage.*;

import java.util.List;
import java.util.Scanner;

import static university.service.Utils.*;

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
    private final List<String> menuOptions = List.of(
            "[1] Вихід з акаунта користувача",
            "[2] Пошук",
            "[3] Звіти",
            "[4] Управління",
            OPT0);
    private final int PERMISSION_EDIT_INDEX = 3;


    public void run() {
        List<String> options;
        while (true) {
            while (UserService.currentUser == null) {
                auth.auth();
            }

            if ((UserService.currentUser & User.PERMISSION_EDIT) != 0)
                options = menuOptions;
            else
                options = menuOptions.stream()
                        .filter(opt -> menuOptions.indexOf(opt) != PERMISSION_EDIT_INDEX)
                        .toList();
            printMenu("DigiUni Registry", options);
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