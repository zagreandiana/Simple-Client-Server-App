package ro.ubb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientService {
    public String getResultFromServer(String fullCommand) {
        try (Socket server = new Socket("localhost", 1234);
             OutputStream os = server.getOutputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(server.getInputStream()))) {

            os.write(prepareToSend(fullCommand).getBytes());

            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String prepareToSend(String stringToBeSent) {
        return stringToBeSent + "\n";
    }
}