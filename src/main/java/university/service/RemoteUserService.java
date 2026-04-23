package university.service;

import com.google.gson.reflect.TypeToken;
import university.domain.User;
import university.exceptions.PersonNotFoundException;
import university.network.Client;

import java.util.List;

public class RemoteUserService {
    private final Client client;

    public RemoteUserService(Client client) {
        this.client = client;
    }

    public void createUser(User user) {
        String response = client.sendRequest("ADD_USER", user);
        handleResponse(response);
    }

    public void deleteUser(Integer userId) {
        String response = client.sendRequest("DELETE_USER", userId);
        handleResponse(response);
    }

    public void updateUser(User user) {
        String response = client.sendRequest("UPDATE_USER", user);
        handleResponse(response);
    }

    public User getUser(String id) {
        String response = client.sendRequest("GET_USER", id);
        String[] parts = response.split("\\|", 2);

        if (parts[0].equals("OK")) {
            return client.getGson().fromJson(parts[1], User.class);
        } else {
            throw new PersonNotFoundException(parts[1]);
        }
    }

    public List<User> getAllUsers() {
        String response = client.sendRequest("GET_ALL_USERS", null);
        String[] parts = response.split("\\|", 2);

        if (parts[0].equals("OK")) {
            return client.getGson().fromJson(parts[1], new TypeToken<List<User>>() {
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