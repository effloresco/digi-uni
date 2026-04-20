package university.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import university.storage.LocalDateAdapter;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class Client {
    private final String host;
    private final int port;
    @Getter
    private final Gson gson;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;

        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public String sendRequest(String command, Object data) {
        String jsonPayload = data != null ? gson.toJson(data) : "";
        String fullRequest = command + "|" + jsonPayload;

        try (Socket socket = new Socket(host, port);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            out.write(fullRequest + "\n");
            out.flush();

            return in.readLine();
        } catch (IOException e) {
            return "ERROR|Не вдалося з'єднатися з сервером: " + e.getMessage();
        }
    }
}