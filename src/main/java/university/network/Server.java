
package university.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// Твої існуючі імпорти з проєкту DigiUni:
import university.domain.Student;
import university.domain.Teacher;
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


    private final StudentRepository studentRepository = StudentRepository.get(StudentRepository.class);
    private final TeacherRepository teacherRepository = TeacherRepository.get(TeacherRepository.class);
    private final DepartmentRepository departmentRepository = DepartmentRepository.get(DepartmentRepository.class);
    private final FacultyRepository facultyRepository = FacultyRepository.get(FacultyRepository.class);
    private static final UserRepository userRepository = UserRepository.get(UserRepository.class);

    private final StudentService studentService = new StudentService(studentRepository);
    private final TeacherService teacherService = new TeacherService(teacherRepository);
    private final DepartmentService departmentService = new DepartmentService(departmentRepository);
    private final FacultyService facultyService = new FacultyService(facultyRepository);
    private static final UserService userService = new UserService(userRepository);

    private static final StudentStorageManager studentStorageManager = new StudentStorageManager();
    private static final TeacherStorageManager teacherStorageManager = new TeacherStorageManager();
    private static final DepartmentStorageManager departmentStorageManager = new DepartmentStorageManager();
    private static final FacultyStorageManager facultyStorageManager = new FacultyStorageManager();
    private static final UserStorageManager userStorageManager = new UserStorageManager();

    private final Gson networkGson;

    public Server() {
        this.networkGson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public void start(int port) {
        System.out.println("Ініціалізація сервера DigiUni...");

        System.out.println("Зчитування локальної бази даних з файлів...");
        studentStorageManager.loadAllData();
        studentStorageManager.loadAllData();
        teacherStorageManager.loadAllData();
        departmentStorageManager.loadAllData();
        facultyStorageManager.loadAllData();
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
            String[] parts = request.split("\\|", 3);
            String command = parts[0];
            String payload = parts.length > 1 ? parts[1] : "";
            String additionalPayload = parts.length > 2 ? parts[2] : "";

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
                    String studentToDelete = networkGson.fromJson(payload, String.class);
                    studentService.deleteStudent(studentToDelete);
                    studentStorageManager.saveAllData();
                    return "OK|Студента видалено";

                case "UPDATE_STUDENT":
                    Student updatedStudent = networkGson.fromJson(payload, Student.class);
                    studentService.updateStudent(updatedStudent.getID(), updatedStudent);
                    return "OK|Студента успішно оновлено!";

                case "GET_STUDENT":
                    String studentId = networkGson.fromJson(payload, String.class);
                    Student student = studentService.getStudent(studentId);
                    return "OK|" + networkGson.toJson(student);

                case "GET_ALL_TEACHERS":
                    List<Teacher> teachers = teacherRepository.findAll();
                    return "OK|" + networkGson.toJson(teachers);

                case "ADD_TEACHER":
                    Teacher newTeacher = networkGson.fromJson(payload, Teacher.class);
                    teacherService.createTeacher(newTeacher);
                    teacherStorageManager.saveAllData();
                    return "OK|Викладача успішно додано до бази!";

                case "DELETE_TEACHER":
                    String teacherToDelete = networkGson.fromJson(payload, String.class);
                    teacherService.deleteTeacher(teacherToDelete);
                    teacherStorageManager.saveAllData();
                    return "OK|Викладача видалено";

                case "UPDATE_TEACHER":
                    Teacher updatedTeacher = networkGson.fromJson(payload, Teacher.class);
                    teacherService.updateTeacher(updatedTeacher.getID(), updatedTeacher);
                    return "OK|Викладача успішно оновлено!";

                case "GET_TEACHER":
                    String teacherId = networkGson.fromJson(payload, String.class);
                    Teacher teacher = teacherService.getTeacher(teacherId);
                    return "OK|" + networkGson.toJson(teacher);

                case "GET_ALL_DEPARTMENTS":
                    List<university.domain.Department> departments = departmentRepository.findAll();
                    return "OK|" + networkGson.toJson(departments);

                case "ADD_DEPARTMENT":
                    university.domain.Department newDepartment = networkGson.fromJson(payload, university.domain.Department.class);
                    departmentService.createDepartment(newDepartment);
                    departmentStorageManager.saveAllData();
                    return "OK|Кафедру успішно додано до бази!";

                case "DELETE_DEPARTMENT":
                    String departmentToDelete = networkGson.fromJson(payload, String.class);
                    departmentService.deleteDepartment(departmentToDelete);
                    departmentStorageManager.saveAllData();
                    return "OK|Кафедру видалено";

                case "UPDATE_DEPARTMENT":
                    university.domain.Department updatedDepartment = networkGson.fromJson(payload, university.domain.Department.class);
                    departmentService.updateDepartment(updatedDepartment.getID(), updatedDepartment);
                    return "OK|Кафедру успішно оновлено!";

                case "GET_DEPARTMENT":
                    String departmentId = networkGson.fromJson(payload, String.class);
                    university.domain.Department department = departmentService.getDepartment(departmentId);
                    return "OK|" + networkGson.toJson(department);

                case "GET_ALL_FACULTIES":
                    List<university.domain.Faculty> faculties = facultyRepository.findAll();
                    return "OK|" + networkGson.toJson(faculties);

                case "ADD_FACULTY":
                    university.domain.Faculty newFaculty = networkGson.fromJson(payload, university.domain.Faculty.class);
                    facultyService.createFaculty(newFaculty);
                    facultyStorageManager.saveAllData();
                    return "OK|Факультет успішно додано до бази!";

                case "DELETE_FACULTY":
                    String facultyToDelete = networkGson.fromJson(payload, String.class);
                    facultyService.deleteFaculty(facultyToDelete);
                    facultyStorageManager.saveAllData();
                    return "OK|Факультет видалено";

                case "UPDATE_FACULTY":
                    university.domain.Faculty updatedFaculty = networkGson.fromJson(payload, university.domain.Faculty.class);
                    facultyService.updateFaculty(updatedFaculty.getID(), updatedFaculty);
                    return "OK|Факультет успішно оновлено!";

                case "GET_FACULTY":
                    String facultyId = networkGson.fromJson(payload, String.class);
                    university.domain.Faculty faculty = facultyService.getFaculty(facultyId);
                    return "OK|" + networkGson.toJson(faculty);

                case "GET_ALL_USERS":
                    List<User> users = userRepository.findAll();
                    return "OK|" + networkGson.toJson(users);

                case "ADD_USER":
                    User newUser = networkGson.fromJson(payload, User.class);
                    userService.createUser(newUser);
                    userStorageManager.saveAllData();
                    return "OK|Користувача успішно додано до бази!";

                case "DELETE_USER":
                    Integer userToDelete = networkGson.fromJson(payload, Integer.class);
                    userService.deleteUser(userToDelete);
                    userStorageManager.saveAllData();
                    return "OK|Користувача видалено";

                case "UPDATE_USER":
                    User updatedUser = networkGson.fromJson(payload, User.class);
                    userService.updateUser(updatedUser.getID(), updatedUser);
                    return "OK|Користувача успішно оновлено!";

                case "GET_USER":
                    Integer userId = networkGson.fromJson(payload, Integer.class);
                    User user = userService.getUser(userId);
                    return "OK|" + networkGson.toJson(user);
                    
                case "AUTH_USER":
                    String[] authParts = networkGson.fromJson(payload, String[].class);

                    if (authParts == null || authParts.length != 2) {
                        return "FAIL|Невірний формат даних";
                    }

                    String reqUsername = authParts[0];
                    String reqPassword = authParts[1];

                    List<User> results = userRepository.findAll().stream()
                            .filter(p -> p.getUserName().equals(reqUsername))
                            .toList();

                    if (results.isEmpty()) {
                        return "FAIL|Користувача не знайдено";
                    }

                    User targetUser = results.get(0);
                    if (!targetUser.getPassword().equals(reqPassword)) {
                        return "FAIL|Неправильний пароль";
                    }

                    return "OK|" + targetUser.getRole();
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