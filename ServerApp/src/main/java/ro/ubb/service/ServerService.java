package ro.ubb.service;

import ro.ubb.handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ServerService {
    private final ExecutorService threadPool;

    public ServerService(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(1234)) {
            while (true) {
                Socket client = server.accept();
                ValidationService validationService =new ValidationService();
                threadPool.submit(new ClientHandler(client, validationService));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}