package university.ui;

import university.domain.User;
import university.network.Client;
import university.repository.UserRepository;
import university.service.RemoteStudentService;
import university.service.RemoteUserService;
import university.service.UserService;

import java.util.List;
import java.util.Optional;

import static university.service.SearchService.scanner;

public class UserMenu {
    private final Client client;
    protected final UserRepository userRepository = UserRepository.get(UserRepository.class);
    protected final RemoteUserService userService;

    private final String opt0 = "0 - Вихід";
    private final List<String> roleOptions = List.of("1 - Користувач", "2 - Менеджер", "3 - Адміністратор", opt0);
    private final List<String> menuOptions = List.of("1 - Додати користувача", "2 - Змінити користувача", "3 - Видалити користувача", opt0);

    public UserMenu(Client client) {
        this.client = client;
        userService = new RemoteUserService(client);
    }

    protected void userManagement() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Управління користувачами-*");
            menuOptions.forEach(System.out::println);
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        addUser();
                        break;
                    case 2:
                        changeUser();
                        break;
                    case 3:
                        deleteUser();
                        break;
                    case 0:
                        status = false;
                        break;
                    default:
                        System.out.println("Введіть коректне значення");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
    }

    protected User userGenerator(int id, String allowedUsername) {
        System.out.println("Введіть ім'я користувача (для виходу введіть 0)");
        String username = scanner.nextLine();
        if ("0".equals(username)) return null;
        if (userRepository.checkUsername(username) && !username.equals(allowedUsername)) {
            System.out.println("Це ім'я користувача вже використовується");
            return null;
        }
        System.out.println("Введіть пароль користувача (для виходу введіть 0)");
        String password = scanner.nextLine();
        if ("0".equals(password)) return null;
        Integer role = null;

        while (role == null) {
            System.out.println("Оберіть роль користувача");
            roleOptions.forEach(System.out::println);
            String inputLine = scanner.nextLine();

            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        role = User.PERMISSION_VIEW;
                        break;
                    case 2:
                        role = User.PERMISSION_VIEW | User.PERMISSION_EDIT;
                        break;
                    case 3:
                        role = User.PERMISSION_VIEW | User.PERMISSION_EDIT | User.PERMISSION_MANAGE_USERS;
                        break;
                    case 0:
                        return null;
                    default:
                        System.out.println("Введіть коректне значення");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
        if (id != 0) return new User(id, username, password, role);
        return new User(username, password, role);
    }

    protected void addUser() {
        User user = userGenerator(0, "0");
        if (user == null) return;
        userService.createUser(user);
    }

    protected void deleteUser() {
        while (true) {
            System.out.println("Введіть ідентифікатор користувача, якого треба видалити (введіть 0 щоб повернутись назад)");
            String input = scanner.nextLine();
            if (input.equals("0")) return;
            try {
                int userId = Integer.parseInt(input);
                userService.deleteUser(userId);

            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
    }

    protected void changeUser() {
        boolean found = false;
        boolean exit = false;
        String userId;
        while (!found && !exit) {
            System.out.println("Введіть ідентифікатор користувача, якого треба замінити (введіть 0 щоб повернутись назад)");
            userId = scanner.nextLine();
            try {
                int id = Integer.parseInt(userId);
                if (id == 0) exit = true;
                else {
                    Optional<User> optionalUser = userRepository.findById(id);

                    if (optionalUser.isPresent()) {
                        found = true;
                        User user = userGenerator(id, optionalUser.get().getUserName());
                        if (user == null) return;
                        userService.deleteUser(optionalUser.get());
                        userService.createUser(user);
                    } else System.out.println("Користувача з таким ID не знайдено.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
    }
}
