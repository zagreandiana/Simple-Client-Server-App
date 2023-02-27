package ro.ubb.client;

import ro.ubb.service.ClientService;
import ro.ubb.ui.TextUi;

import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClientService clientService = new ClientService();
        TextUi ui = new TextUi(scanner, clientService);

        ui.start();
    }
}