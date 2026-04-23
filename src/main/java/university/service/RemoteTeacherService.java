package university.service;

import com.google.gson.reflect.TypeToken;
import university.domain.Teacher;
import university.exceptions.PersonNotFoundException;
import university.network.Client;

import java.util.List;

public class RemoteTeacherService {
    private final Client client;

    public RemoteTeacherService(Client client) {
        this.client = client;
    }

    public void createTeacher(Teacher teacher) {
        String response = client.sendRequest("ADD_TEACHER", teacher);
        handleResponse(response);
    }

    public void deleteTeacher(String teacherId) {
        String response = client.sendRequest("DELETE_TEACHER", teacherId);
        handleResponse(response);
    }

    public void updateTeacher(Teacher teacher) {
        String response = client.sendRequest("UPDATE_TEACHER", teacher);
        handleResponse(response);
    }

    public Teacher getTeacher(String id) {
        String response = client.sendRequest("GET_TEACHER", id);
        String[] parts = response.split("\\|", 2);

        if (parts[0].equals("OK")) {
            return client.getGson().fromJson(parts[1], Teacher.class);
        } else {
            throw new PersonNotFoundException(parts[1]);
        }
    }

    public List<Teacher> getAllTeachers() {
        String response = client.sendRequest("GET_ALL_TEACHERS", null);
        String[] parts = response.split("\\|", 2);

        if (parts[0].equals("OK")) {
            return client.getGson().fromJson(parts[1], new TypeToken<List<Teacher>>() {
            }.getType());
        } else {
            throw new RuntimeException("Помилка сервера: " + parts[1]);
        }
    }

    private void handleResponse(String response) {
        String[] parts = response.split("\\|", 2);
        if (!parts[0].equals("OK")) {
            System.out.println("Сервер повернув помилку: " + parts[1]);
        } else {
            System.out.println(parts[1]);
        }
    }
}