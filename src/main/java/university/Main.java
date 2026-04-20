package university;

import university.network.Client;
import university.network.Server;
import university.ui.MainMenu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Оберіть режим запуску:");
        System.out.println("1 - Запустити Сервер (Центральна база даних)");
        System.out.println("2 - Запустити Клієнт (UI консоль)");

        int choice = scanner.nextInt();

        if (choice == 1) {
            // Запускаємо TCP-сервер, який слухає порт
            Server server = new Server();
            server.start(5555);
        } else {
            Client client = new Client("localhost", 5555);
            MainMenu menu = new MainMenu(client);
            menu.run();
        }
    }
}