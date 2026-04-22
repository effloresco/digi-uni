package university.service;

import com.google.gson.reflect.TypeToken;
import university.domain.Student;
import university.exceptions.PersonNotFoundException;
import university.network.Client;
import java.util.List;

public class RemoteStudentService {
    private final Client client;

    public RemoteStudentService(Client client) {
        this.client = client;
    }

    public void createStudent(Student student) {
        String response = client.sendRequest("ADD_STUDENT", student);
        handleResponse(response);
    }

    public void deleteStudent(Student student) {
        String response = client.sendRequest("DELETE_STUDENT", student);
        handleResponse(response);
    }

    public void updateStudent(Student student) {
        String response = client.sendRequest("UPDATE_STUDENT", student);
        handleResponse(response);
    }

    public Student getStudent(String id) {
        String response = client.sendRequest("GET_STUDENT", id);
        String[] parts = response.split("\\|", 2);

        if (parts[0].equals("OK")) {
            return client.getGson().fromJson(parts[1], Student.class);
        } else {
            throw new PersonNotFoundException(parts[1]);
        }
    }

    public List<Student> getAllStudents() {
        String response = client.sendRequest("GET_ALL_STUDENTS", null);
        String[] parts = response.split("\\|", 2);

        if (parts[0].equals("OK")) {
            return client.getGson().fromJson(parts[1], new TypeToken<List<Student>>(){}.getType());
        } else {
            throw new RuntimeException("Помилка сервера: " + parts[1]);
        }
    }

    private void handleResponse(String response) {
        String[] parts = response.split("\\|", 2);
        if (!parts[0].equals("OK")) {
            System.out.println("Сервер повернув помилку: " + parts[1]);
        } else{
            System.out.println(parts[1]);
        }
    }
}
