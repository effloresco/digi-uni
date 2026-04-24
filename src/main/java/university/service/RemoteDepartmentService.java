package university.service;

import com.google.gson.reflect.TypeToken;
import university.domain.Department;
import university.exceptions.DepartmentNotFoundException;
import university.exceptions.PersonNotFoundException;
import university.network.Client;

import java.util.List;

public class RemoteDepartmentService {
    private final Client client;

    public RemoteDepartmentService(Client client) {
        this.client = client;
    }

    public void createDepartment(Department department) {
        String response = client.sendRequest("ADD_DEPARTMENT", department);
        handleResponse(response);
    }

    public void deleteDepartment(String departmentId) {
        String response = client.sendRequest("DELETE_DEPARTMENT", departmentId);
        handleResponse(response);
    }

    public void updateDepartment(Department department) {
        String response = client.sendRequest("UPDATE_DEPARTMENT", department);
        handleResponse(response);
    }

    public Department getDepartment(String id) {
        String response = client.sendRequest("GET_DEPARTMENT", id);
        String[] parts = response.split("\\|", 2);

        if (parts[0].equals("OK")) {
            return client.getGson().fromJson(parts[1], Department.class);
        } else {
            throw new DepartmentNotFoundException(parts[1]);
        }
    }

    public List<Department> getAllDepartments() {
        String response = client.sendRequest("GET_ALL_DEPARTMENTS", null);
        String[] parts = response.split("\\|", 2);

        if (parts[0].equals("OK")) {
            return client.getGson().fromJson(parts[1], new TypeToken<List<Department>>() {
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