package university.ui;

import university.network.Client;
import university.service.UserService;
import java.util.Scanner;

import static university.service.Utils.*;

public class AuthMenu {
    protected final Scanner scanner = new Scanner(System.in);
    private final Client client;

    public AuthMenu(Client client) {
        this.client = client;
    }

    public void auth() {
        boolean status = true;
        boolean success = false;
        printHeader("Вхід в DigiUni");
        while (status) {
            printMessage(Mt.Warning, "Введіть ім'я користувача", "[0] Вихід");
            printPrompt(">");
            String username = scanner.nextLine();
            if (username.equals("0")) { return; }

            printMessage(Mt.Warning, "Введіть пароль","[0] Вихід", true);
            printPrompt(">");
            String password = scanner.nextLine();
            if (password.equals("0")) { return; }

            String[] authData = {username, password};
            String response = client.sendRequest("AUTH_USER", authData);
            String[] parts = response.split("\\|", 2);

            if (parts[0].equals("OK")) {
                printMessage(Mt.Success, "Успішний вхід! Вітаємо, " + username);
                UserService.currentUser = Integer.parseInt(parts[1]);
                status = false;
                success = true;
            } else {
                printMessage(Mt.Error, "Помилка: " + parts[1] + ". Повторіть спробу.");
            }
        }
        status = true;
        while(status && !success){
            printMessage(Mt.Warning, "Вийти з програми?", "[1] Так \n[0] Ні");
            String answer = scanner.nextLine();
            if (answer.equals("1"))
                System.exit(0);
            else if (answer.equals("0"))
                status = false;
        }
    }
}