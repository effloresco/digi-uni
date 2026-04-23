package university.ui;

import university.domain.User;
import university.repository.UserRepository;
import university.service.UserService;
import java.util.List;
import java.util.Scanner;

import static university.service.Utils.*;

public class AuthMenu {
    protected final Scanner scanner = new Scanner(System.in);
    private final UserRepository userRepository = UserRepository.get(UserRepository.class);

    static void main() {
        AuthMenu auth = new AuthMenu();
    }

    public void auth() {
        boolean status = true;
        boolean success = false;
        User targetUser = null;
        printHeader("Вхід в DigiUni");
        while (status) {
            printMessage(Mt.Warning, "Введіть ім'я користувача", "[0] Вихід");
            printPrompt(">");
            String username = scanner.nextLine();
            if (username.equals("0")) {
                status = false;
                continue;
            }
            List<User> results = userRepository.findAll().stream()
                    .filter(p -> p.getUserName().equals(username))
                    .toList();

            if (results.isEmpty()) {
                printMessage(Mt.Error, "Неправильне ім'я користувача. Повторіть спробу");
            } else {
                targetUser = results.get(0);
                printMessage(Mt.Warning, "Введіть пароль","[0] Вихід", true);
                printPrompt(">");
                String password = scanner.nextLine();
                if (password.equals("0")) {
                    status = false;
                    continue;
                }
                if (!targetUser.getPassword().equals(password)) {
                    printMessage(Mt.Error, "Неправильний пароль. Повторіть спробу");
                    continue;
                }

                printMessage(Mt.Success, "Успішний вхід! Вітаємо, " + targetUser.getUserName(), "",true);

                UserService.currentUser = targetUser.getRole();
                status = false;
                success = true;
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
