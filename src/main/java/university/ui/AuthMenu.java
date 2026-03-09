package university.ui;

import university.domain.User;
import university.repository.UserRepository;
import university.service.UserService;

import java.util.List;
import java.util.Scanner;

public class AuthMenu {
    protected final Scanner scanner = new Scanner(System.in);
    private final UserRepository userRepository = UserRepository.get(UserRepository.class);

    static void main() {
        AuthMenu auth = new AuthMenu();
    }

    public void auth() {
//        UserService userManagement = new UserService();
        boolean status = true;
        boolean success = false;
        User targetUser = null;
        System.out.println("\n*-Вхід в DigiUni:-*");
        while (status) {
            System.out.println("Введіть ім'я користувача (для виходу введіть 0)");
            String username = scanner.nextLine();
            if (username.equals("0")) {
                status = false;
                continue;
            }
            List<User> results = userRepository.findAll().stream()
                    .filter(p -> p.getUserName().equals(username))
                    .toList();

            if (results.isEmpty()) {
                System.out.println("Неправильне ім'я користувача. Повторіть спробу");
            } else {
                targetUser = results.get(0);
                System.out.println("Введіть пароль (для виходу введіть 0)");
                String password = scanner.nextLine();
                if (password.equals("0")) {
                    status = false;
                    continue;
                }
                if (!targetUser.getPassword().equals(password)) {
                    System.out.println("Неправильний пароль. Повторіть спробу");
                    continue;
                }

                System.out.println("Успішний вхід! Вітаємо, " + targetUser.getUserName());
                UserService.currentUser = targetUser.getRole();
                status = false;
                success = true;
            }
        }
        status = true;
        while(status && !success){
            System.out.println("Вийти з програми? y/n");
            String answer = scanner.nextLine();
            if (answer.equals("y"))
                System.exit(0);
            else if (answer.equals("n"))
                status = false;
        }
    }
}
