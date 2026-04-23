package university.service;

import com.google.gson.reflect.TypeToken;
import university.domain.Faculty;
import university.exceptions.FacultyNotFoundException;
import university.exceptions.PersonNotFoundException;
import university.network.Client;

import java.util.List;

public class RemoteFacultyService {
    private final Client client;

    public RemoteFacultyService(Client client) {
        this.client = client;
    }

    public void createFaculty(Faculty faculty) {
        String response = client.sendRequest("ADD_FACULTY", faculty);
        handleResponse(response);
    }

    public void deleteFaculty(String facultyId) {
        String response = client.sendRequest("DELETE_FACULTY", facultyId);
        handleResponse(response);
    }

    public void updateFaculty(Faculty faculty) {
        String response = client.sendRequest("UPDATE_FACULTY", faculty);
        handleResponse(response);
    }

    public Faculty getFaculty(String id) {
        String response = client.sendRequest("GET_FACULTY", id);
        String[] parts = response.split("\\|", 2);

        if (parts[0].equals("OK")) {
            return client.getGson().fromJson(parts[1], Faculty.class);
        } else {
            throw new FacultyNotFoundException(parts[1]);
        }
    }

    public List<Faculty> getAllFaculties() {
        String response = client.sendRequest("GET_ALL_FACULTIES", null);
        String[] parts = response.split("\\|", 2);

        if (parts[0].equals("OK")) {
            return client.getGson().fromJson(parts[1], new TypeToken<List<Faculty>>() {
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