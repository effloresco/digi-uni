
package university.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// Твої існуючі імпорти з проєкту DigiUni:
import university.domain.Student;
import university.domain.User;
import university.exceptions.UniversityBaseException;
import university.exceptions.UserAlreadyExistsException;
import university.exceptions.UsernameAlreadyUsedException;
import university.repository.*;
import university.service.*;
import university.storage.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class Server {


    private final StudentRepository studentRepository = new StudentRepository();
    private static final UserRepository userRepository = UserRepository.get(UserRepository.class);

    private final StudentService studentService = new StudentService(studentRepository);
    private static final UserService userService = new UserService(userRepository);

    private static final StudentStorageManager studentStorageManager = new StudentStorageManager();
    private static final TeacherStorageManager teacherStorageManager = new TeacherStorageManager();
    private static final DepartmentStorageManager departmentStorageManager = new DepartmentStorageManager();
    private static final FacultyStorageManager facultyStorageManager = new FacultyStorageManager();
    private static final ServiceStorageManager serviceStorageManager = new ServiceStorageManager();
    private static final UserStorageManager userStorageManager = new UserStorageManager();

    private final Gson networkGson;

    public Server() {
        this.networkGson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    // Головний метод запуску сервера
    public void start(int port) {
        System.out.println("Ініціалізація сервера DigiUni...");

        // 2. ЗАВАНТАЖЕННЯ ДАНИХ (Те, що раніше робилося в MainMenu)
        System.out.println("Зчитування локальної бази даних з файлів...");
        studentStorageManager.loadAllData();
        studentStorageManager.loadAllData();
        teacherStorageManager.loadAllData();
        departmentStorageManager.loadAllData();
        facultyStorageManager.loadAllData();
        serviceStorageManager.loadAllData();
        userStorageManager.loadAllData();
        try {
            userService.createUser(new User("admin", "12345678", User.PERMISSION_VIEW | User.PERMISSION_EDIT | User.PERMISSION_MANAGE_USERS));
        } catch(UserAlreadyExistsException | UsernameAlreadyUsedException _){}

        System.out.println("Завантаження збережених даних в пам'ять завершено.");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("=============================================");
            System.out.println("TCP Сервер запущено. Очікування на порту " + port);
            System.out.println("=============================================");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Нове підключення: " + clientSocket.getInetAddress());

                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Помилка роботи сервера: " + e.getMessage());
        }
    }

    private class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    socket;
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))
            ) {
                String request;

                while ((request = in.readLine()) != null) {
                    String response = processRequest(request);

                    out.write(response + "\n");
                    out.flush();
                }
            } catch (IOException e) {
                System.out.println("Клієнт " + socket.getInetAddress() + " відключився.");
            }
        }
    }

    private String processRequest(String request) {
        try {
            String[] parts = request.split("\\|", 2);
            String command = parts[0];
            String payload = parts.length > 1 ? parts[1] : "";

            System.out.println("Обробка команди: " + command);

            switch (command) {
                case "GET_ALL_STUDENTS":
                    List<Student> students = studentRepository.findAll();
                    return "OK|" + networkGson.toJson(students);

                case "ADD_STUDENT":
                    Student newStudent = networkGson.fromJson(payload, Student.class);
                    studentService.createStudent(newStudent);
                    studentStorageManager.saveAllData();
                    return "OK|Студента успішно додано до бази!";

                case "DELETE_STUDENT":
                    Student studentToDelete = networkGson.fromJson(payload, Student.class);
                    studentService.deleteStudent(studentToDelete);
                    studentStorageManager.saveAllData();
                    return "OK|Студента видалено";
                default:
                    return "ERROR|Невідома команда: " + command;
            }
        }catch (UniversityBaseException e) {
            System.out.println("Відмова: " + e.getMessage());
            return "FAIL|" + e.getMessage();

        }catch (Exception e) {
            return "ERROR|Помилка на сервері: " + e.getMessage();
        }
    }
}