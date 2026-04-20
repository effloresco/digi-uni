package university.ui;

import university.network.Client;
import university.service.UserService;
import java.util.Scanner;

public class AuthMenu {
    protected final Scanner scanner = new Scanner(System.in);
    private final Client client;

    public AuthMenu(Client client) {
        this.client = client;
    }

    public void auth() {
        boolean status = true;
        System.out.println("\n*-Вхід в DigiUni:-*");

        while (status) {
            System.out.println("Введіть ім'я користувача (для виходу введіть 0)");
            String username = scanner.nextLine();
            if (username.equals("0")) { return; }

            System.out.println("Введіть пароль (для виходу введіть 0)");
            String password = scanner.nextLine();
            if (password.equals("0")) { return; }

            String[] authData = {username, password};
            String response = client.sendRequest("AUTH_USER", authData);
            String[] parts = response.split("\\|", 2);

            if (parts[0].equals("OK")) {
                System.out.println("Успішний вхід! Вітаємо, " + username);
                UserService.currentUser = Integer.parseInt(parts[1]);
                status = false;
            } else {
                System.out.println("Помилка: " + parts[1] + ". Повторіть спробу.");
            }
        }
    }
}