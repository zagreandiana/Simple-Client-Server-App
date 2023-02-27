package ro.ubb.server;

import ro.ubb.service.ServerService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ServerService serverService = new ServerService(threadPool);

        serverService.start();
    }
}